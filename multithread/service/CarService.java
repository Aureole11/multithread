package com.multithreading.multithread.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.multithreading.multithread.entity.Cars;
import com.multithreading.multithread.repository.CarRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
@Service
public class CarService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(CarService.class);
	
	private CarRepository repo;
	
	@Async
	public CompletableFuture<List<Cars>> saveCars(final MultipartFile file) throws Exception{
		final long start =System.currentTimeMillis();
		
		List<Cars> car = parseCSVFile(file);
		
		LOGGER.info("Saving a list of cars of size {} records", car.size());
		
		car=repo.saveAll(car);
		
		LOGGER.info("Elapsed time: {}" , (System.currentTimeMillis() -start));
		return CompletableFuture.completedFuture(car);
	}

	
	private List<Cars> parseCSVFile(final MultipartFile file)throws Exception {
		final List<Cars> car=new ArrayList<>();
		try {
			try(final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))){
				String line;
				while((line=br.readLine())!= null) {
					final String[] data=line.split(";");
				    final Cars vh=new Cars();
					vh.setManufacturer(data[10]);
					vh.setModel(data[1]);
					vh.setType(data[2]);
					car.add(vh);
				}
				return car;
			}
			catch(final IOException e) {
				LOGGER.error("Failed to parse CSV file {}", e);
				throw new Exception("Failed to parse CSV file{}", e);
			}
			
		}catch(final Exception s) {
			System.out.println(s);
		}
		return parseCSVFile(file);		
	}
	
	@Async
	public CompletableFuture<List<Cars>> getAllCar(){
		
		LOGGER.info("Request to get a list of cars");
		final List<Cars> cars = repo.findAll();
		return  CompletableFuture.completedFuture(cars);
	}

}
