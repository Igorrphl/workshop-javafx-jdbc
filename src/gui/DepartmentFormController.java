package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable{

	private Department entity;
	
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
	
	@FXML
	public void onBtSaveAction() {
		System.out.println("Save");
	}
	
	@FXML
	public void onBtCancelAction() {
		System.out.println("Cancel");
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
