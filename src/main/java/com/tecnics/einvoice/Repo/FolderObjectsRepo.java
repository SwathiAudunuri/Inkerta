package com.tecnics.einvoice.Repo;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.tecnics.einvoice.entity.FolderObjects;

public interface FolderObjectsRepo extends CrudRepository<FolderObjects, Integer> {
	
	List<FolderObjects> findByFolderPath(String folderPath);

}



