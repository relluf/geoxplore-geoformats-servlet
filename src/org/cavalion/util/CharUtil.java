/**
 * 
 */
package org.cavalion.util;

/**
 * @author ralph
 * 
 * Various character utilities.
 */
public class CharUtil
{

	// ---------------------------------------------------------------- conversions
	/**
	 * Converts char array into byte array. Chars are truncated to byte size.
	 */
	public static byte[] toByteArray(char[] carr)
	{
		if (carr == null) {
			return null;
		}
		byte[] barr = new byte[carr.length];
		for (int i = 0; i < carr.length; i++) {
			barr[i] = (byte) carr[i];
		}
		return barr;
	}

	/**
	 * Converts byte array to char array.
	 */
	public static char[] toCharArray(byte[] barr)
	{
		if (barr == null) {
			return null;
		}
		char[] carr = new char[barr.length];
		for (int i = 0; i < barr.length; i++) {
			carr[i] = (char) barr[i];
		}
		return carr;
	}

	// ---------------------------------------------------------------- find
	/**
	 * Match if one character equals to any of the given character.
	 * 
	 * @return <code>true</code> if characters match any chararacter from given array,
	 * otherwise <code>false</code>
	 */
	public static boolean equalsOne(char c, char[] match)
	{
		for (char aMatch : match) {
			if (c == aMatch) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Finds index of the first character in given array the matches any from the given
	 * set of characters.
	 * 
	 * @return index of matched character or -1
	 */
	public static int findFirstEqual(char[] source, int index, char[] match)
	{
		for (int i = index; i < source.length; i++) {
			if (equalsOne(source[i], match) == true) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Finds index of the first character in given array the matches any from the given
	 * set of characters.
	 * 
	 * @return index of matched character or -1
	 */
	public static int findFirstEqual(char[] source, int index, char match)
	{
		for (int i = index; i < source.length; i++) {
			if (source[i] == match) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Finds index of the first character in given array the differes from the given set
	 * of characters.
	 * 
	 * @return index of matched character or -1
	 */
	public static int findFirstDiff(char[] source, int index, char[] match)
	{
		for (int i = index; i < source.length; i++) {
			if (equalsOne(source[i], match) == false) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Finds index of the first character in given array the differes from the given set
	 * of characters.
	 * 
	 * @return index of matched character or -1
	 */
	public static int findFirstDiff(char[] source, int index, char match)
	{
		for (int i = index; i < source.length; i++) {
			if (source[i] != match) {
				return i;
			}
		}
		return -1;
	}

	// ---------------------------------------------------------------- is
	/**
	 * Returns <code>true</code> if specified character is lowercase ascii. If user uses
	 * only ASCIIs, it is much much faster.
	 */
	public static boolean isLowerAscii(char c)
	{
		return (c >= 'a') && (c <= 'z');
	}

	/**
	 * Returns <code>true</code> if specified character is uppercase ascii. If user uses
	 * only ASCIIs, it is much much faster.
	 */
	public static boolean isUpperAscii(char c)
	{
		return (c >= 'A') && (c <= 'Z');
	}

	/**
	 * Uppers lowercase ascii char.
	 */
	public static char toUpperAscii(char c)
	{
		if ((c >= 'a') && (c <= 'z')) {
			c -= (char) 0x20;
		}
		return c;
	}

	/**
	 * Lowers upercase ascii char.
	 */
	public static char toLowerAscii(char c)
	{
		if ((c >= 'A') && (c <= 'Z')) {
			c += (char) 0x20;
		}
		return c;
	}
}