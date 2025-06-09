package com.heartwoodlabs.dashboard.rs;

import com.heartwoodlabs.dashboard.business.InvalidDataException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidDataExceptionMapper implements ExceptionMapper<InvalidDataException> {
    @Override
    public Response toResponse(InvalidDataException exception) {
        return Utility.buildBadRequestResponse(exception.getMessage());
    }
}
