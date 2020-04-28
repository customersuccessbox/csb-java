package com.customersuccessbox.sdk.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;

import com.customersuccessbox.sdk.response.CSBResponse;
import com.google.gson.Gson;

/**
 * The Class AsyncAPIClient.
 */
public class AsyncAPIClient extends APIClient {

    /** The Constant LOG. */
    private static final Log LOG = LogFactory.getLog(AsyncAPIClient.class);

    /** The client. */
    CloseableHttpAsyncClient client;

    /**
     * Instantiates a new async API client.
     *
     * @param endpoint
     *            the endpoint
     * @param secret
     *            the secret
     */
    public AsyncAPIClient(final String endpoint, final String secret) {
        super(endpoint, secret);
        client = HttpAsyncClients.createDefault();
        client.start();
    }

    @Override
    public CSBResponse post(final String endpoint, final Map<String, ?> attributes) {

        final HttpPost post = getPostRequest(attributes, new Gson(), endpoint);
        client.execute(post, new FutureCallback<HttpResponse>() {

            @Override
            public void failed(final Exception ex) {
                LOG.error("Failed CSB API Request: " + ex.getMessage());
                post.releaseConnection();
                LOG.trace(Thread.class.getName());

            }

            @Override
            public void completed(final HttpResponse result) {
                LOG.info("CSB API Request completed");
                try {
                    Logger.getGlobal().info("Response: " + EntityUtils.toString(result.getEntity(),
                        StandardCharsets.UTF_8));
                } catch (ParseException | IOException e) {
                    e.printStackTrace();
                }
                post.releaseConnection();
            }

            @Override
            public void cancelled() {
                LOG.trace("CSB API Request cancelled");
                post.releaseConnection();
            }
        });

        return new CSBResponse();
    }

}
