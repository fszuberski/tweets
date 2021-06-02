package com.fszuberski.tweets.persistence.adapter;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;

public abstract class AbstractPersistenceAdapter {
    private static final String DYNAMODB_TABLE_NAME = "UserTweets";
    private static final Regions REGION = Regions.fromName("eu-west-2");

    private static final AmazonDynamoDB dynamoDBClient = initDynamoDBClient();
    private static final DynamoDB dynamoDB = new DynamoDB(dynamoDBClient);

    protected DynamoDB getDynamoDB() {
        return dynamoDB;
    }

    protected Table getTable() {
        return dynamoDB.getTable(DYNAMODB_TABLE_NAME);
    }

    protected TableWriteItems getTableWriteItems() {
        return new TableWriteItems(DYNAMODB_TABLE_NAME);
    }

    private static AmazonDynamoDB initDynamoDBClient() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withRegion(REGION)
                .build();
    }
}
