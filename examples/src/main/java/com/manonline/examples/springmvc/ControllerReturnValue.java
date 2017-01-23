package com.manonline.examples.springmvc;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by davidqi on 1/22/17.
 */
public class ControllerReturnValue {

    // return ModelAndView
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/user/index");
        modelAndView.addObject("xxx", "xxx");
        return modelAndView;
    }

    // return View

    // return String
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        String retVal = "/user/index";
        model.addAttribute("xxx", "xxx");
        return retVal;
    }

    // return Void

    // return Model

    // return ModelMap

    // return Map
    @RequestMapping(method = RequestMethod.GET)
    public Map<String, Object> index1() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("xxx", "xxx");
        // map.put is same as request.Add
        return map;
    }
}