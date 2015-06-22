package com.cinematographer.core.manager;

import java.util.HashMap;
import java.util.Map;

public class ServiceManager implements IServiceManager {

	private static IServiceManager instance = new ServiceManager();

	public static IServiceManager getInstance() {
		return instance;
	}

	private Map<Class<?>, Object> services;

	private ServiceManager() {
		services = new HashMap<Class<?>, Object>();
	}

	@SuppressWarnings("unchecked")
	public <T> T findService(Class<T> clazz) {
		return (T) services.get(clazz);
	}

	public <T> void registerService(Class<T> clazz, T service) {
		services.put(clazz, service);
	}

	public <T> void removeService(Class<T> clazz) {
		services.remove(clazz);
	}

}
