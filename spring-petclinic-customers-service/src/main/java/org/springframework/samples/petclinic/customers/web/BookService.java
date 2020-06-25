package org.springframework.samples.petclinic.customers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class BookService {

	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "reliable")
	public String readingList() {
	  //URI uri = URI.create("http://localhost:8082/recommended"); //wrong URL: Will invoke circuit breaker fallback method
		//URI uri = URI.create("http://localhost:2222/recommended"); // correct URL ... implemented in Accounts Microservice

		try {
			//return this.restTemplate.getForObject(uri, String.class);
			return this.restTemplate.getForObject("http://VETS-SERVICE"+"/vets/recommended", String.class);
		} catch (RestClientException e) {
			System.out.println("Exception Occurred.. now fallback method will be called...");
		}
		return "Saurabh";
	}

	public String reliable() {

		//txn rollback;;
		return "backup method..circuit breaker in action..";

	}

	
	
	
	
}