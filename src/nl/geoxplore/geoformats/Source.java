package nl.geoxplore.geoformats;

import java.io.File;

public class Source {
	private String name;
	private String type;
	private File directory;

	public Source(String name, String type, File directory) {
		this.directory = directory;
		this.name = name;
		this.type = type;
	}

	public Source(String name, File directory) {
		this.directory = directory;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public File getDirectory() {
		return directory;
	}

	public String[] listDatasets() {
		return directory.list();
	}
}
