package com.giraone.samples.catalog1.boundary;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/admin-api")
public class AdminApi extends Application
{
	public final static String PERSISTENCE_UNIT = "primary";
}