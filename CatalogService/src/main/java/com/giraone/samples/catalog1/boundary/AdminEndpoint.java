package com.giraone.samples.catalog1.boundary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.giraone.samples.catalog1.boundary.model.BulkLoadResult;
import com.giraone.samples.catalog1.entity.CatalogEntry;
import com.giraone.samples.common.boundary.BaseEndpoint;
import com.giraone.samples.common.boundary.MultipartRequestMap;

/**
 * Administration (bulk load) end point for catalogs
 * 
 * TODO: http://stackoverflow.com/questions/25797650/fileupload-with-jaxrs
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@Path("/catalogs")
public class AdminEndpoint extends BaseEndpoint
{
	@PersistenceContext(unitName = CoreApi.PERSISTENCE_UNIT)
	private EntityManager em;
	
	@Resource
	private UserTransaction userTransaction;
	

	@POST
	@Path("/{catalogId}/${catalogVersion}/${scope}")
	public Response loadFromServerFile(
		@PathParam("catalogId") String catalogId,
		@PathParam("catalogVersion") int catalogVersion,
		@PathParam("scope") String scope)
	{
		BulkLoadResult result = new BulkLoadResult();

		CatalogEntry sample = new CatalogEntry();
		sample.setCatalogId(catalogId);
		sample.setCatalogVersion(catalogVersion);

		String fileName = "C:/Workspaces/GitHub/catalog-jee-01/CatalogService/data/" + catalogId + "-" + scope + ".txt";

		this.load(sample, scope, fileName, result);
		return Response.ok(result).build();
	}
	
	@POST
	@Path("/server-load")
	public Response httpLoadFromServerFile(
		@FormParam("catalogId") String catalogId,
		@FormParam("catalogVersion") int catalogVersion,
		@FormParam("scope") String scope)
	{
		BulkLoadResult result = new BulkLoadResult();

		CatalogEntry sample = new CatalogEntry();
		sample.setCatalogId(catalogId);
		sample.setCatalogVersion(catalogVersion);

		String fileName = "C:/Workspaces/GitHub/catalog-jee-01/CatalogService/data/" + catalogId + "-" + scope + ".txt";

		this.load(sample, scope, fileName, result);
		return Response.ok(result).build();
	}
	
	@POST
	@Path("/form-upload-file")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response httpLoadFromClientFile(@Context HttpServletRequest request)
	{
		MultipartRequestMap map = new MultipartRequestMap(request);

		BulkLoadResult result = new BulkLoadResult();

		CatalogEntry sample = new CatalogEntry();
		sample.setCatalogId(map.getStringParameter("catalogId"));
		sample.setCatalogVersion(Integer.parseInt(map.getStringParameter("catalogVersion")));
		File attachementFile = map.getFileParameter("attachment");
		InputStream in;
		try
		{
			in = new FileInputStream(attachementFile);
		}
		catch (FileNotFoundException e)
		{
			return Response.status(Status.PRECONDITION_FAILED).build();
		}

		try
		{
			this.load(sample, map.getStringParameter("entryColumn"), in, result);
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

	private void load(CatalogEntry sample, String entryColumn, String fileName, BulkLoadResult result)
	{
		FileInputStream in;
		try
		{
			in = new FileInputStream(fileName);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			result.setStatus(404);
			result.setErrorMessage(e.getMessage());
			return;
		}
		try
		{
			this.load(sample, entryColumn, in, result);
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

	private void load(CatalogEntry sample, String entryColumn, InputStream in, BulkLoadResult result)
	{
		try
		{
			userTransaction.begin();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result.setStatus(BulkLoadResult.STATUS_STOP_ON_SEVERE_ERROR);
			result.setErrorMessage(e.getMessage());
			return;
		}
		
		String entryCode = "US";
		String entryText1 = "Vereinigte Staaten";
		sample.setEntryCode(entryCode);
		sample.setEntryText1(entryText1);
		em.persist(sample);
		
		try
		{
			userTransaction.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result.setStatus(BulkLoadResult.STATUS_STOP_ON_SEVERE_ERROR);
			result.setErrorMessage(e.getMessage());
			return;
		}
		
		result.setStatus(BulkLoadResult.STATUS_OK);
		result.setReceivedEntries(1);
		result.setSuccessfullyAddedEntries(1);
		result.setSkippedEntriesOnError(0);
	}
}
