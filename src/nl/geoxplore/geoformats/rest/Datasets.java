package nl.geoxplore.geoformats.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import nl.geoxplore.geoformats.Session;

@Path("/datasets")
public class Datasets {
	
	@Context
	private HttpServletRequest request;
//	@Context
//	private UriInfo urlInfo;

	@GET
	@Produces({ "application/json" })
	public Object get() throws IOException {
		Session session = Session.get(this.request.getSession());
		return session.listDatasets();
	}
}
