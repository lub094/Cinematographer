package com.cinematographer.rest.utils;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ResponseHelper {

	public static Response createResponse(Status status, String json) {
		return Response.status(status).entity(json).build();
	}

	public static Response createResponse(Status status) {
		return Response.status(status).build();
	}

	public static Response createResponse(Status status, Exception e) {
		return Response.status(status).entity(new ErrorMessage(e)).build();
	}
}
