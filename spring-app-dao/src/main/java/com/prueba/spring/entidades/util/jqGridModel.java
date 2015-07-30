/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.spring.entidades.util;

import java.util.List;

/**
 *
 * @author erick
 */
public class jqGridModel<T> {
    private int page;
    private int total;
    private int records;
    private List<T> rows;

    public jqGridModel() {
    }

    public jqGridModel(int page, int total, int records, List<T> rows) {
        this.page = page;
        this.total = total;
        this.records = records;
        this.rows = rows;
    }

    /**
     * @return Retorna la página actual
     */
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    /**
     * @return Retorna total de páginas
     */
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * @return Retorna el total de Registros
     */
    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    /**
     * @return Retorna Lista que contiene los datos
     */
    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
