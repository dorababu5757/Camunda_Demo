package com.camunda.demo.springboot.adapter;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class StartRocAdapter implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String status=(String) execution.getVariable("status");
		System.out.println("*********Ministry Recivable********"+status);
		execution.setVariable("fstatus",status);
	}

}
