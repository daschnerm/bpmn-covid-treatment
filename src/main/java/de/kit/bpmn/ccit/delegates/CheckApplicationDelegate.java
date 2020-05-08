package de.kit.bpmn.ccit.delegates;

import java.lang.reflect.Field;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.kit.bpmn.ccit.entities.ApplicationEntity;

@Service
public class CheckApplicationDelegate implements JavaDelegate {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Value("${de.kit.bpmn.ccit.variable.names.applicationForm}")
	private String applicationEntityVariable;

	@Value("${de.kit.bpmn.ccit.variable.names.application.feasable}")
	private String applicationFeasableVariableName;

	@Override
	public void execute(DelegateExecution execution) throws IllegalArgumentException, IllegalAccessException {

		LOGGER.info("Entering {}.{}", this.getClass().getName(), "execute()");

		ApplicationEntity application = (ApplicationEntity) execution.getVariable(applicationEntityVariable);

		boolean isApplicationFeasable = true;
		for (Field field : application.getClass().getDeclaredFields()) {
			boolean accessible = field.isAccessible();
			field.setAccessible(true);
			
			if (field.get(application) == null) {
				isApplicationFeasable = false;
				field.setAccessible(accessible);
				break;
			} else if(field.get(application).toString().trim().isEmpty()) {
				isApplicationFeasable = false;
				field.setAccessible(accessible);
				break;
			}
			
			field.setAccessible(accessible);
		}

		execution.setVariable(applicationFeasableVariableName, isApplicationFeasable);

		LOGGER.info("Exiting {}.{}", this.getClass().getName(), "execute()");

	}

}
