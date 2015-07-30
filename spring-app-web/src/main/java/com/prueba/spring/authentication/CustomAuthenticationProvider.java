/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.spring.authentication;

import com.prueba.spring.dao.IDAO;
import com.prueba.spring.dao.impl.UsuarioDAO;
import com.prueba.spring.entidades.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 *
 * @author ERICK
 */
//public class CustomAuthenticationProvider extends DaoAuthenticationProvider {
public class CustomAuthenticationProvider implements AuthenticationProvider{

    private SaltSource saltSource;
    private MessageDigestPasswordEncoder passwordEncoder;
    
    @Autowired
    private IDAO usuarioDAO;
    
    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        String name = a.getName();
        String pass = a.getCredentials().toString();
        if (name.equals("admin") && pass.equals("admin")) {
            Usuario usuario = (Usuario)((UsuarioDAO)usuarioDAO).loadUserByUsername(name);
            List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            Authentication auth = new UsernamePasswordAuthenticationToken(name, pass, grantedAuths);
            return auth;
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public MessageDigestPasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(MessageDigestPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void setSaltSource(SaltSource saltSource) {
        this.saltSource = saltSource;
    }

    public SaltSource getSaltSource() {
        return saltSource;
    }

    public IDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(IDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }
    
    
}
