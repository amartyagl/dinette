package com.stackroute.userservice.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import com.stackroute.userservice.constant.Constants;
import com.stackroute.userservice.modal.Address;
import com.stackroute.userservice.modal.CardDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author amartya.singh
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	@NotBlank(message = Constants.MESSAGE_NAME)
	private String name;

	@NotEmpty(message = Constants.MESSAGE_EMPTYEMAIL)
	@Email(message = Constants.MESSAGE_INVALIDEMAIL)
	private String email;
	private int creditPoints;
	@NotBlank(message = Constants.MESSAGE_EMPTYMOBILE)
	@Pattern(regexp = "^\\d{10}$", message = Constants.MESSAGE_INVALIDMOBILE)
	private String mobileNumber;
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", message = Constants.MESSAGE_VALIDPASSWORD)
	@NotEmpty(message = Constants.MESSAGE_EMPTYPASSWORD)
	private String password;
	private String profilePicture;
	private List<Address> address;
	private List<CardDetails> cardDetails;

}
