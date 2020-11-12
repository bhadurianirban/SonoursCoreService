/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sonorus.core.service;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author bhaduri
 */
@Path("bheu")
public class BheuResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of BheuResource
     */
    public BheuResource() {
    }

    /**
     * Retrieves representation of an instance of org.dgrf.fractalservice.BheuResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getJson() {
        //TODO return proper representation object
        return "bheu bheu";
    }

}
