package com.authenticationservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDao {

	@Id
	@Column(name = "emailId", unique = true, nullable = false, length = 100)
	private String email;

	@Column(name = "password", nullable = false)
	@JsonIgnore
	private String password;

}
