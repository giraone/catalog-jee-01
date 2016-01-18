package com.giraone.samples.catalog1.boundary;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.giraone.samples.catalog1.boundary.model.BulkLoadResult;
import com.giraone.samples.catalog1.entity.CatalogEntry;

/**
 * Administration (bulk load) end point for catalogs
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@Path("/catalogs")
public class AdminEndpoint extends AdminBaseEndpoint
{
	@POST
	@Path("/upload/{catalogId}/{catalogVersion}")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	@Produces("application/json; charset=UTF-8")
	public Response uploadFileUsingSimplePost(
		@PathParam("catalogId") String catalogId,
		@PathParam("catalogVersion") int catalogVersion,
		@QueryParam("ignoreDuplicates") @DefaultValue("true") boolean ignoreDuplicates,
		String content)
	{		
		if (content == null || content.length() < 10)
		{
			return Response.status(Response.Status.PRECONDITION_FAILED).build();
		}
		
		System.err.println("#AdminEndpoint.uploadFileUsingSimplePost#" + content.length() + "#");
		ByteArrayInputStream in;
		try
		{
			in = new ByteArrayInputStream(content.getBytes("UTF-8"));
		}
		catch (UnsupportedEncodingException uee)
		{
			uee.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		BulkLoadResult result = new BulkLoadResult();

		CatalogEntry sample = new CatalogEntry();
		sample.setCatalogId(catalogId);
		sample.setCatalogVersion(catalogVersion);

		this.load(sample, in, result, ignoreDuplicates);
		
		try
		{
			return Response.created(new URI("./" + catalogId + "/" + catalogVersion)).build();
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json; charset=UTF-8")
	public Response uploadFileUsingMultipartPost(
		@FormParam("catalogId") String catalogId,
		@FormParam("catalogVersion") int catalogVersion,
		@FormParam("ignoreDuplicates") @DefaultValue("true") boolean ignoreDuplicates,
		@FormParam("attachment") File file)
	{
		if (file == null)
		{
			return Response.status(Response.Status.PRECONDITION_FAILED).build();
		}
		System.err.println("#AdminEndpoint.uploadFileUsingMultipartPost#" + file.getAbsolutePath().substring(0, Math.min(file.getAbsolutePath().length(), 80)) + "#");
		
		BulkLoadResult result = new BulkLoadResult();

		CatalogEntry sample = new CatalogEntry();
		sample.setCatalogId(catalogId);
		sample.setCatalogVersion(catalogVersion);

		this.load(sample, file, result, ignoreDuplicates);
		
		try
		{
			return Response.created(new URI("./" + catalogId + "/" + catalogVersion)).build();
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
