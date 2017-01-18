package com.manonline.myapp.controller;

import com.manonline.myapp.common.IController;
import com.manonline.myapp.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by davidqi on 1/16/17.
 */
@RestController
@RequestMapping(value = "/")
public class RootController implements IController {

    @Value("${app.version}")
    private String appVersion;
    @Value("${app.author}")
    private String appAuthor;

    @RequestMapping
    public String rootPage() {
        return "MyApp is Alive";
    }

}