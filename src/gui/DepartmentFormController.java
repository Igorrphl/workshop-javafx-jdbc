package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import model.entities.Department;
import model.exceptions.ValidationException;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable{

	private Department entity;
	
	private DepartmentService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtDescription;
	
	@FXML
	private TextField txtResposibilities;
	
	@FXML
	private TextField txtTeamSize;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrorDescription;
	
	@FXML
	private Label labelErrorResponsibilities;
	
	@FXML
	private Label labelErrorTeamSize;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	// Exemplo de método para aplicar efeito de iluminação a um botão
    public void applyLightingEffect(Button button) {
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135.0);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(5.0);
        lighting.setSpecularConstant(2.0);
        lighting.setSpecularExponent(40.0);
        lighting.setDiffuseConstant(1.5);
        lighting.setBumpInput(null);
        lighting.setContentInput(null);

        button.setEffect(lighting);
    }
	
	public void setDepartment(Department entity) {
		this.entity = entity;
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("service was null");
		}
		try {
			entity = getFormData();
			service.saveOfUpdate(entity);
			notifyDatachangeListeners();
			Utils.currenteStage(event).close();
		}
		catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		catch (DbException e) {
			Alerts.showAlert("Error Saving object", null, e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	private void notifyDatachangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}
	
	private Department getFormData() {
	    ValidationException exception = new ValidationException("Validation Error");

	    if (isFieldEmpty(txtName)) {
	        exception.addError("Name", "Field can't be empty");
	    }
	    if (isFieldEmpty(txtDescription)) {
	        exception.addError("Description", "Field can't be empty");
	    }
	    if (isFieldEmpty(txtResposibilities)) {
	        exception.addError("Responsibilities", "Field can't be empty");
	    }
	    if (isFieldEmpty(txtTeamSize)) {
	        exception.addError("TeamSize", "Field can't be empty");
	    }

	    if (exception.getErrors().size() > 0) {
	        throw exception;
	    }

	    Department obj = new Department();
	    obj.setName(txtName.getText());
	    obj.setDescription(txtDescription.getText());
	    obj.setResponsibilities(txtResposibilities.getText());
	    obj.setTeamSize(Utils.tryParseToInt(txtTeamSize.getText()));

	    return obj;
	}

	private boolean isFieldEmpty(TextField textField) {
	    return textField.getText() == null || textField.getText().trim().isEmpty();
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currenteStage(event).close();
	}
	
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
		Constraints.setTextFieldMaxLength(txtDescription, 200);
		Constraints.setTextFieldMaxLength(txtResposibilities, 300);
		Constraints.setTextFieldInteger(txtTeamSize);
	}
	
	//Dentro das caixinhas de texto os dados que estão dentro do objeto entity dentro do departamento
	public void updateFormData () {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		txtDescription.setText(entity.getDescription());
		txtResposibilities.setText(entity.getResponsibilities());
		txtTeamSize.setText(String.valueOf(entity.getTeamSize()));

	}
	
	private void setErrorMessages(Map<String, String> errors) {
	    for (String field : errors.keySet()) {
	        if (field.equals("Name")) {
	            labelErrorName.setText(errors.get("Name"));
	        } else if (field.equals("Description")) {
	            labelErrorDescription.setText(errors.get("Description"));
	        } else if (field.equals("Responsibilities")) {
	            labelErrorResponsibilities.setText(errors.get("Responsibilities"));
	        } else if (field.equals("Team Size")) {
	            labelErrorTeamSize.setText(errors.get("Team Size"));
	        }
	    }
	}


}
