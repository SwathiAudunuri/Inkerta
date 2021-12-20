package com.tecnics.einvoice.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tecnics.einvoice.entity.TodoTaskActivity;

public interface TodoTaskActivityRepo extends JpaRepository<TodoTaskActivity, Integer> {

	@Query("SELECT t FROM TodoTaskActivity t where t.task_ref_id =:task_ref_id")

	List<TodoTaskActivity> resultsTask_ref_id(@Param("task_ref_id") int task_ref_id);

}
