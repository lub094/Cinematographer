package com.cinematographer.rest.services;

import com.cinematographer.core.manager.IServiceManager;
import com.cinematographer.core.manager.ServiceManager;
import com.cinematographer.core.user.Role;
import com.cinematographer.core.user.UserProfile;
import com.cinematographer.core.user.service.IUserService;
import com.cinematographer.core.utils.JsonUtils;
import com.cinematographer.rest.utils.ResponseHelper;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserRestAPI {
	private IServiceManager getServiceManager() {
		return ServiceManager.getInstance();
	}

	private IUserService getUserService() {
		return getServiceManager().findService(IUserService.class);
	}

	@POST
	public Response authorize(String payload, Role role) {
		try {
			IUserService service = getUserService();
			UserProfile user = JsonUtils.fromJson(payload, UserProfile.class);

			service.authorize(user, role);
			return ResponseHelper.createResponse(Status.OK);
		} catch (Exception e) {
			return ResponseHelper.createResponse(Status.FORBIDDEN, e);
		}
	}

	@GET
	@Path("/{name}")
	public Response findUser(@PathParam("name") String name) {
		IUserService service = getUserService();
		UserProfile user = service.findUser(name);

		if (null == user) {
			return ResponseHelper.createResponse(Status.OK);
		}

		String json = JsonUtils.toJson(user);
		return ResponseHelper.createResponse(Status.OK, json);
	}

	@POST
	public Response createUser(String payload) {
		try {
			IUserService service = getUserService();
			UserProfile user = JsonUtils.fromJson(payload, UserProfile.class);

			service.addUser(user);
			return ResponseHelper.createResponse(Response.Status.OK);
		} catch (Exception e) {
			return ResponseHelper.createResponse(Status.INTERNAL_SERVER_ERROR, e);
		}
	}

	@POST
	public Response authenticateUser(String payload) {
		try {
			IUserService service = getUserService();
			UserProfile user = JsonUtils.fromJson(payload, UserProfile.class);

			service.authenticate(user.getName(), user.getPassword());
			return ResponseHelper.createResponse(Response.Status.OK);
		} catch (Exception e) {
			return ResponseHelper.createResponse(Status.BAD_REQUEST, e);
		}
	}
}
