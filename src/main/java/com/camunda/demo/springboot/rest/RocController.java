package com.camunda.demo.springboot.rest;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parallel")
public class RocController {

	@Autowired
	private ProcessEngine processEngine;
	
	  @RequestMapping(method=RequestMethod.POST)
	  public void addRocStatus(@RequestParam("status")String status) {
		  roc(status);
	  }

	public void roc(String status) {
		 processEngine.getRuntimeService().startProcessInstanceByKey(//
				"roc", //
				Variables //
						.putValue("status", status)); //
	}

}
