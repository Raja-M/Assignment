package com.example.custtrns.controller;


import com.example.custtrns.service.CustTrnsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class CustTrnsController {

	@Autowired
	CustTrnsService custTrnsService;


	@GetMapping("/customer/list")
	public Object[] homeContent(@RequestParam ( value = "qtr"   , required = true ) Integer qtr) {
		log.debug("In controller : {}", qtr);
		Object[] quarter =  custTrnsService.fetchCustTrnsByQuarter( qtr);
		return quarter;
	}
	@GetMapping("/hello")
	public String hello(){
		return "hello";
	}
}
