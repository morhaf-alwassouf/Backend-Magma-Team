/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magma.main.Models;


/**
 * This class represent a response to the client side from the API.
 * It's a sub class of the super class {@link ComposedResponse}.
 * <p>The data object in this class represents an {@link Iterable}, so this class is usually used when the response contains a list of objects.</p>
 *
 * @author Ali Issa Wassouf
 */
public class MultipleObjectsResponse<T extends Object, K, N> extends ComposedResponse {


    //*******************************************
    // Private Attributes
    //*******************************************

    /**
     * An object that holds a list of objects in the response.
     */
    private T data;

    /**
     * An object that represents the message in the response.
     */
    private K message;
    private N totalCount;

    /**
     * Creates an instance of {@link MultipleObjectsResponse} with a code which is passed to the super class, a message, and a Iterable instance which holds a list of objects.
     *
     * @param code    code of the response
     * @param message message of the response
     * @param data    contains the data fetched form the database.
     * @see SingleObjectResponse#SingleObjectResponse(int, Object, DomainObject)
     */
    public MultipleObjectsResponse(int code, K message, T data, N totalCount) {

        super(code);

        this.totalCount = totalCount;
        this.message = message;
        this.data = data;
    }

    public N getTotalCount() {
        return totalCount;
    }


    //*******************************************
    // Constructors
    //*******************************************

    /**
     * An object that represents the total count for the returned list in the response.
     */
    public void setTotalCount(N totalCount) {
        this.totalCount = totalCount;
    }


    //*******************************************
    // Public Methods
    //*******************************************

    /**
     * Returns the data object in the {@link MultipleObjectsResponse}
     *
     * @return data {@link java.util.Map}
     */
    public T getData() {
        return data;
    }

    /**
     * Sets the data object in the {@link MultipleObjectsResponse}
     *
     * @param data the list of objects.
     */

    public void setData(T data) {
        this.data = data;
    }

    /**
     * Returns the message in the {@link MultipleObjectsResponse}
     *
     * @return message {@link K}
     */

    public K getMessage() {
        return message;
    }

    /**
     * Sets the message object to a passed value in {@link MultipleObjectsResponse}
     *
     * @param message the message to be returned in the response.
     */
    public void setMessage(K message) {
        this.message = message;
    }
}
