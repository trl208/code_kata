package com.epam.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="librarytable")
@Getter
@Setter
@NoArgsConstructor 
public class Library {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	
	String username;
	
	Long bookid;

	
	
}
