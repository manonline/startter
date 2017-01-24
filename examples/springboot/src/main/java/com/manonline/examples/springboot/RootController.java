package com.manonline.examples.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by davidqi on 1/16/17.
 */
@RestController
@RequestMapping(value = "/")
public class RootController {

    @RequestMapping
    public String rootPage() {
        return "MyApp is Alive";
    }

}