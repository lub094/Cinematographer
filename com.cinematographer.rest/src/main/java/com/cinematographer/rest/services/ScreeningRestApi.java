package com.cinematographer.rest.services;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

import java.nio.file.attribute.UserPrincipal;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.persistence.annotations.UnionPartitioning;

import com.cinematographer.core.exceptions.InvalidUserException;
import com.cinematographer.core.manager.IServiceManager;
import com.cinematographer.core.manager.ServiceManager;
import com.cinematographer.core.screening.Screening;
import com.cinematographer.core.screening.service.IScreeningService;
import com.cinematographer.core.user.UserProfile;
import com.cinematographer.core.user.service.IUserService;
import com.cinematographer.core.utils.JsonUtils;
import com.cinematographer.rest.utils.ResponseHelper;

@Path("/screenings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScreeningRestApi {

	@GET
	public Response getAllScreenings(/*UserProfile user*/) {
		/*IUserService userService = getServiceManager().findService(
				IUserService.class);
		try {
			userService.authenticate(user.getName(), user.getPassword());
		} catch (InvalidUserException e) {
			return ResponseHelper.createResponse(Status.UNAUTHORIZED);
		}*/

		IScreeningService service = getScreeningService();
		Collection<Screening> screenings = service.getAllScreenings();

		String json = JsonUtils.toJson(screenings);
		return ResponseHelper.createResponse(Status.OK, json);
	}

	private IScreeningService getScreeningService() {
		return getServiceManager().findService(IScreeningService.class);
	}

	@GET
	@Path("/{title}")
	public Response getScreening(@PathParam("title") String title) {
		// TODO: Validate credentials
		IScreeningService service = getScreeningService();
		Screening screening = service.findScreening(title);

		if (null == screening) {
			return ResponseHelper.createResponse(Status.NOT_FOUND);
		}

		String json = JsonUtils.toJson(screening);
		return ResponseHelper.createResponse(Status.OK, json);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createScreening(String payload) {
		try {
			IScreeningService service = getScreeningService();
			Screening screening = JsonUtils.fromJson(payload, Screening.class);

			service.addScreening(screening);
			return ResponseHelper.createResponse(Status.OK);
		} catch (Exception e) {
			// TODO: Catch conflict exceptions
			return ResponseHelper.createResponse(INTERNAL_SERVER_ERROR, e);
		}
	}

	private IServiceManager getServiceManager() {
		return ServiceManager.getInstance();
	}
}
