package com.giraone.samples.catalog1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@IdClass(CatalogEntryCompositeKey.class)
@Entity
@Table(name = CatalogEntry_.SQL_NAME)
public class CatalogEntry
{
	public static final char SEPARATOR = '|';
	
	/**
	 * Unique ID of the catalog, e.g. "iso-639-1", "iso-639-2-alpha-3", "iso-3166-1-alpha-3.
	 * It is recommended to use lower case letters, numbers and dash only.
	 **/
	@Id
	@Column(name = CatalogEntry_.SQL_NAME_catalogId, nullable = false, length = 128)
	@NotNull
	@Size(min = 10, max = 128)
	@Pattern(regexp = "[0-9a-z\\-]*", message = "Only numbers, lowercase ASCII letters and dash are allowed")
	private String catalogId;
	
	/** A version number starting with 0 for the first version */
	@Id
	@Column(name = CatalogEntry_.SQL_NAME_catalogVersion, nullable = true, length = 16)
	@NotNull
	private int catalogVersion;

	@Id
	@Column(name = CatalogEntry_.SQL_NAME_entryCode, nullable = false, length = 128)
	@NotNull
	@Size(min = 10, max = 128)
	@Pattern(regexp = "[0-9A-Za-z_.\\-]*", message = "Only numbers, ASCII letters, ., _ and - are allowed")
	private String entryCode;
	
	@Column(name = CatalogEntry_.SQL_NAME_entryText1, nullable = true, length = 256)
	private String entryText1;
	
	@Column(name = CatalogEntry_.SQL_NAME_entryText2, nullable = true, length = 256)
	private String entryText2;
	
	@Column(name = CatalogEntry_.SQL_NAME_entryText3, nullable = true, length = 256)
	private String entryText3;

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

	public String getEntryText1()
	{
		return entryText1;
	}

	public void setEntryText1(String entryText1)
	{
		this.entryText1 = entryText1;
	}

	public String getEntryText2()
	{
		return entryText2;
	}

	public void setEntryText2(String entryText2)
	{
		this.entryText2 = entryText2;
	}

	public String getEntryText3()
	{
		return entryText3;
	}

	public void setEntryText3(String entryText3)
	{
		this.entryText3 = entryText3;
	}
}
