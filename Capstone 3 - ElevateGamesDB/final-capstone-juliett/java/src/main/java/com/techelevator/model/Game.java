package com.techelevator.model;

public class Game {
    private int gameId;
    private String listName;

    public Game() {

    }

    public Game(int gameId) {
        this.gameId = gameId;
    }

    public Game(int gameId, String listName) {
        this.gameId = gameId;
        this.listName = listName;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
