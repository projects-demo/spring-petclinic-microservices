package org.springframework.samples.petclinic.customers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Home page controller.
 * 
 */
@Controller
public class HomeController {

	@Autowired
	private BookService bookService;


	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		return "hello";
	}

	@RequestMapping("/circuit-breaker-example")
	@ResponseBody
	public String circuitBreakerEx() {
		return bookService.readingList();
	}

	/*
	 * http://localhost:3333/greeting
	 */
	@RequestMapping("/greeting")
	@ResponseBody
	public String greeting() {
		return "greeting";
	}
}
