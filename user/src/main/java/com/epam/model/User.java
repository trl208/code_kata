package com.epam.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="usertable")
@Getter
@Setter
@NoArgsConstructor
public class User {

	@Id
	private String username;
	
	private String name;
	
	private String email;
	
}
