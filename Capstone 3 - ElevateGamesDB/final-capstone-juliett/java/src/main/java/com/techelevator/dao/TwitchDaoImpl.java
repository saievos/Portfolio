package com.techelevator.dao;

import com.techelevator.model.Data;
import com.techelevator.model.RootObject;
import com.techelevator.model.Video;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class TwitchDaoImpl implements TwitchDao {

    private static final String API_BASE_URL = "https://api.twitch.tv/helix/videos?game_id=";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Data> fetchVideos(int gameId, String token) {
        RootObject root = null;

        try {
            ResponseEntity<RootObject> response = restTemplate.exchange(API_BASE_URL + gameId, HttpMethod.GET, makeAuthEntity(token), RootObject.class);
            root = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            e.printStackTrace();
        }

        return root.getData();
    }

    private HttpEntity<Void> makeAuthEntity(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        headers.set("Client-ID", "03vf45apqn4941sztgy229hmdpvzwj");
        return new HttpEntity<>(headers);
    }
}
