package com.giraone.samples.catalog1.entity;

import java.io.Serializable;

public class CatalogEntryCompositeKey implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String catalogId;
	private int catalogVersion;
	private String entryCode;
	
	public String getCatalogId()
	{
		return catalogId;
	}
	public void setCatalogId(String catalogId)
	{
		this.catalogId = catalogId;
	}
	public int getCatalogVersion()
	{
		return catalogVersion;
	}
	public void setCatalogVersion(int catalogVersion)
	{
		this.catalogVersion = catalogVersion;
	}
	public String getEntryCode()
	{
		return entryCode;
	}
	public void setEntryCode(String entryCode)
	{
		this.entryCode = entryCode;
	}
	
	public String composite()
	{
		return (this.catalogId + CatalogEntry.SEPARATOR
			+ this.catalogVersion + CatalogEntry.SEPARATOR
			+ this.entryCode.hashCode());
	}
	
	public int hashCode()
	{
		return this.composite().hashCode();
	}
	
	public boolean equals(Object obj)
	{
		if (obj == this)
		{
			return true;
		}
		if (!(obj instanceof CatalogEntryCompositeKey) || obj == null)
		{
			return false;
		}
		return this.composite().equals((CatalogEntryCompositeKey) obj);
	}
}
