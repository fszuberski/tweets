package com.fszuberski.tweets.persistence.adapter;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import com.fszuberski.tweets.persistence.port.out.GetTweetsPort;
import com.fszuberski.tweets.persistence.port.out.SaveTweetPort;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.apache.http.util.TextUtils.isEmpty;

public class TweetsPersistenceAdapter
        extends AbstractPersistenceAdapter
        implements GetTweetsPort, SaveTweetPort {

    @Override
    public List<Map<String, Object>> getTweetsAsMap(String userId, boolean createdByUser, boolean createdByOtherUsers) {
        if (isEmpty(userId)) {
            return Collections.emptyList();
        }

        Table table = getTable();
        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression("PK = :v_pk and begins_with(SK, :v_sk)")
                .withScanIndexForward(false); // descending order


        if (!createdByUser && createdByOtherUsers) {
            querySpec.withFilterExpression("CreatedBy <> :v_pk");
        } else if (createdByUser && !createdByOtherUsers) {
            querySpec.withFilterExpression("CreatedBy = :v_pk");
        }

        querySpec.withValueMap(new ValueMap()
                .withString(":v_pk", "USER#" + userId)
                .withString(":v_sk", "TWEET#"));

        ItemCollection<QueryOutcome> items = table.query(querySpec);

        List<Map<String, Object>> result = new ArrayList<>();
        for (Item item : items) {
            result.add(item.asMap());
        }

        return result;
    }

    @Override
    public void saveTweet(String createdByUserId, String text) {
        if (createdByUserId == null || createdByUserId.trim().isEmpty()) {
            throw new IllegalArgumentException("'createdByUserId' cannot be null or empty");
        }

        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("'text' cannot be null or empty");
        }

        List<String> userIds = getAllUserIds();
        String currentDateTime = DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now());

        List<Item> itemsToPersist = new ArrayList<>();
        for (String createdForUserId : userIds) {
            Item item = createTweetItem(createdByUserId, createdForUserId.replace("PROFILE#", ""), text, currentDateTime);
            itemsToPersist.add(item);
        }

        TableWriteItems tableWriteItems = getTableWriteItems().withItemsToPut(itemsToPersist);
        BatchWriteItemOutcome batchWriteItemOutcome = getDynamoDB().batchWriteItem(tableWriteItems);
        // Check for unprocessed keys which could happen if you exceed provisioned throughput
        // https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/batch-operation-document-api-java.html
        do {
            Map<String, List<WriteRequest>> unprocessedItems = batchWriteItemOutcome.getUnprocessedItems();
            if (batchWriteItemOutcome.getUnprocessedItems().size() > 0) {
                batchWriteItemOutcome = getDynamoDB().batchWriteItemUnprocessed(unprocessedItems);
            }

        } while (batchWriteItemOutcome.getUnprocessedItems().size() > 0);
    }

    /**
     * Returns IDs of all existing users.
     * <p>
     * WARNING:
     * This method uses a full table scan, which is not performant (scans all partitions) and consumes a lot of Read Capacity Units.
     * <p>
     * This could be improved by extending the code challenge - keeping track of user followers.
     * The IDs of the followers could be tied to the users profile, which is easily Queried (retrieving a single row from a single partition).
     */
    private List<String> getAllUserIds() {
        Table table = getTable();
        ScanSpec scanSpec = new ScanSpec()
                .withFilterExpression("begins_with(SK, :v_sk)")
                .withValueMap(new ValueMap()
                        .withString(":v_sk", "PROFILE#"));

        ItemCollection<ScanOutcome> items = table.scan(scanSpec);

        List<String> userIds = new ArrayList<>();
        for (Item item : items) {
            String userId = (String) item.asMap().get("SK");
            userIds.add(userId);
        }

        return userIds;
    }

    private Item createTweetItem(String createdByUserId, String createdForUserId, String text, String currentDateTime) {
        String tweetId = UUID.randomUUID().toString();
        return new Item()
                .withPrimaryKey(
                        "PK", String.format("USER#%s", createdForUserId),
                        "SK", String.format("TWEET#%s#%s", currentDateTime, tweetId))
                .withString("TweetId", tweetId)
                .withString("Text", text)
                .withString("CreatedBy", String.format("USER#%s", createdByUserId))
                .withString("CreatedAt", currentDateTime);
    }
}
