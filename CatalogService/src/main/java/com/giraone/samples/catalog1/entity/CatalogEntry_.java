package com.giraone.samples.catalog1.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(CatalogEntry.class)
public class CatalogEntry_
{
	public static volatile SingularAttribute<CatalogEntry, String> catalogId;
	public static volatile SingularAttribute<CatalogEntry, Integer> catalogVersion;
	public static volatile SingularAttribute<CatalogEntry, String> entryCode;
	public static volatile SingularAttribute<CatalogEntry, String> entryText1;
	public static volatile SingularAttribute<CatalogEntry, String> entryText2;
	public static volatile SingularAttribute<CatalogEntry, String> entryText3;
	
	public static final String SQL_NAME = "CatalogEntry";
	
	public static final String SQL_NAME_catalogId = "catalogId";
	public static final String SQL_NAME_catalogVersion = "catalogVersion";
	public static final String SQL_NAME_entryCode = "entryCode";
	public static final String SQL_NAME_entryText1 = "entryText1";
	public static final String SQL_NAME_entryText2 = "entryText2";
	public static final String SQL_NAME_entryText3 = "entryText3";
}
