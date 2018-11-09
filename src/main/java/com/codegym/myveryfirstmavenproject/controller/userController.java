package com.codegym.myveryfirstmavenproject.controller;

import com.codegym.myveryfirstmavenproject.model.Customer;
import com.codegym.myveryfirstmavenproject.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/")
public class userController {
    @GetMapping("/")
    public String index(HttpServletRequest request) {
        if (isRemembered()) {
            if (request.isUserInRole("ADMIN")) {
                return "redirect:/admin/dashboard";
            }
            if (request.isUserInRole("USER")) {
                return "redirect:/user/dashboard";
            }
        }
        return "index";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/goodbye")
    public String goodbye() {
        return "goodbye";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin";
    }

    @GetMapping("/user/dashboard")
    public String userDashboard() {
        return "user";
    }

    private boolean isRemembered() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        return RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
    }

    @Autowired
    CustomerService customerService;

    @GetMapping("/user/customers")
    public ModelAndView customerList(ModelAndView modelAndView, Pageable pageable) {
        modelAndView.setViewName("/customerManagement/customerList");
        Page<Customer> customers = customerService.findAll(pageable);
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }
}
