package com.giraone.samples.catalog1.boundary.model;

public class BulkLoadResult
{
	public static final int STATUS_OK = 0;
	public static final int STATUS_OK_WITH_ERRORS = 1;
	public static final int STATUS_STOP_ON_ERROR_LIMIT = 2;
	public static final int STATUS_STOP_ON_SEVERE_ERROR = 3;
	
	int status;
	
	int receivedEntries;
	int successfullyProcessedEntries;
	int successfullyAddedEntries;
	int successfullyUpdatedEntries;
	int skippedEntriesOnError;
	
	String errorMessage;
	
	public int getStatus()
	{
		return status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
	public int getReceivedEntries()
	{
		return receivedEntries;
	}
	public void setReceivedEntries(int receivedEntries)
	{
		this.receivedEntries = receivedEntries;
	}
	public int getSuccessfullyProcessedEntries()
	{
		return successfullyProcessedEntries;
	}
	public void setSuccessfullyProcessedEntries(int successfullyProcessedEntries)
	{
		this.successfullyProcessedEntries = successfullyProcessedEntries;
	}
	public int getSuccessfullyAddedEntries()
	{
		return successfullyAddedEntries;
	}
	public void setSuccessfullyAddedEntries(int successfullyAddedEntries)
	{
		this.successfullyAddedEntries = successfullyAddedEntries;
	}
	public int getSuccessfullyUpdatedEntries()
	{
		return successfullyUpdatedEntries;
	}
	public void setSuccessfullyUpdatedEntries(int successfullyUpdatedEntries)
	{
		this.successfullyUpdatedEntries = successfullyUpdatedEntries;
	}
	public int getSkippedEntriesOnError()
	{
		return skippedEntriesOnError;
	}
	public void setSkippedEntriesOnError(int skippedEntriesOnError)
	{
		this.skippedEntriesOnError = skippedEntriesOnError;
	}
	
	public String getErrorMessage()
	{
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
}
