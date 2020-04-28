package com.customersuccessbox.sdk.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import com.customersuccessbox.sdk.exception.CSBException;
import com.customersuccessbox.sdk.response.CSBResponse;
import com.google.gson.Gson;

/**
 * The Class APIClient.
 */
public abstract class APIClient {

    /** The endpoint. */
    private String endpoint;

    /** The secret. */
    private String secret;

    /**
     * Instantiates a new API client.
     *
     * @param endpoint
     *            the endpoint
     * @param secret
     *            the secret
     */
    public APIClient(final String endpoint, final String secret) {
        this.endpoint = endpoint;
        this.secret = secret;
    }

    /**
     * Track.
     *
     * @param attributes
     *            the attributes
     * @return the CSB response
     */
    public CSBResponse track(final Map<String, String> attributes) {
        return this.post(endpoint + "/api_js/v1_1/track", attributes);
    }

    /**
     * Account.
     *
     * @param attributes
     *            the attributes
     * @return the CSB response
     */
    public CSBResponse account(final Map<String, Object> attributes) {
        return this.post(endpoint + "/api_js/v1_1/account", attributes);
    }

    /**
     * Identify.
     *
     * @param attributes
     *            the attributes
     * @return the CSB response
     */
    public CSBResponse identify(final Map<String, String> attributes) {
        return this.post(endpoint + "/api_js/v1_1/identify", attributes);
    }

    /**
     * Feature.
     *
     * @param attributes
     *            the attributes
     * @return the CSB response
     */
    public CSBResponse feature(final Map<String, String> attributes) {

        return this.post(endpoint + "/api_js/v1_1/feature", attributes);
    }

    /**
     * Post.
     *
     * @param endpoint
     *            the endpoint
     * @param attributes
     *            the attributes
     * @return the CSB response
     */
    public abstract CSBResponse post(final String endpoint, final Map<String, ?> attributes);

    /**
     * Gets the post request.
     *
     * @param attributes
     *            the attributes
     * @param gson
     *            the gson
     * @param url 
     * @return the post request
     */
    public HttpPost getPostRequest(final Map<String, ?> attributes, final Gson gson, final String url) {
        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setEntity(new StringEntity(gson.toJson(attributes)));
        } catch (UnsupportedEncodingException e) {
            throw new CSBException("Unsupported Encoding", e);
        }
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type", "application/json");
        httpPost.addHeader("Authorization", "Bearer " + this.getSecret());
        return httpPost;
    }

    /**
     * Gets the endpoint.
     *
     * @return the endpoint
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * Sets the endpoint.
     *
     * @param endpoint
     *            the new endpoint
     */
    public void setEndpoint(final String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * Gets the secret.
     *
     * @return the secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Sets the secret.
     *
     * @param secret
     *            the new secret
     */
    public void setSecret(final String secret) {
        this.secret = secret;
    }

}
