/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magma.main.WebInterface;


import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author HebaKek
 */
@ApplicationPath("Api")
public class ContentService extends Application {
    @Context
    protected UriInfo context;

    public ContentService() {

    }

}
