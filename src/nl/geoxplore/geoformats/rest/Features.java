package nl.geoxplore.geoformats.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.cavalion.util.io.Directory;
import org.cavalion.util.io.FileUtils;
import org.cavalion.util.io.InputStream;

import COM.safe.fmeobjects.FMEException;
import nl.geoxplore.geoformats.Session;
import nl.geoxplore.geoformats.Source;
import nl.geoxplore.geoformats.fme.Configuration;
import nl.geoxplore.geoformats.fme.Converter;

@Path("features")
public class Features {
	@Context
	private HttpServletRequest request;

//	@Context
//	private UriInfo urlInfo;

	@GET
	@Produces({ "application/json" })
	public Response get(@QueryParam("format") String format) throws IOException, FMEException {
		File destDir = Directory.createTempDirectory("geoformats-", ".dest");
		Session session = Session.get(this.request.getSession());

		if (format == null || format.equals("")) {
			format = "SHAPE";
		}

		try {
			for (Source source : session.getSources()) {
				File dir = source.getDirectory();
				byte b;
				int j;
				String[] arrayOfString;
				for (j = (arrayOfString = source.listDatasets()).length, b = 0; b < j;) {
					String name = arrayOfString[b];
					File sourceFile = new File(dir, name);
					File destFile = new File(destDir, name);

					int i = 2;
					while (destFile.exists()) {
						destFile = new File(destDir, String.format("%s-%d", name, Integer.valueOf(i++)));
					}
					String sourceType = Converter.getType(sourceFile);
					Converter.convert(new Configuration(sourceFile, destFile, sourceType, format));
					b++;
				}

			}
			final File zip = File.createTempFile("geoformats-", ".zip");
			FileUtils.zip(destDir, zip, true);

			StreamingOutput stream = new StreamingOutput() {

				public void write(OutputStream output) throws IOException, WebApplicationException {
					FileInputStream fis = new FileInputStream(zip);
					try {
						InputStream.toOutputStream(fis, output, 2048);
					} finally {
						fis.close();
						FileUtils.delete(zip, true);
					}
				}
			};

			Response response = Response.ok(stream, "application/zip").build();
			FileUtils.delete(destDir, true);
			return response;
		} finally {
			FileUtils.delete(destDir, true);
		}
	}

	@GET
	@Path("settings")
	@Produces({ "application/json" })
	public Object getSettings() {
		return null;
	}

	@PUT
	@Path("settings")
	@Produces({ "application/json" })
	public Object putSettings() {
		return null;
	}
}
