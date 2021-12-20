package com.tecnics.einvoice.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.tecnics.einvoice.entity.TodoTask;

public interface TodoTaskRepo extends PagingAndSortingRepository<TodoTask, Integer>{


	List<TodoTask> findByStatus(String status);
	
	List<TodoTask> findByFlag(Boolean flag);
	
	List<TodoTask> findByPriority(String flag);
	
	
	@Query("SELECT t FROM TodoTask t where t.createdBy=:createdBy")
	Page<TodoTask> findAll(Pageable paging, @Param ("createdBy") String createdBy);

	List<TodoTask> findByCreatedBy(String userName);

}
