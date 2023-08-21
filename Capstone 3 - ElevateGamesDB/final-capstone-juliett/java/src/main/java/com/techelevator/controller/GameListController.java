package com.techelevator.controller;

import com.techelevator.dao.GameListDao;
import com.techelevator.dao.TwitchDao;
import com.techelevator.model.Data;
import com.techelevator.model.Game;
import com.techelevator.model.ListDTO;
import com.techelevator.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class GameListController {

    @Autowired
    private GameListDao gameListDao;
    @Autowired
    private TwitchDao twitchDao;

    @RequestMapping(path = "/list/names", method = RequestMethod.GET)
    public List<String> retrieveListNames(Principal principal) {
        return gameListDao.retrieveGameListNames(principal.getName());
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public List<Integer> retrieveGameLists(String listName) {
        return gameListDao.retrieveGameLists(listName);
    }

    @RequestMapping(path = "/game", method = RequestMethod.POST)
    public int addGameToList(@RequestBody Game game){
        return gameListDao.addGameToList(game.getGameId(), game.getListName());
    }

    @RequestMapping(path = "/game", method = RequestMethod.DELETE)
    public int deleteGame(@RequestParam int gameId){
        return gameListDao.deleteGame(gameId);
    }

    @RequestMapping(path = "/list", method = RequestMethod.POST)
    public int createCustomList(@RequestBody ListDTO list, Principal principal){
        String username = principal.getName();
        int userId = gameListDao.getUserIdByUsername(username);
        return gameListDao.createCustomList(userId, list.getListName());
    }

    @RequestMapping(path="/list", method = RequestMethod.PUT)
    public void editList(@RequestBody ListDTO list){
        gameListDao.editList(list.getListName(), list.getUpdateName());
    }

    @RequestMapping(path="/list", method = RequestMethod.DELETE)
    public void deleteList(@RequestParam String name){
        gameListDao.deleteList(name);
    }

    @RequestMapping(path="/videos", method = RequestMethod.GET)
    public List<Data> fetchVideos(@RequestParam int gameId, @RequestParam String token) {
        return twitchDao.fetchVideos(gameId, token);
    }
}
