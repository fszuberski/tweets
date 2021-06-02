package com.fszuberski.tweets.authorizer.core.domain.iam;

import com.fszuberski.tweets.authorizer.core.domain.HttpMethod;
import lombok.Getter;

import java.util.*;

public class Policy {

    private static final String RESOURCE_TEMPLATE = "arn:aws:execute-api:%s:%s:%s/%s/%s/%s";

    private final PolicyDocument policyDocument;

    @Getter
    private final String principalId;

    @Getter
    private final Map<String, String> context;

    private Policy(String principalId, PolicyDocument policyDocument) {
        this(principalId, policyDocument, Collections.emptyMap());
    }

    private Policy(String principalId, PolicyDocument policyDocument, Map<String, String> context) {
        this.principalId = principalId;
        this.policyDocument = policyDocument;
        this.context = context;
    }

    public Map<String, Object> getPolicyDocument() {
        return policyDocument.asMap();
    }

    public static Policy create(Map<String, List<HttpMethod>> allowedResources, String methodArn, Map<String, String> ctx) {
        PolicyContext policyContext = new PolicyContext(methodArn);

        List<String> resources = new ArrayList<>();
        for (Map.Entry<String, List<HttpMethod>> allowedResource : allowedResources.entrySet()) {
            for (HttpMethod httpMethod : allowedResource.getValue()) {
                String resource = String.format(RESOURCE_TEMPLATE,
                        policyContext.getRegion(),
                        policyContext.getAwsAccountId(),
                        policyContext.getRestApiId(),
                        policyContext.getStage(),
                        httpMethod.toString(),
                        allowedResource.getKey());

                resources.add(resource);
            }
        }

        Statement statement = new Statement(Action.EXECUTE_API_INVOKE, Effect.ALLOW, resources);
        PolicyDocument policyDocument = new PolicyDocument(Collections.singletonList(statement));
        return new Policy(policyContext.getAwsAccountId(), policyDocument, ctx);
    }

    public static Policy denyAll(String methodArn) {
        PolicyContext policyContext = new PolicyContext(methodArn);
        Statement statement = new Statement(Action.ALL, Effect.DENY);
        PolicyDocument policyDocument = new PolicyDocument(Collections.singletonList(statement));
        return new Policy(policyContext.getAwsAccountId(), policyDocument);
    }

    @Getter
    private static class PolicyContext {
        private final String region;
        private final String awsAccountId;
        private final String restApiId;
        private final String stage;

        public PolicyContext(String methodArn) {
            if (methodArn == null || methodArn.trim().isEmpty()) {
                throw new IllegalArgumentException(
                        String.format("Exception instantiating %s - methodArn cannot be null", PolicyContext.class.getSimpleName()));
            }

            String[] arnPartials = methodArn.split(":");
            this.region = arnPartials[3];
            this.awsAccountId = arnPartials[4];
            String[] apiGatewayArnPartials = arnPartials[5].split("/");
            this.restApiId = apiGatewayArnPartials[0];
            this.stage = apiGatewayArnPartials[1];
        }
    }
}
