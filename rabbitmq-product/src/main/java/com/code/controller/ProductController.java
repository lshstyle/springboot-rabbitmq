package com.code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.code.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping(value = "/fanout")
	public void fanout( @RequestParam("msg") String msg) {
		productService.fanout(msg);
		
	}
	
	@GetMapping(value = "/deadFanout")
	public void deadFanout( @RequestParam("msg") String msg) {
		productService.deadFanout(msg);
		
	}
	
	@GetMapping(value = "/topic")
	public void topic( @RequestParam("msg") String msg) {
		productService.topic(msg);
		
	}
	
	@GetMapping(value = "/direct")
	public void direct( @RequestParam("msg") String msg) {
		productService.direct(msg);
		
	}

}
