package com.techelevator.dao;

import java.util.List;

public interface GameListDao {

    List<String> retrieveGameListNames(String userName);

    List<Integer> retrieveGameLists(String listName);

    int addGameToList(int gameId, String listName);

    int createCustomList(int userId, String listName);

    void editList(String listName, String updatedListName);

    void deleteList(String listName);

    int getUserIdByUsername(String username);

    int deleteGame(int gameId);

    void setupUser(int userId);
}
