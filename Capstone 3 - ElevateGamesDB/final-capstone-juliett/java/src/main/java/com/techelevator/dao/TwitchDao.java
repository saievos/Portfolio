package com.techelevator.dao;

import com.techelevator.model.Data;

import java.util.List;

public interface TwitchDao {
    List<Data> fetchVideos(int gameId, String token);
}
