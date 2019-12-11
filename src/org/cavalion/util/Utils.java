package org.cavalion.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

	/**
	 *
	 * @param args
	 * @return
	 */
	public static Map<String, Object> mapOf(Object... args) {
		Map<String, Object> map = new HashMap<>();
		for (int i = 0; i < args.length; i += 2) {
			map.put((String) args[i], args[i + 1]);
		}
		return map;
	}

	/**
	 *
	 * @param args
	 * @return
	 */
	@SafeVarargs
	public static <T> List<T> listOf(T... args) {
		List<T> list = new ArrayList<>();
		for (int i = 0; i < args.length; ++i) {
			list.add(args[i]);
		}
		return list;
	}

}
