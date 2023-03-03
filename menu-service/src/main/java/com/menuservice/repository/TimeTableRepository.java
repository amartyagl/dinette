package com.menuservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.menuservice.model.TimeTable;

@Repository
public interface TimeTableRepository extends MongoRepository<TimeTable, String> {

	@Query(value = "{emailId : ?0}")
	Optional<TimeTable> findByEmailId(String emailId);

}
