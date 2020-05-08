package de.kit.bpmn.ccit.clients;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import de.kit.bpmn.ccit.controller.BpmnInstanceManagerController;
import de.kit.bpmn.ccit.entities.ApplicationEntity;

@Route(value = "application")
public class ProcessCustomClient extends FormLayout {

	private final static Logger LOGGER = LoggerFactory.getLogger(ProcessCustomClient.class);

	private static final long serialVersionUID = 9171761346326000443L;

	private Map<String, Object> guiFields;

	@Autowired
	private BpmnInstanceManagerController controller;

	public ProcessCustomClient() throws ClassNotFoundException {

		guiFields = new HashMap<>();
		Class<?> inputClass = ApplicationEntity.class;

		// Build UI from payload class
		buildUi(inputClass, inputClass.getName());

		// Configure send button
		Button sendButton = new Button("Send", e -> {
			sendRequest(inputClass);
		});

		// Build button bar
		HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.setSizeFull();
		buttonsLayout.add(sendButton);
		this.add(buttonsLayout);
	}

	private void sendRequest(Class<?> inputClass) {
		try {
			// get Payload object and populate them with ui values
			Object application = inputClass.getConstructor().newInstance();
			setValues(inputClass, application, inputClass.getName());

			// invoke ProcessStart
			controller.startProcessInstance((ApplicationEntity) application);

			// tell user of successful request
			Notification.show("request successful");

		} catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException
				| InstantiationException | InvocationTargetException | DatatypeConfigurationException e1) {
			e1.printStackTrace();
		}

	}

	private void setValues(Class<?> inputClass, Object payload, String parentName)
			throws IllegalArgumentException, IllegalAccessException, DatatypeConfigurationException,
			InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException {

		for (Field field : inputClass.getDeclaredFields()) {
			boolean accessible = field.isAccessible();
			field.setAccessible(true);
			String id = buildFieldId(parentName, field.getName());

			Object uiComponent = guiFields.get(id);
			if (uiComponent instanceof TextField) {
				String textFieldValue = ((TextField) uiComponent).getValue();
				try {
					field.set(payload, textFieldValue);
				} catch (IllegalArgumentException e) {
					if (textFieldValue != null && !textFieldValue.trim().isEmpty())
						field.set(payload, Integer.parseInt(textFieldValue));
				}

			} else if (uiComponent instanceof DatePicker) {
				LocalDate dateValue = ((DatePicker) uiComponent).getValue();
				if (dateValue != null)
					field.set(payload, Date.from(dateValue.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
			} else if(uiComponent instanceof Checkbox){
                            boolean booleanValue = ((Checkbox) uiComponent).getValue();
                            field.set(payload, booleanValue);
                        }

			field.setAccessible(accessible);
		}

	}

	private void buildUi(Class<?> inputClass, String parentName) {
		for (Field field : inputClass.getDeclaredFields()) {
			String id = buildFieldId(parentName, field.getName());
			if (field.getType().isAssignableFrom(String.class) || field.getType().isAssignableFrom(Integer.class)) {
				guiFields.put(id, new TextField());
				this.addFormItem((TextField) guiFields.get(id), field.getName());
			} else if (field.getType().isAssignableFrom(Date.class)) {
				guiFields.put(id, new DatePicker());
				this.addFormItem((DatePicker) guiFields.get(id), field.getName());
			} else if (field.getType().isAssignableFrom(Boolean.class)){
                                LOGGER.info("Adding checkbox" ,field.getName(), field.getType(), id);
                                guiFields.put(id, new Checkbox());
                                this.addFormItem((Checkbox) guiFields.get(id), field.getName());
                        }else {
				LOGGER.info(
						"Field {} wasn't added because type {} is not a applicable Format: Please use: java.util.String, java.util.Integer or java.util.Date only!",
						field.getName(), field.getType());
			}
		}

	}

	private String buildFieldId(String parentName, String fieldName) {
		return parentName + "." + fieldName;
	}

}
