package com.example.custtrans;

import com.example.custtrns.CusttransApplication;
import com.example.custtrns.entity.CustTrns;
import com.example.custtrns.repository.CustTrnsRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(classes = CusttransApplication.class)
@Slf4j
class CusttransApplicationTests {

	@Autowired
	CustTrnsRepository custTrnsRepository;



	@Test
	@DisplayName(" ContextLoad Test")
	void contextLoads() {
		System.out.println("Test completed successfully");
	}

	@Test
	@DisplayName("Jpa Test")
	void jpaTest(){

		Optional<CustTrns> custTrns = custTrnsRepository.findById(1);


		if( custTrns.isPresent()){
			CustTrns custTrns1 =  custTrns.get();
			log.debug("Custrns : {}" , custTrns1.getCustName());
			assertEquals( "Robert",  custTrns1.getCustName() );
		}else{
			fail( "Data not found using Jpa Repo");
		}

	}

}
