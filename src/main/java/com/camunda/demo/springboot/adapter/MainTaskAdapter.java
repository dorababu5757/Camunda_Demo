package com.camunda.demo.springboot.adapter;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.camunda.demo.springboot.email.EmailServiceImpl;

@Component
public class MainTaskAdapter implements JavaDelegate {

	@Autowired
	private EmailServiceImpl emailServiceImpl;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("***********All tasks are completed**************");
		emailServiceImpl.sendSimpleMessage("pakkurthi.dorababu@1rivet.com", "Task Completed",
				execution.getVariable("result").toString()+" "+execution.getVariable("finalstatus"));

	}

}
