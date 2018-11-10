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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

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
    public String customerList(Model model, Pageable pageable, Optional<String> name) {
        Page<Customer> customers;
        if (name.isPresent()) {
            customers = customerService.findByName(name.get(), pageable);
            model.addAttribute("name", name.get());
        } else if (model.containsAttribute("name")) {
            customers = customerService.findByName(name.get(), pageable);
        } else {
            customers = customerService.findAll(pageable);
        }
        model.addAttribute("customers", customers);
        return "/customerManagement/customerList";
    }


//    @PostMapping("/user/customers")
//    public ModelAndView customerSort(@RequestParam("name") String name, ModelAndView modelAndView, Pageable pageable) {
////        Page<Customer> customers = customerService.findByName(name, pageable);
//        Page<Customer> customers = customerService.findAll(pageable);
//        System.out.println(name);
//        modelAndView.addObject("customers", customers);
//        return modelAndView;
//    }
}
