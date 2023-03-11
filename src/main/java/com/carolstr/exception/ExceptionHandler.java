package com.carolstr.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        if(exception instanceof PautaInvalidaException){
            return Response.status(Response.Status.NOT_FOUND).entity(ExceptionResponse.builder().mensagem(exception.getMessage()).build()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
    }

}
