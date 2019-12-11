package org.cavalion.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtils {

	/**
	 *
	 * @param src
	 * @param dest
	 * @return
	 * @throws IOException
	 */
	static public File copy(File src, File dest) throws IOException {
		FileInputStream from = new FileInputStream(src);
		FileOutputStream to = new FileOutputStream(dest);
		byte[] buffer = new byte[4096];
		int bytesRead;
		while ((bytesRead = from.read(buffer)) != -1) {
			to.write(buffer, 0, bytesRead); // write
		}
		from.close();
		to.close();
		return dest;
	}

	/**
	 *
	 * @param file
	 * @param recursively
	 */
	static public void delete(File file, boolean recursively) {
		if (recursively && file.isDirectory()) {
			for (String name : file.list()) {
				delete(new File(file, name), recursively);
			}
		}
		file.deleteOnExit();
		file.delete();
	}

	/**
	 *
	 * @param src
	 * @param dest
	 * @param recursively
	 * @throws IOException
	 */
	static public void zip(File src, File dest, boolean recursively)
			throws IOException {

		FileOutputStream fos = new FileOutputStream(dest);
		try {
			zip(src, fos, recursively);
		} finally {
			fos.close();
		}
	}

	/**
	 *
	 * @param src
	 * @param fos
	 * @param recursively
	 * @throws IOException
	 */
	static public void zip(File src, OutputStream fos, boolean recursively)
			throws IOException {

		List<File> files = new ArrayList<>();
		int start;

		if (src.isDirectory()) {
			files.addAll(Arrays.asList(src.listFiles()));
			start = src.getAbsolutePath().length() + 1;
		} else {
			files.add(src);
			start = 0;
		}

		ZipOutputStream zos = new ZipOutputStream(fos);
		try {
			while (files.size() > 0) {
				File file = files.remove(0);
				if (file.isDirectory() && recursively) {
					files.addAll(Arrays.asList(file.listFiles()));
				} else {
					FileInputStream fis = new FileInputStream(file);
					try {
						zos.putNextEntry(new ZipEntry(file
								.getAbsolutePath().substring(start)));
						InputStream.toOutputStream(fis, zos, 2048);
						zos.closeEntry();
					} finally {
						fis.close();
					}
				}
			}
		} finally {
			zos.close();
		}
	}

}
