package com.tecnics.einvoice.test;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestService {

	
	@Autowired
	TestRepo repo;

	public Iterable<TestEntity> findAll() {
		return repo.findAll();
	}

	public Optional<TestEntity> findById(int rollnumber) {
		return repo.findById(rollnumber);
	}

	public TestEntity save(TestEntity obj) {
		return repo.save(obj);
	}
	
	
	
}
