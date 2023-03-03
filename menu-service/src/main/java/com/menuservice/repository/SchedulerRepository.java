package com.menuservice.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.menuservice.model.ScheduledInfo;

@Repository
@EnableMongoRepositories
public interface SchedulerRepository extends MongoRepository<ScheduledInfo, Long> {
	
	@Query(value = "{jobKey : ?0}")
	ScheduledInfo findByJobKey(String jobKey);
	
	@Query(value = "{emailId : ?0}")
	List<ScheduledInfo> findByEmailId(String emailId);

}
