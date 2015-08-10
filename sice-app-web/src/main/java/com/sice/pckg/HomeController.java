/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sice.pckg;

import com.sice.pckg.bussiness.impl.IntentosLoginBL;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
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

    @Autowired
    IntentosLoginBL intentosBL;

    public IntentosLoginBL getIntentosBL() {
        return intentosBL;
    }

    public void setIntentosBL(IntentosLoginBL intentosBL) {
        this.intentosBL = intentosBL;
    }

    @RequestMapping(value = "/Principal", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, Principal principal) {
        this.getIntentosBL().reiniciarIntentosFallidos(principal.getName());
        return new ModelAndView("Principal", "usuario", principal.getName());
    }

    @RequestMapping(value = {"", "/", "/Login"}, method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("Login");
    }

    @RequestMapping(value = "/BadCredentials")
    public ModelAndView badCredentials(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        BadCredentialsException exception = session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION") == null ? new BadCredentialsException("") : (BadCredentialsException) session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        String username = exception.getAuthentication().getPrincipal().toString();
        try {
            this.getIntentosBL().actualizarIntentosFallidos(username);
            return new ModelAndView("redirect:/Home/Login?error=userCredentials");
        } catch (Exception ex) {
            session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", ex);
            return new ModelAndView("redirect:/Home/Login?error=userLocked");
        }
    }

    @RequestMapping(value = "/Logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("Login?Logout=true");
    }

    @RequestMapping(value = "/Forbidden", method = RequestMethod.GET)
    public ModelAndView forbidden(HttpServletRequest request, HttpServletResponse response, Principal principal) {
        //return "redirect:/Home/Principal?forbidden=true"
        //return new ModelAndView("redirect:/Home/Prohibido");
        return new ModelAndView("redirect:/Home/Principal?forbidden=true");
    }
}
