package com.camunda.demo.springboot.adapter;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class LoggerDelegate implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {

		System.out.println("************Failed Weather******" + execution.getActivityInstanceId());
		System.out.println("***********Process Id*****" + execution.getProcessDefinitionId());
		System.out.println("*********Activity Name*********" + execution.getCurrentActivityName());
		System.out.println("*********************************************************************");
	}

}
