package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcGameListDao implements GameListDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcGameListDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> retrieveGameListNames(String userName) {
        List<String> listNames = new ArrayList<>();

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet("SELECT list_name FROM lists " +
                    "JOIN users ON users.user_id = lists.user_id " +
                    "WHERE users.username = ?", userName);
            while (results.next()) {
                listNames.add(results.getString("list_name"));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server or database.", e);
        }

        return listNames;
    }

    @Override
    public List<Integer> retrieveGameLists(String listName) {
        List<Integer> gameList = new ArrayList<>();

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet("SELECT games.game_id FROM games " +
                    "JOIN game_list ON game_list.game_id = games.game_id " +
                    "JOIN lists ON lists.list_id = game_list.list_id " +
                    "WHERE lists.list_name = ?", listName);
            while (results.next()) {
                gameList.add(results.getInt("game_id"));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server or database.", e);
        }

        return gameList;
    }

    @Override
    public int addGameToList(int gameId, String listName) {
        int result = 0;

        try {
             result = jdbcTemplate.queryForObject("INSERT INTO games (game_id) VALUES (?) RETURNING game_id", int.class, gameId);
             jdbcTemplate.update("INSERT INTO game_list (game_id, list_id) VALUES (?, (SELECT list_id FROM lists WHERE list_name = ?))", gameId, listName);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server or database.", e);
        } catch (NullPointerException e)  {
            throw new NullPointerException("Null value found");
        } catch (DataIntegrityViolationException e) {
            result = -1;
        }

        return result;
    }

    @Override
    public int createCustomList(int userId, String listName) {
        try {
            //return jdbcTemplate.queryForObject("Insert Into lists(list_name) Values (?)", int.class, listName);
            return jdbcTemplate.update("INSERT INTO lists (user_id, list_name) VALUES (?, ?)", userId, listName);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server or database.", e);
        } catch (NullPointerException e)  {
            throw new NullPointerException("Null value found");
        }
    }

    @Override
    public void editList(String listName, String updatedListName) {
        try {
            jdbcTemplate.update("UPDATE lists SET list_name = ? WHERE list_name = ?", updatedListName, listName);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server or database.", e);
        } catch (NullPointerException e)  {
            throw new NullPointerException("Null value found");
        }
    }
    @Override
    public void deleteList(String listName) {
        try {
            jdbcTemplate.update("DELETE FROM lists WHERE list_name=?", listName);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server or database.", e);
        }
    }

    //New stuff here
    @Override
    public int getUserIdByUsername(String username) {
        SqlRowSet result = jdbcTemplate.queryForRowSet("SELECT user_id FROM users WHERE username = ?", username);
        if (result.next()) {
            return result.getInt("user_id");
        }
        throw new DaoException("User not found with username: " + username);
    }

    @Override
    public int deleteGame(int gameId) {
        try {
            jdbcTemplate.update("DELETE FROM game_list WHERE game_id = ?", gameId);
            jdbcTemplate.update("DELETE FROM games WHERE game_id = ?", gameId);
            return 1;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server or database.", e);
        }
    }

    @Override
    public void setupUser(int userId) {
        try {
            jdbcTemplate.update("INSERT INTO lists (user_id, list_name) VALUES (?, 'Wishlist');", userId);
            jdbcTemplate.update("INSERT INTO lists (user_id, list_name) VALUES (?, 'Currently Playing');", userId);
            jdbcTemplate.update("INSERT INTO lists (user_id, list_name) VALUES (?, 'Completed');", userId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the server or database.", e);
        }
    }
}
