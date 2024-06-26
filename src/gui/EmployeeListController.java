package gui;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Employee;
import model.services.EmployeeService;

public class EmployeeListController implements Initializable, DataChangeListener {

	private EmployeeService service;

	@FXML
	private TableView<Employee> tableViewEmployee;

	@FXML
	private TableColumn<Employee, Integer> tableColumnId;

	@FXML
	private TableColumn<Employee, String> tableColumnName;

	@FXML
	private TableColumn<Employee, Employee> tableColumnEDIT;
	
	@FXML
	private TableColumn<Employee, Employee> tableColumnREMOVE;

	@FXML
	private TableColumn<Employee, String> tableColumnEmail;

	@FXML
	private TableColumn<Employee, Date> tableColumnBirthDate;

	@FXML
	private TableColumn<Employee, Double> tableColumnBaseSalary;

	@FXML
	private Button btNew;

	private ObservableList<Employee> obsList;

	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = gui.util.Utils.currenteStage(event);
		Employee obj = new Employee();
		createDialogForm(obj, "/gui/EmployeeForm.fxml", parentStage);
	}

	public void setEmployeeService(EmployeeService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		Utils.formatTableColumnDate(tableColumnBirthDate, "dd/MM/yyyy");
		tableColumnBaseSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
		Utils.formatTableColumnDouble(tableColumnBaseSalary, 2);


		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewEmployee.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Employee> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewEmployee.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Employee obj, String absoluteName, Stage parenteStage) {
//		try {
//			// View que vou carregar
//			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
//			Pane pane = loader.load();
//
//			// Referência para o controlador
//			EmployeeFormController controller = loader.getController();
//			controller.setEmployee(obj);
//			controller.setEmployeeService(new EmployeeService());
//			controller.subscribeDataChangeListener(this);
//			controller.updateFormData();
//
//			// Comportamento da janela de cadastro
//			Stage dialogStage = new Stage();
//
//			// Título da janela
//			dialogStage.setTitle("Enter Employee data");
//
//			// Cena do stage - Criando uma nova cena
//			dialogStage.setScene(new Scene(pane));
//
//			// A janela não pode ser redimensionada
//			dialogStage.setResizable(false);
//
//			// Stage pai da janela (parenteStage)
//			dialogStage.initOwner(parenteStage);
//
//			// Dizer se a janeça é modal - Ou seja ela fica travada enquanto você não fechar
//			// ela
//			dialogStage.initModality(Modality.WINDOW_MODAL);
//
//			dialogStage.showAndWait();
//
//		} catch (IOException e) {
//			Alerts.showAlert("IO Exception", "Error loading View", e.getMessage(), AlertType.ERROR);
//		}
	}

	public void onDataChanged() {
		updateTableView();
	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Employee, Employee>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Employee obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/EmployeeForm.fxml", Utils.currenteStage(event)));
			}
		});
	}
	
	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Employee, Employee>() {
		 private final Button button = new Button("remove");
		 @Override
		 protected void updateItem(Employee obj, boolean empty) {
		 super.updateItem(obj, empty);
		 if (obj == null) {
		 setGraphic(null);
		 return;
		 }
		 setGraphic(button);
		 button.setOnAction(event -> removeEntity(obj));
		 }
		 });
		}

	private void removeEntity(Employee obj) {
		Optional <ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
		
		if(result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				updateTableView();
			}
			catch (DbIntegrityException e) {
				Alerts.showAlert("Error remove object", null, e.getMessage(), AlertType.ERROR);				
			}
			
		}
	} 
}
