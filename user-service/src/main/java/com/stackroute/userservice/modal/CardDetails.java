package com.stackroute.userservice.modal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.stackroute.userservice.constant.Constants;

import lombok.Data;

/**
 * @author amartya.singh
 */
@Data
public class CardDetails {
	private String name;
	
	@NotEmpty(message=Constants.MESSAGE_EMPTYCARDNUMBER)
	private String cardNumber;

	@Pattern(regexp = "(^0?[1-9]$)|(^1[0-2]$)",message = Constants.MESSAGE_VALIDEXPIRYMONTH)
	private String expiryMonth;
	@Pattern(regexp = "^20((1[1-9])|([2-9][0-9]))$",message = Constants.MESSAGE_VALIDEXPIRYYEAR)
	private String expiryYear;
}
