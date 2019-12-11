/**
 *
 */
package org.cavalion.util.io;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author ralph
 * @since Jul 18, 2008
 *
 */
public class FileCopy {

	/**
	 *
	 * @param src
	 * @param dest
	 * @return
	 * @throws IOException
	 * @deprecated Use {@link org.cavalion.util.io.File#copy(File,File)} instead
	 */
	static public File copy(File src, File dest) throws IOException {
		return FileUtils.copy(src, dest);
	}
}
