package nl.geoxplore.geoformats.fme;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import COM.safe.fmeobjects.FMEException;
import COM.safe.fmeobjects.FMEObjects;
import COM.safe.fmeobjects.IFMEFeature;
import COM.safe.fmeobjects.IFMESession;
import COM.safe.fmeobjects.IFMEStringArray;
import COM.safe.fmeobjects.IFMEUniversalReader;
import COM.safe.fmeobjects.IFMEUniversalWriter;
import COM.safe.fmeobjects.IFMEWorkspaceRunner;
import nl.geoxplore.GeoFormats;

public class Converter {

//	private static final String FME_LOG_PATH = GeoFormats.propertyValueFor("fme_log", "/Users/ralph/fme.log");
//	private static final String FMW_PATH = GeoFormats.propertyValueFor("fmw_path", "/Users/ralph/Scripts/fmw/");


	public static void main(String[] args) throws FMEException {
		convert(new Configuration(new File(args[0]), new File(args[2]), args[1], args[3]));
	}

	public static void convert(Configuration config) throws FMEException {
		if (config.getDestType().endsWith(".fmw")) {
			convertFMEWorkbench(config);
		} else {
			convertFMEReaderWriter(config);
		}
	}

	public static void convertFMEWorkbench(Configuration config) throws FMEException {
		String FMW_FMT = GeoFormats.propertyValueFor("fmw_fmt");

		Runtime rt = Runtime.getRuntime();
		String cmd = String.format(FMW_FMT, config.getDestType().split("\\.")[0]);
		String gml = config.getSourceFileName();
		String out = config.getDestFileName();

		try {
			String[] cmdl = { cmd, gml, out };
			System.out.println(String.format("%d <= rt.exec(%s %s %s)", 
					rt.exec(cmdl).waitFor(), cmdl[0], cmdl[1], cmdl[2]));;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void convertFMEReaderWriter(Configuration config) throws FMEException {
		IFMESession session = FMEObjects.createSession();
		IFMEFeature feature = null;

		String FME_LOG_PATH = GeoFormats.propertyValueFor("fme_log");
		
		try {
			IFMEUniversalWriter writer = null;
			IFMEUniversalReader reader = null;

			IFMEStringArray directives = session.createStringArray();
			IFMEStringArray parameters = session.createStringArray();

			directives.append("AUTO_CREATE_LAYERS");
			directives.append("yes");

			session.init(directives);
			session.logFile().setFileName(FME_LOG_PATH, true);

			feature = session.createFeature();
			try {
				reader = session.createReader(config.getSourceType(), true, directives);
				reader.open(config.getSourceFileName(), parameters);

				writer = session.createWriter(config.getDestType(), parameters);
				writer.open(config.getDestFileName(), parameters);

				for (; reader.readSchema(feature); writer.addSchema(feature)) {}
				for (; reader.read(feature); writer.write(feature)) {}
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

	public static void main_(String[] args) throws FMEException {
//		convert(new Configuration(new File(args[0]), new File(args[2]), args[1], args[3]));

		String workDir = "/Users/ralph/Workspaces/geoxplore.nl/klicvectorizer/klicvectorizer-fme/work/";

		IFMESession session = FMEObjects.createSession();
		IFMEStringArray directives = session.createStringArray();
		session.init(directives);

		Map<String, String> allParamValues = new HashMap<>();
		allParamValues.put("GML_INPUT_FILE", workDir + "20C007616_3/GI_gebiedsinformatielevering_20C007616_3.xml");
		allParamValues.put("DXF_DEST_DIR", workDir + "20C007616_3-results");
		allParamValues.put("DGN_DEST_DIR", workDir + "20C007616_3-results");

		String workspace = workDir + "../KV.fmw";
		IFMEWorkspaceRunner runner = session.createWorkspaceRunner();
		IFMEStringArray paramNames = session.createStringArray();
		try {
			runner.getPublishedParamNames(workspace, paramNames);

			// Use the parameter names to retrieve detailed information on the parameters.
			int numParams = paramNames.entries();
			boolean paramOptional[] = new boolean[numParams];
			String paramType[] = new String[numParams];
			String paramValues[] = new String[numParams];
			String paramLabel[] = new String[numParams];

			try {
				for (int i = 0; i < numParams; i++) {
					paramLabel[i] = runner.getParamLabel(workspace, paramNames.getElement(i));
					paramOptional[i] = runner.getParamOptional(workspace, paramNames.getElement(i));
					paramType[i] = runner.getParamType(workspace, paramNames.getElement(i));
					paramValues[i] = runner.getParamValues(workspace, paramNames.getElement(i));

					System.out.println("Label: " + paramLabel[i]);
					System.out.println("Parameter: " + paramNames.getElement(i));
					System.out.println("Optional: " + paramOptional[i]);
					System.out.println("Type: " + paramType[i]);
					System.out.println("Possible values: " + paramValues[i]);
				}

				// Represent our parameters as name/value pairs in an IFMEStringArray.
				IFMEStringArray workspaceParams = session.createStringArray();
				for (int i = 0; i < numParams; i++) {
					String name = paramNames.getElement(i);
					workspaceParams.append(name);
					workspaceParams.append(allParamValues.get(name));
				}

				// We got all our parameter values, now we can run the
				// workspace using those values.
				try {
					System.out.println("Running workspace " + workspace + "...");
					runner.runWithParameters(workspace, workspaceParams);
				} catch (FMEException e) {
					System.out.println(e.getMessage());
				}

				workspaceParams.dispose();

				System.out.println("Workspace has been run.");
			} catch (FMEException e) {
				System.out.println(e.getMessage());
			}
		} catch (FMEException e) {
			System.out.println(e.getMessage());
		}
		paramNames.dispose();
		runner.dispose();
	}

}
