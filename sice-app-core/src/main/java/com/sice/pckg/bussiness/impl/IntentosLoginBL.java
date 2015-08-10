/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sice.pckg.bussiness.impl;

import com.sice.pckg.dao.impl.IntentosLoginDAO;
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
        try {
            this.getIntentosDAO().actualizarIntentosFallidos(usuario);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void reiniciarIntentosFallidos(String usuario) {
        this.getIntentosDAO().reiniciarIntentosFallidos(usuario);
    }
}
