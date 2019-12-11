package nl.geoxplore.geoformats.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

import org.cavalion.util.io.FileUtils;
import org.cavalion.util.io.InputStream;

class FileMessageBodyWriter implements MessageBodyWriter<File> {
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	public long getSize(File file, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return file.length();
	}

	public void writeTo(File file, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
			throws IOException, WebApplicationException {
		InputStream.toOutputStream(new FileInputStream(file), entityStream, 2048);
		entityStream.flush();
		FileUtils.delete(file, true);
	}
}
