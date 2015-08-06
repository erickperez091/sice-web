/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        int numero = request.getParameter("error") == null ? 0 : Integer.parseInt(request.getParameter("error"));
        String txt = "";
        if(numero != 0){
             txt = request.getAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) == null ? "" : request.getAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).toString();
             txt = request.getParameter("j_username");
            //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            //String user = auth.getPrincipal().toString();
        }
        String exception = request.getAttribute("SPRING_SECURITY_LAST_EXCEPTION") == null ? "" : request.getAttribute("SPRING_SECURITY_LAST_EXCEPTION").toString();
        return new ModelAndView("Login");
    }

    @RequestMapping(value = "/BadCredentials")
    public ModelAndView badCredentials(HttpServletRequest request, HttpServletResponse response) {
        String username = (String)request.getAttribute("username");
        return new ModelAndView("Login");
    }

    @RequestMapping(value = "/Logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("Login");
    }
}
