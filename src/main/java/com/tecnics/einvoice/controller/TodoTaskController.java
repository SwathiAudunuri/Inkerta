package com.tecnics.einvoice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tecnics.einvoice.entity.Enquiry;
import com.tecnics.einvoice.entity.TaskActivity;
import com.tecnics.einvoice.entity.TodoTask;
import com.tecnics.einvoice.entity.TodoTaskActivity;
import com.tecnics.einvoice.entity.VendorMapping;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.service.TodoTaskService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class TodoTaskController extends BaseController {

	private TodoTaskService todoTaskService;

	@Autowired
	public TodoTaskController(TodoTaskService theTodoTaskService) {
		todoTaskService = theTodoTaskService;
	}

	@GetMapping("/getTodo")
	public ResponseEntity<List<TodoTask>> getAllEmployees(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "taskRefId") String sortBy, @RequestHeader("authorization") String token) {
		List<TodoTask> list = todoTaskService.getAllTodo(pageNo, pageSize, sortBy, getUserName(token));

		return new ResponseEntity<List<TodoTask>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/todos")
	public ResponseEntity<ResponseMessage> findAll() {
		List<TodoTask> response = todoTaskService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

	@GetMapping("/todo")
	public ResponseEntity<ResponseMessage> findByUserId(@RequestHeader("authorization") String token) {
		List<TodoTask> response = todoTaskService.findByUserId(getUserName(token));
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

	
	@GetMapping("/todo/{id}")
	public ResponseEntity<ResponseMessage> findById(@PathVariable int id) {
		Optional<TodoTask> response = todoTaskService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

	@GetMapping("/todo/comments/{id}")
	public List<TodoTaskActivity> findByUserId(@PathVariable int id ){
		
		return todoTaskService.findBytaskRefId(id) ;
	}

	@PostMapping("/todo/addcomment")
	public TaskActivity save(@RequestBody TaskActivity todoTask, @RequestHeader("authorization") String token) {

		return todoTaskService.saveActivity(todoTask, getUserName(token));

	}

	/**
	 * @param todoTask
	 * @param token
	 * @return TodoResponse
	 */
	@PostMapping("/todo")
	public ResponseEntity<ResponseMessage> save(@RequestBody TodoTask todoTask,
			@RequestHeader("authorization") String token) {
		TransactionResponse response = todoTaskService.save(todoTask, getUserName(token));
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

	}

	/**
	 * @param id
	 * @param todoTask
	 * @return TodoTask
	 */
	@PutMapping("/todo/{id}")
	public ResponseEntity<ResponseMessage> put(@PathVariable int id, @RequestBody TodoTask todoTask,
			@RequestHeader("authorization") String token) {
		todoTask.setTaskRefId(id);
		TransactionResponse response = todoTaskService.update(todoTask, getUserName(token));
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

	}

	@PutMapping("/todo/status_change/{id}/{status}")
	public ResponseEntity<ResponseMessage> activate(@PathVariable Integer id,@PathVariable String status) {
		Optional<TodoTask> response = todoTaskService.findById(id);
		response.get().setStatus(status);
		TodoTask response2 = todoTaskService.update( response.get());
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response2));
	}
	
	
//	@PutMapping("/todo/deletestatus/{id}")
//	public ResponseEntity<ResponseMessage> activate1(@PathVariable Integer id) {
//		Optional<TodoTask> response = todoTaskService.findById(id);
//		response.get().setStatus("deleted");
//		TodoTask response2 = todoTaskService.update( response.get());
//		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response2));
//	}

	/**
	 * @param status
	 * @return
	 */
	@GetMapping("/todo/status/{status}")
	public ResponseEntity<ResponseMessage> findByStatus(@PathVariable String status) {
		List<TodoTask> response = todoTaskService.findByStatus(status);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

	}

	@GetMapping("/todo/flag/{flag}")
	public ResponseEntity<ResponseMessage> findByFlag(@PathVariable Boolean flag) {
		List<TodoTask> response = todoTaskService.findByFlag(flag);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

	}

	@GetMapping("/todo/priority/{priority}")
	public ResponseEntity<ResponseMessage> findByPriority(@PathVariable String priority) {
		List<TodoTask> response = todoTaskService.findByPriority(priority);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}

	@DeleteMapping("/todo/{id}")
	public void delete(@PathVariable int id) {
		todoTaskService.delete(id);
	}

}
