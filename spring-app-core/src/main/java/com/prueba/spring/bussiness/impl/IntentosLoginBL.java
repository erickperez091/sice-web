/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.spring.bussiness.impl;

import com.prueba.spring.dao.impl.IntentosLoginDAO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Erick
 */
public class IntentosLoginBL {

    @Autowired
    private IntentosLoginDAO intentosDAO;

    public IntentosLoginDAO getIntentosDAO() {
        return intentosDAO;
    }

    public void setIntentosDAO(IntentosLoginDAO intentosDAO) {
        this.intentosDAO = intentosDAO;
    }

    public void actualizarIntentosFallidos(String usuario) {
        this.getIntentosDAO().actualizarIntentosFallidos(usuario);
    }

    public void reiniciarIntentosFallidos(String usuario){
        this.getIntentosDAO().reiniciarIntentosFallidos(usuario);
    }
}
