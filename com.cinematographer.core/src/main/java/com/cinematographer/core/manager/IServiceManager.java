package com.cinematographer.core.manager;

public interface IServiceManager {

	public <T> T findService(Class<T> clazz);

	public <T> void registerService(Class<T> clazz, T service);

	public <T> void removeService(Class<T> clazz);

}
