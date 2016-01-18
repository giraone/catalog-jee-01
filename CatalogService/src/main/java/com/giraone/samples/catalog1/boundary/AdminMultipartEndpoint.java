package com.giraone.samples.catalog1.boundary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.giraone.samples.catalog1.boundary.model.BulkLoadResult;
import com.giraone.samples.catalog1.entity.CatalogEntry;
import com.giraone.samples.common.boundary.MultipartRequestMap;

/**
 * Administrative bulk load for catalogs.
 * This version is for multipart-upload from a browser and needs a web.xml configuration.
 * See http://stackoverflow.com/questions/20456243/how-can-i-define-a-jax-rs-service-that-processes-multi-part-data-in-jee
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@Path("/catalogs-multipart")
public class AdminMultipartEndpoint extends AdminBaseEndpoint
{	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("text/xml; charset=UTF-8")
	public Response uploadFile(@Context HttpServletRequest request)
	{
		MultipartRequestMap map = new MultipartRequestMap(request);

		BulkLoadResult result = new BulkLoadResult();

		CatalogEntry sample = new CatalogEntry();
		sample.setCatalogId(map.getStringParameter("catalogId"));
		sample.setCatalogVersion(Integer.parseInt(map.getStringParameter("catalogVersion")));
		boolean ignoreDuplicates = true;
		File file = map.getFileParameter("attachment");
		
		System.err.println("#AdminMultipartEndpoint.uploadFileUsingMultipartPost#" + file.getAbsolutePath().substring(0, Math.min(file.getAbsolutePath().length(), 80)) + "#");

		InputStream in;
		try
		{
			in = new FileInputStream(file);
		}
		catch (FileNotFoundException e)
		{
			return Response.status(Status.PRECONDITION_FAILED).build();
		}

		try
		{
			this.load(sample, in, result, ignoreDuplicates);
			return Response.ok(result).build();
		}
		finally
		{
			try
			{
				in.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
