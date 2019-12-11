package nl.geoxplore.geoformats.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.cavalion.util.Utils;

import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.Part;

import nl.geoxplore.geoformats.Session;

@Path("/sources")
public class Sources {
	
	@Context
	private HttpServletRequest request;
	
//	@Context
//	private UriInfo urlInfo;

	@GET
	@Produces({ "application/json" })
	public Object get() throws IOException {
		Session session = Session.get(this.request.getSession());
		return session.listSources();
	}

	@DELETE
	@Produces({ "application/json" })
	public Object delete() throws IOException {
		Session session = Session.get(this.request.getSession());
		session.clearSources();
		return Utils.mapOf(new Object[0]);
	}

	@POST
	@Produces({ "application/json" })
	public Object post() throws IllegalStateException, IOException, ServletException {
		MultipartParser mpp = new MultipartParser(this.request, 1073741824);

		Session session = Session.get(this.request.getSession());
		Part part;
		while ((part = mpp.readNextPart()) != null) {
			if (part instanceof FilePart) {
				session.addSource((FilePart) part);
			}
		}
		return get();
	}

	@GET
	@Path("{sourceName}/datasets")
	@Produces({ "application/json" })
	public Object getDatasets(@PathParam("sourceName") String sourceName) throws IOException {
		return Session.get(this.request.getSession()).getSource(sourceName).listDatasets();
	}
}
