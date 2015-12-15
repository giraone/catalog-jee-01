package com.giraone.samples.catalog1.boundary;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

public class AdminApi
{
}

/*
@ApplicationPath("/admin-api")
public class AdminApi extends Application
{
	public final static String PERSISTENCE_UNIT = "primary";

	// Override because we have two base paths.
	// See http://www.adam-bien.com/roller/abien/entry/multiple_jax_rs_uris_in
	@Override
	public Set<Class<?>> getClasses()
	{
		Set<Class<?>> resources = new HashSet<>();
		resources.add(AdminEndpoint.class);
		return resources;
	}
}
*/