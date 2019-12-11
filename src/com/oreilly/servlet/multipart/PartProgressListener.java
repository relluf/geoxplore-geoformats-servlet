/**
 * 
 */
package com.oreilly.servlet.multipart;

/**
 * @author ralph
 * @since Sep 18, 2006
 * 
 */
public interface PartProgressListener
{
	public abstract void notifyProgress(int read, int expected);
}
