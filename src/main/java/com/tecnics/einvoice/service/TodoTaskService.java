package com.tecnics.einvoice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.Repo.TaskActivityRepo;
import com.tecnics.einvoice.Repo.TodoTaskActivityRepo;
import com.tecnics.einvoice.Repo.TodoTaskRepo;
import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.entity.TaskActivity;
import com.tecnics.einvoice.entity.TodoTask;
import com.tecnics.einvoice.entity.TodoTaskActivity;
import com.tecnics.einvoice.entity.VendorMapping;
import com.tecnics.einvoice.response.TransactionResponse;

@Component
public class TodoTaskService extends BaseService {

	@Autowired
	TodoTaskRepo todoTaskRepo;

	@Autowired
	ErrorLogRepo errorLogRepo;

	@Autowired
	TaskActivityRepo taskActivityRepo;
	
	@Autowired
	TodoTaskActivityRepo todoActivityRepo;

	public List<TodoTask> findAll() {
		return (List<TodoTask>) todoTaskRepo.findAll();
	}

	public Optional<TodoTask> findById(int id) {
		return (Optional<TodoTask>) todoTaskRepo.findById(id);
	}

	public TransactionResponse update(TodoTask todoTask, String user) {
//		return todoTaskRepo.save(todoTask);

		int failCount = 0, successCount = 0;
		List<TaskActivity> log = todoTask.getTaskActivities();

		TransactionResponse todoResponse = new TransactionResponse();
		try {
			TodoTask response = todoTaskRepo.save(todoTask);
			todoResponse.setRefid(response.getTaskRefId());
			for (TaskActivity taskActivity : log) {
				List<Integer> taskActivityIds = new ArrayList<Integer>();
				taskActivity.setActionBy(user);
				taskActivity.setTodoTask(todoTask);
				TaskActivity activity = null;
				activity = taskActivityRepo.save(taskActivity);
				taskActivityIds.add(activity.getId());
			}
			successCount++;
		}

		catch (Exception ex) {
//			System.err.println(getStackTrace(ex));
			failCount++;
			ErrorLog error = new ErrorLog();
			error.setComponentName("TODO");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("Save");
			error.setSource("Java");
			error.setUserId(user);
			errorLogRepo.save(error);
		}
		todoResponse.setFailureTransactionsCount(failCount);
		todoResponse.setSuccessTransactionsCount(successCount);
		todoResponse.setTotalTransactionsCount(failCount + successCount);
		return todoResponse;
	}

	public TransactionResponse save(TodoTask obj, String user) {
		int failCount = 0, successCount = 0;
		List<TaskActivity> log = obj.getTaskActivities();

		TransactionResponse todoResponse = new TransactionResponse();
		try {
			TodoTask response = todoTaskRepo.save(obj);
			todoResponse.setRefid(response.getTaskRefId());
			for (TaskActivity taskActivity : log) {
				List<Integer> taskActivityIds = new ArrayList<Integer>();
				taskActivity.setActionBy(user);
				taskActivity.setTodoTask(obj);
				TaskActivity activity = taskActivityRepo.save(taskActivity);
				taskActivityIds.add(activity.getId());
			}
			successCount++;
		}

		catch (Exception ex) {
			failCount++;
			ErrorLog error = new ErrorLog();
			error.setComponentName("TODO");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("Save");
			error.setSource("Java");
			error.setUserId(user);
			errorLogRepo.save(error);
		}
		todoResponse.setFailureTransactionsCount(failCount);
		todoResponse.setSuccessTransactionsCount(successCount);
		todoResponse.setTotalTransactionsCount(failCount + successCount);
		return todoResponse;
	}

	public void delete(int id) {
		todoTaskRepo.deleteById(id);
	}

	/**
	 * @param status
	 * @return
	 */
	public List<TodoTask> findByStatus(String status) {
		return (List<TodoTask>) todoTaskRepo.findByStatus(status);
	}

	/**
	 * @param flag
	 * @return
	 */
	public List<TodoTask> findByFlag(Boolean flag) {
		return (List<TodoTask>) todoTaskRepo.findByFlag(flag);
	}

	/**
	 * @param flag
	 * @return
	 */
	public List<TodoTask> findByPriority(String flag) {
		return (List<TodoTask>) todoTaskRepo.findByPriority(flag);
	}

	/**
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy
	 * @param string
	 * @return
	 */
	public List<TodoTask> getAllTodo(Integer pageNo, Integer pageSize, String sortBy, String userid) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		Page<TodoTask> pagedResult = todoTaskRepo.findAll(paging, userid);

		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<TodoTask>();
		}
	}

	public List<TodoTask> findByUserId(String userName) {
		return (List<TodoTask>) todoTaskRepo.findByCreatedBy(userName);

	}

	public TodoTask update( TodoTask todoTask) {
		// TODO Auto-generated method stub
		return todoTaskRepo.save(todoTask);
	}

	public TaskActivity saveActivity(TaskActivity todoTask, String userName) {
		// TODO Auto-generated method stub
		todoTask.setActionBy(userName);
		return taskActivityRepo.save(todoTask);
	}

	public List<TodoTaskActivity> findBytaskRefId(int id) {
		// TODO Auto-generated method stub
		return todoActivityRepo.resultsTask_ref_id(id);
	}

}
