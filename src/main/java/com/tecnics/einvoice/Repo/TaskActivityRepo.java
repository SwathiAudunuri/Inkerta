package com.tecnics.einvoice.Repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tecnics.einvoice.entity.TaskActivity;


public interface TaskActivityRepo extends CrudRepository<TaskActivity, Integer>{



}
