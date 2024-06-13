package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable{
	
	private DepartmentService service;

	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Department, String> tableColumnName;

	@FXML
	private TableColumn<Department, String> tableColumnDescription;
	
	@FXML
	private TableColumn<Department, String> tableColumnResponsibilities;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnTeamSize;

	@FXML
	private Button btNew;
	
	private ObservableList<Department> obsList;
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = gui.util.Utils.currenteStage(event);
		Department obj = new Department();
		createDialogForm(obj, "/gui/DepartmentForm.fxml", parentStage);
	}
	
	public void setDeparmentService(DepartmentService service) {
		this.service = service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {	
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		tableColumnResponsibilities.setCellValueFactory(new PropertyValueFactory<>("responsibilities"));
		tableColumnTeamSize.setCellValueFactory(new PropertyValueFactory<>("teamSize"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Department> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsList);
	}
	
	private void createDialogForm(Department obj, String absoluteName, Stage parenteStage) {
		try {
			//View que vou carregar
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			//Referência para o controlador
			DepartmentFormController controller = loader.getController();
			controller.setDepartment(obj);
			controller.setDepartmentService(new DepartmentService());
			controller.updateFormData();
			
			//Comportamento da janela de cadastro
			Stage dialogStage = new Stage();
			
			//Título da janela
			dialogStage.setTitle("Enter Department data");
			
			//Cena do stage - Criando uma nova cena
			dialogStage.setScene(new Scene(pane));
			
			//A janela não pode ser redimensionada
			dialogStage.setResizable(false);
			
			//Stage pai da janela (parenteStage)
			dialogStage.initOwner(parenteStage);
			
			//Dizer se a janeça é modal - Ou seja ela fica travada enquanto você não fechar ela
			dialogStage.initModality(Modality.WINDOW_MODAL);
			
			
			dialogStage.showAndWait();

		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading View", e.getMessage(), AlertType.ERROR);
		}
	}

}
