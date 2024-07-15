package com.oksenda.winterhold.controllers.mvc;

import com.oksenda.winterhold.dtos.LoginRequestDto;
import com.oksenda.winterhold.dtos.RegisterRequestDto;
import com.oksenda.winterhold.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthMvcController {
    private final AuthService authService;

    public AuthMvcController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public ModelAndView login(Authentication authentication, @RequestParam(required = false) Boolean error,
                              @RequestParam(required = false) Boolean timeout,
                              @RequestParam(required = false) Boolean expired){
        if (authentication != null){
            return new ModelAndView("redirect:/");
        }
        ModelAndView modelAndView = new ModelAndView("auth");
        modelAndView.addObject("contentFragment", "auth/login-form :: content");
        modelAndView.addObject("error",error);
        modelAndView.addObject("expired",expired);
        modelAndView.addObject("timeout",timeout);
        modelAndView.addObject("dto",LoginRequestDto.builder().build());
        return modelAndView;
    }
    @GetMapping("register")
    public ModelAndView register(){
        ModelAndView modelAndView = new ModelAndView("auth");
        modelAndView.addObject("contentFragment", "auth/register-form :: content");
        modelAndView.addObject("dto", RegisterRequestDto.builder().build());
        return modelAndView;
    }

    @PostMapping("register")
    public ModelAndView registerSave(@Valid @ModelAttribute("dto") RegisterRequestDto dto, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("auth");
            modelAndView.addObject("contentFragment", "auth/register-form :: content");
            modelAndView.addObject("dto", dto);
            return modelAndView;
        }
        authService.register(dto);
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("")
    public ModelAndView redirectAfterLoging(Authentication authentication){
        ModelAndView modelAndView =new ModelAndView("redirect:/home");
        return modelAndView;
    }
}
