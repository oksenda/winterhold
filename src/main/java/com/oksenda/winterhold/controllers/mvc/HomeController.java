package com.oksenda.winterhold.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/home")
public class HomeController {
    @GetMapping("")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("layout");
        modelAndView.addObject("contentFragment", "home/index :: content");
        modelAndView.addObject("title","Home Page");
        return modelAndView;
    }
}
