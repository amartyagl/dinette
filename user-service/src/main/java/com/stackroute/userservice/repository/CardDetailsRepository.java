/**
 * 
 */
package com.stackroute.userservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stackroute.userservice.modal.CardDetails;

/**
 * @author amartya.singh
 */
public interface CardDetailsRepository extends MongoRepository<CardDetails,String>{

}
