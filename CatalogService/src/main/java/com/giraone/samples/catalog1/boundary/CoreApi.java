package com.giraone.samples.catalog1.boundary;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class CoreApi extends Application
{
	public final static String PERSISTENCE_UNIT = "primary";
}