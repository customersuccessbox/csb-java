package com.customersuccessbox.sdk.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.customersuccessbox.sdk.exception.CSBException;
import com.customersuccessbox.sdk.response.CSBResponse;
import com.google.gson.Gson;

/**
 * The Class SyncAPIClient.
 */
public class SyncAPIClient extends APIClient {

    /** The client. */
    CloseableHttpClient client;

    /**
     * Instantiates a new sync API client.
     *
     * @param endpoint
     *            the endpoint
     * @param secret
     *            the secret
     */
    public SyncAPIClient(final String endpoint, final String secret) {
        super(endpoint, secret);
        client = HttpClients.createDefault();
    }

    @Override
    public CSBResponse post(final String endpoint, final Map<String, ?> attributes) {
        Gson gson = new Gson();
        HttpPost httpPost = getPostRequest(attributes, gson, endpoint);
        String responseBody = null;
        try {
            CloseableHttpResponse response = client.execute(httpPost);
            responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            // client.close();
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new CSBException("CSB API request failed Status Code:" + response.getStatusLine().getStatusCode()
                    + " Response:" + responseBody);
            }
        } catch (ClientProtocolException e) {
            throw new CSBException("Client protocol exception", e);
        } catch (IOException e) {
            throw new CSBException("Error while communicating to API", e);
        } finally {
            httpPost.releaseConnection();
        }
        CSBResponse response = gson.fromJson(responseBody, CSBResponse.class);

        if (response.getError() != null) {
            throw new CSBException(response.getError());
        }

        return response;
    }

}
