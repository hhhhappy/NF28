package TD03;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.util.converter.NumberStringConverter;

public class TD03Controller {
	
	
	
	// fxml elements
	@FXML
	TextField lastname;
	@FXML
	TextField firstname;
	@FXML
	TextField nostreet;
	@FXML
	TextField pc;
	@FXML
	TextField city;
	@FXML
	ComboBox<String> country;
	@FXML
	DatePicker birthday;
	@FXML
	RadioButton radiof;
	@FXML
	RadioButton radiom;
	@FXML
	Button buttonsave;
	@FXML
	ComboBox<String> group;
	
	Contact contact;
	
	//<key of error, error>
	MapChangeListener<String,String> errorListener;	
	
	public TD03Controller() {
		contact =  new Contact();
	}
	
	public void initialize() {
		contact.initialize();
		// set countries
		country.setItems(Country.getCountryNameList());
		
		// set groups
		group.setItems(Group.getGroupObList());
		
		// Binding VM
		lastname.textProperty().bindBidirectional(contact.lastnameProperty());
		firstname.textProperty().bindBidirectional(contact.firstnameProperty());
		nostreet.textProperty().bindBidirectional(contact.noStreetProperty());
		pc.textProperty().bindBidirectional(contact.postCodeProperty());
		city.textProperty().bindBidirectional(contact.cityProperty());
		birthday.valueProperty().bindBidirectional(contact.birthdayProperty());
		radiof.setOnAction(evt -> contact.setSex("F"));
		radiom.setOnAction(evt -> contact.setSex("M"));
		country.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> contact.setCountry(newVal));
		group.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> contact.setGroup(newVal));
		buttonsave.setOnAction(evt -> contact.save());
		
		// BInding MC
		errorListener = changed -> {
			if(changed.wasAdded()) {
				printErrorMsg(changed.getKey(), changed.getValueAdded());
			}
			
			if(changed.wasRemoved()) {
				removeErrorMsg(changed.getKey());
			}
		};
		contact.errorMsgs.addListener(errorListener);
	}
	
	private void printErrorMsg(String key, String msg) {
		switch (key) {
		case "street":
			nostreet.setStyle("-fx-border-color: red ;");
			nostreet.setTooltip(new Tooltip(msg));
			break;
			
		case "pc":
			pc.setStyle("-fx-border-color: red ;");
			pc.setTooltip(new Tooltip(msg));
			break;
			
		case "city":
			city.setStyle("-fx-border-color: red ;");
			city.setTooltip(new Tooltip(msg));
			break;
			
		case "firstname":
			firstname.setStyle("-fx-border-color: red ;");
			firstname.setTooltip(new Tooltip(msg));
			break;
			
		case "lastname":
			lastname.setStyle("-fx-border-color: red ;");
			lastname.setTooltip(new Tooltip(msg));
			break;
		}
	}
	
	private void removeErrorMsg(String key) {
		switch (key) {
		case "street":
			nostreet.setStyle(null);
			nostreet.setTooltip(null);
			break;
			
		case "pc":
			pc.setStyle(null);
			pc.setTooltip(null);
			break;
			
		case "city":
			city.setStyle(null);
			city.setTooltip(null);
			break;
			
		case "firstname":
			firstname.setStyle(null);
			firstname.setTooltip(null);
			break;
			
		case "lastname":
			lastname.setStyle(null);
			lastname.setTooltip(null);
			break;
		}
	}
}
