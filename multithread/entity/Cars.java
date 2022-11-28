package com.multithreading.multithread.entity;

import java.lang.String;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;


@Data
@EqualsAndHashCode
@Entity
public class Cars implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column (name = "Id", nullable =false)
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NonNull
	@Column(nullable = false)
	private String manufacturer;
	
	@NonNull
	@Column(nullable = false)
	private String model;
	
	@NonNull
	@Column(nullable = false)
	private String type;

	public void setManufacturer(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setModel(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setType(String string) {
		// TODO Auto-generated method stub
		
	}

}
