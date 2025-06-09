package com.heartwoodlabs.dashboard.rs;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Utility {
    public static Response buildOkResponse(Object entity) {
        return Response
                .status(Response.Status.OK)
                .entity(entity)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }//200 - GET (Ok)

    public static Response buildBadRequestResponse(Object entity) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(entity)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }//400 - (Bad Request)

    public static Response buildNoContentResponse() {
        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }//204 - PUT-DELETE (No Content)

    public static Response buildCreatedResponse() {
        return Response
                .status(Response.Status.CREATED)
                .build();
    }//201 - POST (Created)

    public static Response buildNotFoundResponse() {
        return Response
                .status(Response.Status.NOT_FOUND)
                .build();
    } //404 - (Not Found)

    public static Response buildServerError(String message) {
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(message)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }//500 - (Internal Server Error)
}
