package com.stackroute.userservice.modal;


import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="user_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	private String name;
	@Id
	private String email;
	private String mobileNumber;
	private int creditPoints;
	private String profilePicture;
	private List<Address> address;
	private List<CardDetails> cardDetails;

}
