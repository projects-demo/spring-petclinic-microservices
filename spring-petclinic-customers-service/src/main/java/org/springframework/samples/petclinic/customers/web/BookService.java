package org.springframework.samples.petclinic.customers.web;

import java.net.URI;

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
		// URI uri = URI.create("http://localhost:8082/recommended"); //wrong URL: Will
		// invoke circuit breaker fallback method
		// URI uri = URI.create("http://localhost:2222/recommended"); // correct URL ...
		// implemented in Accounts Microservice

		try {
			return this.restTemplate.getForObject("http://VETS-SERVICE" + "/vets/recommended", String.class);
		} catch (Exception e) {

			try {
				System.out.println("1Exception Occurred.. now fallback method will be called..." + e);

				return this.restTemplate.getForObject("http://vets-service" + "/vets/recommended", String.class);

			} catch (Exception e2) {

				try {
					URI uri = URI.create("http://34.69.108.236:8080/vets/recommended");
					System.err.println("uri--->" + uri);
					System.out.println("2Exception Occurred.. now fallback method will be called..." + e2);
					return this.restTemplate.getForObject(uri, String.class);
				} catch (Exception e3) {
					System.out.println("3Exception Occurred.. now fallback method will be called..." + e3);
				}

			}
			return "Saurabh";
		}
	}

	public String reliable() {

		// txn rollback;;
		return "backup method..circuit breaker in action..";

	}

}