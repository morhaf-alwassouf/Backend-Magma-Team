/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magma.main.Models;


/**
 * This is the <b>super class</b> in the <i>Custom response hierarchy</i>.
 * This class has the code part of the response returned to the client side.
 *
 * @author Ali Issa Wassouf
 */
public class ComposedResponse {

    //*******************************************
    // Private Attributes
    //*******************************************
    /**
     * The code of the response returned to the client side the API.
     */
    int code;


    //*******************************************
    // Constructors
    //*******************************************

    /**
     * Default Constructor
     *
     * @see #ComposedResponse(int)
     */
    public ComposedResponse() {
    }

    /**
     * Creates a new instance of {@link ComposedResponse}
     *
     * @param code this is the code part of the response returned to the client of the API
     * @see #ComposedResponse()
     */
    public ComposedResponse(int code) {
        this.code = code;
    }


    //*******************************************
    // Public Methods
    //*******************************************

    /**
     * Returns the code object in {@link ComposedResponse}
     *
     * @return code - {@link String}
     */
    public int getCode() {
        return code;
    }


    /**
     * Sets the code object to a value in {@link ComposedResponse}
     *
     * @param code this is the code part of the response returned to the client of the API
     */
    public void setCode(int code) {
        this.code = code;
    }


}
