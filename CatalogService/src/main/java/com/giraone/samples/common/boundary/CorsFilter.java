package com.giraone.samples.common.boundary;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

/**
 * A servlet filter for JAX/RS to configure CORS to allow requests from any host.
 */
public class CorsFilter implements Filter
{
	private static final Marker LOG_TAG = MarkerManager.getMarker("API-CORS");
	private static final String CORS_ALLOW_HEADER = "Access-Control-Allow-Origin";

	@Inject
	private Logger logger;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException
	{
		if (((HttpServletResponse) response).getHeader(CORS_ALLOW_HEADER) == null)
		{
			((HttpServletResponse) response).addHeader(CORS_ALLOW_HEADER, "*"); // "127.0.0.1" may not work always
		}
		if (logger != null && logger.isDebugEnabled())
		{
			logger.debug(LOG_TAG,
				"Access-Control-Allow-Origin=" + ((HttpServletResponse) response).getHeader(CORS_ALLOW_HEADER));
		}
		// proceed along the chain
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
	}

	@Override
	public void destroy()
	{
	}
}
