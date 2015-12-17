package com.giraone.samples.catalog1.boundary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.giraone.samples.catalog1.boundary.model.BulkLoadResult;
import com.giraone.samples.catalog1.entity.CatalogEntry;
import com.giraone.samples.catalog1.entity.CatalogEntry_;
import com.giraone.samples.common.boundary.BaseEndpoint;

/**
 * Administration base class for bulk load of catalogs
 */
public class AdminBaseEndpoint extends BaseEndpoint
{
	@PersistenceContext(unitName = CoreApi.PERSISTENCE_UNIT)
	private EntityManager em;

	@Resource
	private UserTransaction userTransaction;

	protected void load(CatalogEntry sample, File file, BulkLoadResult result)
	{
		FileInputStream in;
		try
		{
			in = new FileInputStream(file);
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
			this.load(sample, in, result);
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

	// iso-3166-1-alpha2.csv => 328 milliseconds without performance optimization
	protected void load(CatalogEntry sample, InputStream in, BulkLoadResult result)
	{
		final long start = System.currentTimeMillis();

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

		InputStreamReader inReader;
		try
		{
			inReader = new InputStreamReader(in, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			result.setStatus(BulkLoadResult.STATUS_STOP_ON_SEVERE_ERROR);
			result.setErrorMessage(e.getMessage());
			return;
		}
		Iterable<CSVRecord> records;
		try
		{
			records = CSVFormat.RFC4180.parse(inReader);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			result.setStatus(BulkLoadResult.STATUS_STOP_ON_SEVERE_ERROR);
			result.setErrorMessage(e.getMessage());
			return;
		}

		// Delete the existing records first
		this.deleteCatalogId(sample.getCatalogId());

		int processedRecords = 0;
		int readRecords = 0;

		for (CSVRecord record : records)
		{
			readRecords++;
			int nrOfColumns = record.size();
			if (nrOfColumns > 1)
			{
				final CatalogEntry newEntry = new CatalogEntry();
				newEntry.setCatalogId(sample.getCatalogId());
				newEntry.setCatalogVersion(sample.getCatalogVersion());
				newEntry.setEntryCode(record.get(0).trim());
				newEntry.setEntryText1(record.get(1).trim());
				if (nrOfColumns > 2)
				{
					newEntry.setEntryText2(record.get(2).trim());
					if (nrOfColumns > 3)
					{
						newEntry.setEntryText3(record.get(3).trim());
					}
				}
				em.persist(newEntry);
				processedRecords++;
			}
		}

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

		final long end = System.currentTimeMillis();

		result.setStatus(BulkLoadResult.STATUS_OK);
		result.setTimeInMilliSeconds(end - start);
		result.setReceivedEntries(readRecords);
		result.setSuccessfullyProcessedEntries(processedRecords);
		result.setSkippedEntriesOnError(0);
	}

	protected int deleteCatalogId(String catalogId)
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<CatalogEntry> criteriaDelete = cb.createCriteriaDelete(CatalogEntry.class);
		Root<CatalogEntry> table = criteriaDelete.from(CatalogEntry.class);
		criteriaDelete.where(cb.equal(table.get(CatalogEntry_.SQL_NAME_catalogId), catalogId));
		return em.createQuery(criteriaDelete).executeUpdate();
	}
}
