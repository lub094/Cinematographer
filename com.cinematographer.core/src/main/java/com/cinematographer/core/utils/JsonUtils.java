package com.cinematographer.core.utils;

import com.google.gson.GsonBuilder;

public class JsonUtils {

	public static String toJson(Object obj) {
		return new GsonBuilder().create().toJson(obj);
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		return new GsonBuilder().create().fromJson(json, clazz);
	}
}