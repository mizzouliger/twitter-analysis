package com.aditya;

import java.io.IOException;
import java.net.URISyntaxException;

public class TwitterApp {

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("this should start the base app");

        TwitterService recentTweets = new TwitterService();
        // Get the userID
        recentTweets.getTweets();
    }
}

