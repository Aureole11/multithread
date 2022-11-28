package com.multithreading.multithread.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.multithreading.multithread.entity.Cars;
import com.multithreading.multithread.service.CarService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/car")
public class CarController {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(CarController.class);
	
	@Autowired
	private CarService serv;
	 @RequestMapping (method = RequestMethod.POST, consumes= {MediaType.MULTIPART_FORM_DATA_VALUE},
			 produces= {MediaType.APPLICATION_JSON_VALUE})
	 public @ResponseBody ResponseEntity<?> uploadFile(@RequestParam (value = "files") MultipartFile[] files){
		 try { 
			 for(final MultipartFile file: files) {
				 serv.saveCars(file);
			 }}catch(Exception e) {
				 System.out.println(e);
			 }
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		 }
	 
	 
	 @SuppressWarnings("rawtypes")
	@RequestMapping (method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE},
			 produces ={MediaType.APPLICATION_JSON_VALUE})
	 public @ResponseBody CompletableFuture<ResponseEntity> getAllCar(){
		 
		 return serv.getAllCar().<ResponseEntity>thenApply(ResponseEntity::ok).exceptionally(handleGetCarFailure);
	 }
	 
	 private static Function<Throwable, ResponseEntity<? extends List<Cars>>> handleGetCarFailure = throwable ->{
		 LOGGER.error("Failed to read records: {}", throwable);
		 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();	
		 };

}
