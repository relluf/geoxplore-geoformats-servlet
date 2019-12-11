/**
 *
 */
package org.cavalion.util.io;

import java.io.IOException;

/**
 * @author ralph
 * @since Oct 12, 2006
 *
 */
public class InputStream {

	/**
	 *
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String slurp(java.io.InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	/**
	 *
	 * @param stream
	 * @return
	 * @throws IOException
	 */
	static public String readln(java.io.InputStream stream) throws IOException {
		StringBuffer buffer = new StringBuffer();
		char ch = '\0';
		while (ch != '\n' && stream.available() > 0) {
			ch = (char) stream.read();
			if (ch != '\r' && ch != '\n') {
				buffer.append(ch);
			}
		}
		return buffer.toString();
	}

	/**
	 *
	 * @param in
	 * @param out
	 * @param bufferSize
	 * @throws IOException
	 */
	static public void toOutputStream(java.io.InputStream in,
			java.io.OutputStream out, int bufferSize) throws IOException {
		byte[] b = new byte[bufferSize];
		int read = in.read(b);
		while (read != -1) {
			out.write(b, 0, read);
			read = in.read(b);
		}
	}
}
