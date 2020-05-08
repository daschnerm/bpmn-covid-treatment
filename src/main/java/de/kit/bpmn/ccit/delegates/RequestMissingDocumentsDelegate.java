package de.kit.bpmn.ccit.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.kit.bpmn.ccit.controller.BpmnInstanceManagerController;
import de.kit.bpmn.ccit.entities.ApplicationEntity;

@Service
public class RequestMissingDocumentsDelegate implements JavaDelegate {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Value("${de.kit.bpmn.ccit.variable.names.applicationForm}")
	private String applicationEntityVariable;

	@Value("${de.kit.bpmn.ccit.message.names.request.message}")
	private String callbackMessageName;

	private String applicantProcessMsgStartEvent = "StartApplicantProcessMessageName";

	@Autowired
	private BpmnInstanceManagerController controller;

	@Override
	public void execute(DelegateExecution execution) {

		LOGGER.info("Entering {}.{}", this.getClass().getName(), "execute()");

		ApplicationEntity application = (ApplicationEntity) execution.getVariable(applicationEntityVariable);

		controller.correlateMessageByMessageName(application, applicantProcessMsgStartEvent, callbackMessageName);

		LOGGER.info("Exiting {}.{}", this.getClass().getName(), "execute()");

	}

}
