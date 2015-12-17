package com.giraone.samples.catalog1.boundary.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "BulkLoadResult", namespace = "http://www.giraone.com/samples/catalog1" )
public class BulkLoadResult implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public static final int STATUS_OK = 0;
	public static final int STATUS_OK_WITH_ERRORS = 1;
	public static final int STATUS_STOP_ON_ERROR_LIMIT = 2;
	public static final int STATUS_STOP_ON_SEVERE_ERROR = 3;
	
	int status;
	String errorMessage;
	
	int receivedEntries;
	int successfullyProcessedEntries;
	int skippedEntriesOnError;
	
	long timeInMilliSeconds;
		
	public int getStatus()
	{
		return status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}	
	public String getErrorMessage()
	{
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
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
	public int getSkippedEntriesOnError()
	{
		return skippedEntriesOnError;
	}
	public void setSkippedEntriesOnError(int skippedEntriesOnError)
	{
		this.skippedEntriesOnError = skippedEntriesOnError;
	}
	
	public long getTimeInMilliSeconds()
	{
		return timeInMilliSeconds;
	}
	public void setTimeInMilliSeconds(long timeInMilliSeconds)
	{
		this.timeInMilliSeconds = timeInMilliSeconds;
	}
}
