package com.camunda.demo.springboot.adapter;

import java.util.Random;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class PrepaidRedemptionAdapter implements JavaDelegate{
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String status=(String) execution.getVariable("fstatus");
		System.out.println("*******Parallel Gate Execution||Prepaid Redemption recivable******"+status);
		execution.setVariable("finalstatus", status);
		Random random=new Random();
		execution.setVariable("result", random.nextBoolean());
	//	execution.setVariable("result", true);
	}
}
