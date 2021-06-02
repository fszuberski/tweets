package com.fszuberski.tweets.persistence.adapter;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.fszuberski.tweets.persistence.port.out.GetUserProfilePort;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;


public class UserProfilePersistenceAdapter
        extends AbstractPersistenceAdapter
        implements GetUserProfilePort {

    @Override
    public Optional<Map<String, Object>> getUserProfileAsMap(String username) {
        Table table = getTable();

        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression("PK = :v_pk and SK = :v_sk")
                .withValueMap(new ValueMap()
                        .withString(":v_pk", "USER#" + username)
                        .withString(":v_sk", "PROFILE#" + username));

        ItemCollection<QueryOutcome> items = table.query(querySpec);

        Iterator<Item> iterator = items.iterator();
        if (!iterator.hasNext()) {
            return Optional.empty();
        }

        return Optional.of(iterator.next().asMap());
    }
}
