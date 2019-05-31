package com.camunda.demo.springboot;

import static org.camunda.bpm.engine.authorization.Authorization.ANY;
import static org.camunda.bpm.engine.authorization.Authorization.AUTH_TYPE_GRANT;
import static org.camunda.bpm.engine.authorization.Permissions.ALL;
import static org.camunda.bpm.engine.authorization.Resources.FILTER;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Groups;
import org.camunda.bpm.engine.authorization.Resource;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.filter.Filter;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
@EnableProcessApplication
public class Application {

	public static void main(String... args) {
		SpringApplication.run(Application.class, args);

		// do default setup of platform
		ProcessEngine engine = BpmPlatform.getDefaultProcessEngine();
		createDefaultUser(engine);
		// setCamundaEELicenseKey(engine);
	}

	public static void createDefaultUser(ProcessEngine engine) {
		// and add default user to Camunda to be ready-to-go
		if (engine.getIdentityService().createUserQuery().userId("dorababu").count() == 0) {
			User user = engine.getIdentityService().newUser("dorababu");
			user.setFirstName("dorababu");
			user.setLastName("Pakkurthi");
			user.setPassword("dorababu");
			user.setEmail("demo@camunda.org");
			engine.getIdentityService().saveUser(user);

			Group group = engine.getIdentityService().newGroup(Groups.CAMUNDA_ADMIN);
			group.setName("Administrators");
			group.setType(Groups.GROUP_TYPE_SYSTEM);
			engine.getIdentityService().saveGroup(group);

			for (Resource resource : Resources.values()) {
				Authorization auth = engine.getAuthorizationService().createNewAuthorization(AUTH_TYPE_GRANT);
				auth.setGroupId(Groups.CAMUNDA_ADMIN);
				auth.addPermission(ALL);
				auth.setResourceId(ANY);
				auth.setResource(resource);
				engine.getAuthorizationService().saveAuthorization(auth);
			}

			engine.getIdentityService().createMembership("dorababu", Groups.CAMUNDA_ADMIN);
		}

		// create default "all tasks" filter
		if (engine.getFilterService().createFilterQuery().filterName("Alle").count() == 0) {

			Map<String, Object> filterProperties = new HashMap<String, Object>();
			filterProperties.put("description", "Alle Aufgaben");
			filterProperties.put("priority", 10);

			Filter filter = engine.getFilterService().newTaskFilter() //
					.setName("Alle") //
					.setProperties(filterProperties)//
					.setOwner("dorababu")//
					.setQuery(engine.getTaskService().createTaskQuery());
			engine.getFilterService().saveFilter(filter);

			// and authorize demo user for it
			if (engine.getAuthorizationService().createAuthorizationQuery().resourceType(FILTER)
					.resourceId(filter.getId()) //
					.userIdIn("dorababu").count() == 0) {
				Authorization managementGroupFilterRead = engine.getAuthorizationService()
						.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
				managementGroupFilterRead.setResource(FILTER);
				managementGroupFilterRead.setResourceId(filter.getId());
				managementGroupFilterRead.addPermission(ALL);
				managementGroupFilterRead.setUserId("dorababu");
				engine.getAuthorizationService().saveAuthorization(managementGroupFilterRead);
			}

		}
	}

	public static void setCamundaEELicenseKey(ProcessEngine engine) {
		engine.getManagementService().setProperty("camunda-license-key", "xxxx");
	}

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);

		mailSender.setUsername("dorababu5757@gmail.com");
		mailSender.setPassword("Telugu*5757");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}
}
