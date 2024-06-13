package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import model.entities.Department;
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
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
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
		Department obj = new Department();
		
		obj.setId(Utils.tryParseToInt( txtId.getText()));
		obj.setName(txtName.getText());
		obj.setDescription(txtDescription.getText());
		obj.setResponsibilities(txtResposibilities.getText());
		obj.setTeamSize(Utils.tryParseToInt( txtTeamSize.getText()));

		return obj;
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

}
