package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {

    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }
    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getUserList(Principal principal){
        List<User> userList;
        User user = userDao.getUserByUsername(principal.getName());
        userList = userDao.getUsers();
        userList.remove(user);
        return userList;
    }
}
