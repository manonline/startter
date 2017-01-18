package com.manonline.myapp.controller;

import com.manonline.myapp.common.IController;
import com.manonline.myapp.common.IRepository;
import com.manonline.myapp.entity.User;
import com.manonline.myapp.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * Created by davidqi on 1/16/17.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController implements IController {

    @RequestMapping
    public String rootPage() {
        return "User Home Page";
    }

    @RequestMapping(value = "get")
    public Map<String, Object> getByIdInParam(@RequestParam int id) {
        // validate the parameters

        // get the stuff
        User user = userFinder(id, null, null);

        // compose the response
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", user.getId());
        map.put("name", user.getName());
        map.put("date", user.getDate());

        // return a map that will be parsed by jackson-databind
        return map;
    }

    @RequestMapping(value = "get/{id}")
    public User getByIdInPath(@PathVariable int id) {
        // validate the variables

        // get the stuff
        User user = userFinder(id, null, null);

        // compose the response

        // return an user object that will be parsed by jackson-databind
        return user;
    }

    private User userFinder(int id, String name, Date date) {
        // validate parameters

        // get the stuff
        User user = new User();
        user.setId(id);
        user.setName(isNull(name) ? "generated" : name);
        user.setDate(isNull(date) ? new Date() : date);

        return user;
    }
}