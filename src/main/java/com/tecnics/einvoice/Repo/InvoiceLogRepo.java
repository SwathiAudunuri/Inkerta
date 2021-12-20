/**
 * 
 */
package com.tecnics.einvoice.Repo;

import org.springframework.data.repository.CrudRepository;

import com.tecnics.einvoice.entity.InvoiceLog;
import com.tecnics.einvoice.entity.InvoiceLogFailure;
public interface InvoiceLogRepo extends CrudRepository<InvoiceLog, Integer> {


}
