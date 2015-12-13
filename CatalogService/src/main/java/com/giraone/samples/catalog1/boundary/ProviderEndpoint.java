package com.giraone.samples.catalog1.boundary;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    @Path("/{catalogId}/${scope}")
    @Produces("application/json")
    public List<CodeTextPair> fetchCatalogForScope(
    	@PathParam("catalogId") String catalogId,
    	@PathParam("scope") String scope,
    	@QueryParam("orderby") @DefaultValue("code ASC") String orderby)
    {    	
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<CatalogEntry> c = cb.createQuery(CatalogEntry.class);
        final Root<CatalogEntry> table = c.from(CatalogEntry.class);
        final CriteriaQuery<CatalogEntry> select = c.select(table);
        
        javax.persistence.criteria.Path<Object> textColumn;
        if ("en".equals(scope))
        	textColumn = table.get(CatalogEntry_.SQL_NAME_entryText2);
        else
        	textColumn = table.get(CatalogEntry_.SQL_NAME_entryText1);
        
        if ("text ASC".equals(orderby))
        	select.orderBy(cb.asc(textColumn));
        else
        	select.orderBy(cb.asc(table.get(CatalogEntry_.SQL_NAME_entryCode)));
        
		select.where(cb.and(
			cb.equal(table.get(CatalogEntry_.SQL_NAME_catalogId), catalogId),
			cb.equal(table.get(CatalogEntry_.SQL_NAME_catalogVersion), 0)
		));
		
        final TypedQuery<CatalogEntry> tq = em.createQuery(select);
                
        final List<CatalogEntry> searchResults = tq.getResultList();
        final List<CodeTextPair> results = new ArrayList<CodeTextPair>();
        for (CatalogEntry catalogEntry : searchResults)
        {
        	CodeTextPair dto = new CodeTextPair();
        	dto.setCode(catalogEntry.getEntryCode());
        	dto.setCode(catalogEntry.getEntryText1());
            results.add(dto);
        }
        return results;
    }
}
