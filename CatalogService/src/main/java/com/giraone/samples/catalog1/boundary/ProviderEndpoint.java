package com.giraone.samples.catalog1.boundary;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.giraone.samples.catalog1.boundary.model.CodeTextPair;
import com.giraone.samples.catalog1.entity.CatalogEntry;
import com.giraone.samples.catalog1.entity.CatalogEntry_;
import com.giraone.samples.common.boundary.BaseEndpoint;

/**
 * Provider (= consumer) end point for catalogs
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@Path("/catalogs")
public class ProviderEndpoint extends BaseEndpoint
{	
    @PersistenceContext(unitName = CoreApi.PERSISTENCE_UNIT)
    private EntityManager em;

    @GET
    @Produces("application/json; charset=UTF-8")
    public List<String> fetchAllCatalogs()
    {
    	 final CriteriaBuilder cb = em.getCriteriaBuilder();
         final CriteriaQuery<Tuple> c = cb.createTupleQuery();
         final Root<CatalogEntry> table = c.from(CatalogEntry.class);
         final CriteriaQuery<Tuple> select = c.multiselect(table.get(CatalogEntry_.SQL_NAME_catalogId)).distinct(true);
         
         List<String> ret = new ArrayList<String>();
         List<Tuple> tupleResult = em.createQuery(select).getResultList();
         for (Tuple tuple : tupleResult)
         {
             ret.add((String) tuple.get(0));
         }
         return ret;
    }

    @GET
    @Path("/{catalogId}/versions")
    @Produces("application/json; charset=UTF-8")
    public List<String> fetchAllCatalogVersions(@PathParam("catalogId") String catalogId)
    {
    	 final CriteriaBuilder cb = em.getCriteriaBuilder();
         final CriteriaQuery<Tuple> c = cb.createTupleQuery();
         final Root<CatalogEntry> table = c.from(CatalogEntry.class);
         final CriteriaQuery<Tuple> select = c.multiselect(table.get(CatalogEntry_.SQL_NAME_catalogVersion)).distinct(true);
         select.where(cb.equal(table.get(CatalogEntry_.SQL_NAME_catalogId), catalogId));
         
         List<String> ret = new ArrayList<String>();
         List<Tuple> tupleResult = em.createQuery(select).getResultList();
         for (Tuple tuple : tupleResult)
         {
             ret.add(((Integer) tuple.get(0)).toString());
         }
         return ret;
    }

    @GET
    @Path("/{catalogId}/scopes")
    @Produces("application/json; charset=UTF-8")
    public List<String> fetchAllCatalogScopes(@PathParam("catalogId") String catalogId)
    {         
         List<String> ret = new ArrayList<String>();
         ret.add("de");
         ret.add("en");
         return ret;
    }
 
    @GET
    @Path("/{catalogId}/scope-list/{scope}")
    @Produces("application/json; charset=UTF-8")
    public List<CodeTextPair> fetchCatalogForScope(
    	@PathParam("catalogId") String catalogId,
    	@PathParam("scope") String scope,
    	@QueryParam("orderby") @DefaultValue("code ASC") String orderby)
    {       
    	 final CriteriaBuilder cb = em.getCriteriaBuilder();
         final CriteriaQuery<Tuple> c = cb.createTupleQuery();
         final Root<CatalogEntry> table = c.from(CatalogEntry.class);
         
         javax.persistence.criteria.Path<Object> textColumn;
         if ("en".equals(scope))
         	textColumn = table.get(CatalogEntry_.SQL_NAME_entryText2);
         else
         	textColumn = table.get(CatalogEntry_.SQL_NAME_entryText1);
         
         final CriteriaQuery<Tuple> select = c.multiselect(
        	 table.get(CatalogEntry_.SQL_NAME_entryCode), textColumn);
         
         select.where(cb.and(
 			cb.equal(table.get(CatalogEntry_.SQL_NAME_catalogId), catalogId),
 			cb.equal(table.get(CatalogEntry_.SQL_NAME_catalogVersion), 0)
 		));
         
         if ("text ASC".equals(orderby))
         	select.orderBy(cb.asc(textColumn));
         else
         	select.orderBy(cb.asc(table.get(CatalogEntry_.SQL_NAME_entryCode)));
         
        final List<CodeTextPair> results = new ArrayList<CodeTextPair>();
        List<Tuple> tupleResult = em.createQuery(select).getResultList();
        for (Tuple tuple : tupleResult)
        {
        	CodeTextPair dto = new CodeTextPair();
        	dto.setCode((String) tuple.get(0));
        	dto.setText((String) tuple.get(1));
            results.add(dto);
        }
        return results;
    }
    
    @GET
    @Path("/{catalogId}/full-list")
    @Produces("application/json; charset=UTF-8")
    public List<CatalogEntry> fetchFullCatalog(@PathParam("catalogId") String catalogId)
    {    	
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<CatalogEntry> c = cb.createQuery(CatalogEntry.class);
        final Root<CatalogEntry> table = c.from(CatalogEntry.class);
        final CriteriaQuery<CatalogEntry> select = c.select(table);
       	select.orderBy(cb.asc(table.get(CatalogEntry_.SQL_NAME_entryCode)));
        
		select.where(cb.and(
			cb.equal(table.get(CatalogEntry_.SQL_NAME_catalogId), catalogId),
			cb.equal(table.get(CatalogEntry_.SQL_NAME_catalogVersion), 0)
		));
		
        final TypedQuery<CatalogEntry> tq = em.createQuery(select);
               
        final List<CatalogEntry> searchResults = tq.getResultList();
        return searchResults;
    }
}