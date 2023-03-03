package com.stackroute.userservice.modal;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.stackroute.userservice.constant.Constants;

import lombok.Data;

@Data
public class Address {
	
	private int addressId;
	@NotEmpty(message = Constants.MESSAGE_EMPTYHOUSENUMBER)
	private String houseNumber;
	@NotEmpty(message = Constants.MESSAGE_EMPTYCITY)
	private String city;
	@NotEmpty(message = Constants.MESSAGE_EMPTYSTATE)
	private String state;
	@NotEmpty(message = Constants.MESSAGE_EMPTYCOLONY)
	private String colony;
	@NotEmpty(message = Constants.MESSAGE_EMPTYPINCODE)
	@Pattern(regexp = "^[1-9]{1}[0-9]{2}\\s{0,1}[0-9]{3}$",message = Constants.MESSAGE_VALIDPINCODE)
	private String pincode;

}