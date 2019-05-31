package com.camunda.demo.springboot.adapter;

import java.util.Random;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class CheckWeatherDelegate implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("************Process is starter::");
		Random random = new Random();
		execution.setVariable("name", "Dorababu");
		execution.setVariable("weatherOk", random.nextBoolean());

	}

}
