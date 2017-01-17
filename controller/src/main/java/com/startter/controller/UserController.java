package com.startter.controller;

import com.startter.common.IController;
import com.startter.common.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by davidqi on 1/16/17.
 */
@RestController
public class UserController implements IController {
    @Autowired
    private IRepository userRepository;

    @Value("${app.version}")
    private String appVersion;
    @Value("${app.author}")
    private String appAuthor;

    @RequestMapping("/")
    public String homePage() {
        return "Hello World!" + appAuthor + appVersion;
    }

}