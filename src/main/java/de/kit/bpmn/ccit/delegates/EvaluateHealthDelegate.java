package de.kit.bpmn.ccit.delegates;

import java.lang.reflect.Field;
import java.util.Random;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.kit.bpmn.ccit.entities.ApplicationEntity;

@Service
public class EvaluateHealthDelegate implements JavaDelegate {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution execution) throws IllegalArgumentException, IllegalAccessException {

        LOGGER.info("Entering {}.{}", this.getClass().getName(), "execute()");

        Boolean isSick = Math.random() < 0.5;

        execution.setVariable("isSick", isSick);

        LOGGER.info("Exiting {}.{}", this.getClass().getName(), "execute()");

    }

}
