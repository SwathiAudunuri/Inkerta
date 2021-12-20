/**
 * 
 */
package com.tecnics.einvoice.Repo;

import org.springframework.data.repository.CrudRepository;

import com.tecnics.einvoice.entity.InvoiceSubDetail;
public interface InvoiceSubDetailsRepo extends CrudRepository<InvoiceSubDetail, Integer> {

}
