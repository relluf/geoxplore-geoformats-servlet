package nl.geoxplore.geoformats.fme;

import java.io.File;

import COM.safe.fmeobjects.FMEException;
import COM.safe.fmeobjects.FMEObjects;
import COM.safe.fmeobjects.IFMEFeature;
import COM.safe.fmeobjects.IFMESession;
import COM.safe.fmeobjects.IFMEStringArray;
import COM.safe.fmeobjects.IFMEUniversalReader;
import COM.safe.fmeobjects.IFMEUniversalWriter;

public class Converter {

	public static void main(String[] args) throws FMEException {
		convert(new Configuration(new File(args[0]), new File(args[2]), args[1], args[3]));
	}

	public static void convert(Configuration config) throws FMEException {
		IFMESession session = FMEObjects.createSession();
		IFMEFeature feature = null;

		try {
			IFMEUniversalWriter writer = null;
			IFMEUniversalReader reader = null;

			IFMEStringArray directives = session.createStringArray();
			IFMEStringArray parameters = session.createStringArray();

			session.init(directives);
			session.logFile().setFileName("/Users/ralph/fme.log", true);

			feature = session.createFeature();
			try {
				reader = session.createReader(config.getSourceType(), true, directives);
				reader.open(config.getSourceFileName(), parameters);

				writer = session.createWriter(config.getDestType(), parameters);
				writer.open(config.getDestFileName(), parameters);

				for (; reader.readSchema(feature); writer.addSchema(feature)) {
				}
				for (; reader.read(feature); writer.write(feature)) {
				}
			} finally {
				reader.close();
				writer.close();
				parameters.dispose();
				directives.dispose();
				if (reader != null) {
					reader.dispose();
				}
				if (writer != null) {
					writer.dispose();
				}
			}
		} finally {
			if (feature != null) {
				feature.dispose();
			}
			session.dispose();
		}
	}

	public static String getType(File file) {
		String split[] = file.getName().split("\\.");
		String ext = split[split.length - 1].toLowerCase();
		if (ext.equals("xml")) {
			return "GML";
		}
		if (ext.equals("dxf") || ext.equals("dwg")) {
			return "ACAD";
		}
		if (ext.equals("dgn")) {
			return "IGDS";
		} else {
			return null;
		}
	}

}
