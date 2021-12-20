package com.tecnics.einvoice.Repo;

import org.springframework.data.repository.CrudRepository;

import com.tecnics.einvoice.entity.InvoiceItemList;

public interface InvoiceItemListRepo extends CrudRepository<InvoiceItemList, Integer> {

}
