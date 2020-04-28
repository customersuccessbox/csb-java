package com.customersuccessbox.sdk;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import com.customersuccessbox.sdk.response.CSBResponse;
import com.customersuccessbox.sdk.service.APIClient;
import com.customersuccessbox.sdk.service.AsyncAPIClient;
import com.customersuccessbox.sdk.service.SyncAPIClient;
import com.customersuccessbox.sdk.utils.CSBUtils;

/**
 * The Class CSB.
 */
public class CSB {

    /** The api service. */
    private APIClient client;

    /**
     * Instantiates a new csb.
     *
     * @param endpoint
     *            the endpoint
     * @param secret
     *            the secret
     */
    public CSB(final String endpoint, final String secret) {
        this(endpoint, secret, false);
    }

    /**
     * Instantiates a new csb.
     *
     * @param endpoint
     *            the endpoint
     * @param secret
     *            the secret
     * @param async
     *            the async
     */
    public CSB(final String endpoint, final String secret, final boolean async) {
        if (async) {
            this.client = new AsyncAPIClient(endpoint, secret);
        } else {
            this.client = new SyncAPIClient(endpoint, secret);
        }
    }

    /**
     * Login.
     *
     * @param userId
     *            the user id
     * @param accountId
     *            the account id
     * @return the CSB response
     */
    public CSBResponse login(final String userId, final String accountId) {
        this.validateLoginCredentials(accountId, userId);

        Map<String, String> attrs = buildAttributes(userId, accountId, "track");
        return this.track("User Login", attrs);
    }

    /**
     * Logout.
     *
     * @param userId
     *            the user id
     * @param accountId
     *            the account id
     * @return the CSB response
     */
    public CSBResponse logout(final String userId, final String accountId) {
        this.validateLoginCredentials(accountId, userId);

        Map<String, String> attrs = buildAttributes(userId, accountId, "track");
        return this.track("User Logout", attrs);
    }

    /**
     * Account.
     *
     * @param accountId
     *            the account id
     * @param traits
     *            the traits
     */
    public void account(final String accountId, final Map<String, String> traits) {
        validateAccountID(accountId);
        Map<String, String> attrs = buildAttributes(null, accountId, "account");
        Map<String, Object> objAttrs = new HashMap<>();
        objAttrs.putAll(attrs);
        objAttrs.put("traits", traits);

        this.client.account(objAttrs);
    }

    /**
     * User.
     *
     * @param userId
     *            the user id
     * @param accountId
     *            the account id
     * @param traits
     *            the traits
     */
    public void user(final String userId, final String accountId, final Map<String, String> traits) {
        this.validateLoginCredentials(accountId, userId);
        Map<String, String> attrs = buildAttributes(userId, accountId, "user");
        Map<String, Object> objAttrs = new HashMap<>();
        objAttrs.putAll(attrs);
        objAttrs.put("traits", traits);

        this.client.account(objAttrs);
    }

    /**
     * Feature.
     *
     * @param userId
     *            the user id
     * @param accountId
     *            the account id
     * @param productId
     *            the product id
     * @param moduleId
     *            the module id
     * @param featureId
     *            the feature id
     */
    public void feature(final String userId, final String accountId, final String productId, final String moduleId,
        final String featureId) {
        this.feature(userId, accountId, productId, moduleId, featureId, 1);
    }

    /**
     * Feature.
     *
     * @param userId
     *            the user id
     * @param accountId
     *            the account id
     * @param productId
     *            the product id
     * @param moduleId
     *            the module id
     * @param featureId
     *            the feature id
     * @param total
     *            the total
     */
    public void feature(final String userId, final String accountId, final String productId, final String moduleId,
        final String featureId, final int total) {
        this.validateLoginCredentials(accountId, userId);
        Map<String, String> attrs = this.buildAttributes(userId, accountId, "feature");
        attrs.put("productId", productId);
        attrs.put("featureId", featureId);
        attrs.put("moduleId", moduleId);
        attrs.put("total", String.valueOf(total));

        this.client.feature(attrs);
    }

    /**
     * Validate account ID.
     *
     * @param accountId
     *            the account id
     */
    private void validateAccountID(final String accountId) {
        if (!CSBUtils.emptyCheck(accountId)) {
            throw new IllegalArgumentException("Invalid Account ID");
        }
    }

    /**
     * Validate login credentials.
     *
     * @param userId
     *            the user id
     * @param accountId
     *            the account id
     */
    private void validateLoginCredentials(final String userId, final String accountId) {
        if (!CSBUtils.emptyCheck(userId)) {
            throw new IllegalArgumentException("Invalid User ID");
        }
        validateAccountID(accountId);
    }

    /**
     * Track.
     *
     * @param event
     *            the event
     * @param attrs
     *            the attrs
     * @return the CSB response
     */
    private CSBResponse track(final String event, Map<String, String> attrs) {
        if (attrs == null) {
            attrs = new HashMap<>();
        }
        attrs.put("event", event);
        return this.client.track(attrs);
    }

    /**
     * Gets the GMT timestamp.
     *
     * @return the GMT timestamp
     */
    private String getGMTTimestamp() {
        return LocalDateTime.now(ZoneId.of("GMT")).toString();
    }

    /**
     * Builds the attributes.
     *
     * @param userId
     *            the user id
     * @param accountId
     *            the account id
     * @param type
     *            the type
     * @return the map
     */
    public Map<String, String> buildAttributes(final String userId, final String accountId, final String type) {
        Map<String, String> attrs = new HashMap<>();
        attrs.put("accountId", accountId);
        if (userId != null) {
            attrs.put("userId", userId);
        }
        attrs.put("type", type);
        attrs.put("timestamp", getGMTTimestamp());
        return attrs;
    }

}
