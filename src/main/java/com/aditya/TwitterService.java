package com.aditya;

import com.aditya.domain.Tweet;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class TwitterService {

    public TwitterService() {
    }

    HttpClient httpClient = HttpClients.custom()
            .setDefaultRequestConfig(RequestConfig.custom()
                    .setCookieSpec(CookieSpecs.STANDARD).build())
            .build();


    public void getTweets() throws IOException, URISyntaxException {
        String bearerToken = "AAAAAAAAAAAAAAAAAAAAAKMx9gAAAAAADPUG2ub%2BkRrNw5z46KiWl6yB2yk%3DJfYv4YNWmjgBUELEQXsebG13wBZoXLSYlPQoQmbnZejC0IgPyX";
//        String response = getTwitterUser(bearerToken, "RepHartzler");
        String tweets = getTweetsByUserID("237763317", bearerToken);
        System.out.println(tweets);
    }

    private String getTweetsByUserID(String userID, String bearerToken) throws IOException, URISyntaxException {
        String searchResponse = null;

        URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/users/" + userID + "/tweets");
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
//        queryParameters.add(new BasicNameValuePair("pagination_token", "7140dibdnow9c7btw3z21ao1vtzvt6aythkd7ii0a7f3t"));
        queryParameters.add(new BasicNameValuePair("max_results", "5"));

        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            searchResponse = EntityUtils.toString(entity, "UTF-8");
            InputStream entireTweet = new ByteArrayInputStream(searchResponse.getBytes(StandardCharsets.UTF_8));

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode map = objectMapper.readTree(entireTweet);

            JsonNode data = map.get("data");

            String text = new BufferedReader(
                    new InputStreamReader(entireTweet, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            System.out.println(text);

        }
        return searchResponse;
    }

    private String getTwitterUser(String bearerToken, String userName) throws IOException, URISyntaxException {

        String searchResponse = null;

        URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/users/by");
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("usernames", userName));
        queryParameters.add(new BasicNameValuePair("user.fields", "created_at,description,pinned_tweet_id"));

        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);

        HttpEntity entity = response.getEntity();
        if (null != entity) {
            searchResponse = EntityUtils.toString(entity, "UTF-8");
            InputStream textOfTweet = new ByteArrayInputStream(searchResponse.getBytes(StandardCharsets.UTF_8));

            String text = new BufferedReader(
                    new InputStreamReader(textOfTweet, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            System.out.println(text);

        }
        return searchResponse;

    }

}