package de.kit.bpmn.ccit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.Variables.SerializationDataFormats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.kit.bpmn.ccit.entities.ApplicationEntity;

@Service
public class BpmnInstanceManagerController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RuntimeService runtimeService;

	@Value("${de.kit.bpmn.ccit.message.names.inital.message}")
	private String intialMsgName;

	@Value("${de.kit.bpmn.ccit.variable.names.applicationForm}")
	private String applicationEntityVariable;
	
	private String callbackMessageVariableName = "callbackMsgName";

	public void startProcessInstance(ApplicationEntity application) {

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(applicationEntityVariable,
				Variables.objectValue(application).serializationDataFormat(SerializationDataFormats.JSON).create());

		ProcessInstance result = runtimeService.createMessageCorrelation(intialMsgName).setVariables(variables)
				.processInstanceBusinessKey(application.getCaseId()).correlateStartMessage();

		LOGGER.info("Message has started ProcessInstance: {}", result.getProcessInstanceId());

	}

	public void correlateMessageByMessageName(ApplicationEntity application, String messageName, String callbackMessage) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(applicationEntityVariable,
				Variables.objectValue(application).serializationDataFormat(SerializationDataFormats.JSON).create());
		variables.put(callbackMessageVariableName, callbackMessage);

		List<MessageCorrelationResult> resultList = runtimeService.createMessageCorrelation(messageName)
				.setVariables(variables).processInstanceBusinessKey(application.getCaseId())
				.correlateAllWithResult();
		for (MessageCorrelationResult result : resultList) {
			if (result.getProcessInstance() != null)
				LOGGER.info("Message has started ProcessInstance: {}",
						result.getProcessInstance().getProcessInstanceId());
			else if (result.getExecution() != null)
				LOGGER.info("Message has correlated to ProcessInstance: {}",
						result.getExecution().getProcessInstanceId());
		}
	}

}
