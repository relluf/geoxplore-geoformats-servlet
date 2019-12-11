/**
 *
 */
package org.cavalion.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author ralph
 * @since Oct 8, 2010
 *
 */
public class Directory {

	/**
	 *
	 * @param prefix
	 * @param suffix
	 * @param parent
	 * @return
	 * @throws IOException
	 */
	static public File createTempDirectory(String prefix, String suffix)
			throws IOException {
		return createTempDirectory(prefix, suffix, null);
	}

	/**
	 *
	 * @param prefix
	 * @param suffix
	 * @param parent
	 * @return
	 * @throws IOException
	 */
	static public File createTempDirectory(String prefix, String suffix, File parent)
			throws IOException {
		File dir = java.io.File.createTempFile(prefix, suffix, parent);
		dir.delete();
		dir.mkdir();
		return dir;
	}

	/**
	 *
	 * @return
	 * @throws IOException
	 */
	static public File createTempDirectory() throws IOException {
		return createTempDirectory("tmp", "", null);
	}

	/**
	 *
	 * @return
	 * @throws IOException
	 */
	static public File createTempDirectory(File parent) throws IOException {
		return createTempDirectory("tmp", "", parent);
	}

	/**
	 *
	 * @param dir
	 * @return
	 */
	static public boolean deleteDirectory(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDirectory(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		return dir.delete();
	}

	/**
	 *
	 * @param src
	 * @param dest
	 * @throws IOException
	 */
	static public void copy(File src, File dest) throws IOException {
		if (src.isDirectory()) {
			// if directory not exists, create it
			if (!dest.exists()) {
				dest.mkdir();
			}

			// list all the directory contents
			String files[] = src.list();

			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				// recursive copy
				copy(srcFile, destFile);
			}

		} else {
			FileInputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);
			try {
				byte[] buffer = new byte[1024];
				int length;
				// copy the file content in bytes
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
			} finally {
				in.close();
				out.close();
			}
		}
	}

	/**
	 *
	 * @param zipFolder
	 * @return
	 * @throws IOException
	 */
	static public File zip(File zipFolder) throws IOException {
		File dest = File.createTempFile("folder", ".zip");
		zip(zipFolder, dest);
		return dest;
	}

	/**
	 *
	 * @param src
	 * @return
	 * @throws IOException
	 */
	static public void zip(File folder, File dest) throws IOException {
		FileOutputStream out = new FileOutputStream(dest);
		ZipOutputStream zip = new ZipOutputStream(out);
		try {
			addFolderToZip(folder, zip, folder.getAbsolutePath() + File.separator);
		} finally {
			zip.close();
			out.close();
		}
	}

	/**
	 *
	 * @param folder
	 * @param zip
	 * @param baseName
	 * @throws IOException
	 */
	private static void addFolderToZip(File folder, ZipOutputStream zip, String baseName) throws IOException {
		File[] files = folder.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				addFolderToZip(file, zip, baseName);
			} else {
				String name = file.getAbsolutePath().substring(
						baseName.length());

				ZipEntry zipEntry = new ZipEntry(name);
				zip.putNextEntry(zipEntry);
				InputStream
						.toOutputStream(new FileInputStream(file), zip, 1024);
				zip.closeEntry();
			}
		}
	}
}
