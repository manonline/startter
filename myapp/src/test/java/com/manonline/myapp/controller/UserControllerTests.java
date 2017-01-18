package com.manonline.myapp.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by davidqi on 1/18/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTests {
    private MockMvc mvc;

    @Before
    public void before() {
        this.mvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
    }

    @Test
    public void contextLoads() throws Exception {
        // construct request
        RequestBuilder req = get("/user");

        // send request and get/validate response
        mvc.perform(req).andExpect(status().isOk())
                        .andExpect(content().string("User Home Page"));
    }
}