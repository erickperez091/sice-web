/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author eperez
 */
@RestController
@RequestMapping(value = "/Home")
public class HomeController {

    @RequestMapping(value = "/Principal", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("Principal");
    }

    @RequestMapping(value = "/Login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response)
    {
        return new ModelAndView("Login");
    }
    
    @RequestMapping(value = "/Logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response)
    {
        return new ModelAndView("Login");
    }
}
