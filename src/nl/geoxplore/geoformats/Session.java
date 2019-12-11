package nl.geoxplore.geoformats;

import com.oreilly.servlet.multipart.FilePart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.servlet.http.HttpSession;
import nl.geoxplore.geoformats.Session;
import nl.geoxplore.geoformats.Source;
import org.cavalion.util.io.Directory;
import org.cavalion.util.io.FileUtils;
import org.cavalion.util.io.InputStream;

public class Session {

	private static Map<HttpSession, Session> sessions = new HashMap<>();
	private File workingDir;
	private List<Source> sources;

	public static Session get(HttpSession session) throws IOException {
		Session sess = sessions.get(session);
		if (sess == null) {
			sess = new Session();
			sessions.put(session, sess);
			session.setAttribute(Session.class.getName(), sess);
		}
		return sess;
	}

	public Session() throws IOException {
		this.workingDir = Directory.createTempDirectory("geoformats-", ".session");
		this.sources = new ArrayList<>();
	}

	public void finalize() {
		FileUtils.delete(this.workingDir, true);
	}

	public void clearSources() {
		this.sources = new ArrayList<>();
		byte b;
		int i;
		String[] arrayOfString;
		for (i = (arrayOfString = this.workingDir.list()).length, b = 0; b < i;) {
			String name = arrayOfString[b];
			FileUtils.delete(new File(this.workingDir, name), true);
			b++;
		}

	}

	public void addSource(FilePart part) throws IOException {
		File dir = Directory.createTempDirectory(this.workingDir);
		if (part.getContentType().equals("application/zip")) {
			ZipInputStream is = new ZipInputStream(part.getInputStream());
			try {
				ZipEntry entry;
				while ((entry = is.getNextEntry()) != null) {
					File file = new File(String.format("%s/%s", new Object[] { dir, entry.getName() }));
					file.getParentFile().mkdirs();
					if (!entry.isDirectory()) {
						FileOutputStream os = new FileOutputStream(file);
						try {
							InputStream.toOutputStream(is, os, 2048);
						} finally {
							os.close();
						}
						continue;
					}
					if (!file.exists()) {
						file.mkdirs();
					}
				}

				entry = is.getNextEntry();
			} finally {
				is.close();
			}
		} else {
			FileOutputStream fos = new FileOutputStream(new File(dir, part.getFileName()));
			try {
				InputStream.toOutputStream(part.getInputStream(), fos, 4 * 1024);
			} finally {
				fos.close();
			}
		}
		this.sources.add(new Source(part.getFileName(), dir));
	}

	public Source getSource(String name) {
		for (Source source : this.sources) {
			if (source.getName().equals(name)) {
				return source;
			}
		}
		return null;
	}

	public List<String> listSources() {
		List<String> sources = new ArrayList<>();

		for (Source source : this.sources) {
			sources.add(source.getName());
		}

		return sources;
	}

	public Object listDatasets() {
		List<String> datasets = new ArrayList<>();

		for (Source source : this.sources) {
			byte b;
			int i;
			String[] arrayOfString;
			for (i = (arrayOfString = source.listDatasets()).length, b = 0; b < i;) {
				String name = arrayOfString[b];
				datasets.add(String.format("%s/%s", new Object[] { source.getName(), name }));
				b++;
			}

		}
		return datasets;
	}

	public List<Source> getSources() {
		return this.sources;
	}
}
