package TD03;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

public class Contact {
	
	// Address
	private StringProperty noStreet = new SimpleStringProperty(null, "street", "");
	private StringProperty postCode = new SimpleStringProperty(null, "pc", "");
	private StringProperty city = new SimpleStringProperty(null, "city", "");
	private StringProperty country = new SimpleStringProperty("");
	
	// Person
	private StringProperty firstName = new SimpleStringProperty(null, "firstname", "");
	private StringProperty lastName = new SimpleStringProperty(null, "lastname", "");
	private ObjectProperty<LocalDate> birthday = new SimpleObjectProperty<>();
	private StringProperty sex = new SimpleStringProperty("");
	private StringProperty group = new SimpleStringProperty("");
		
	// Observable map
	ObservableMap<String,String> errorMsgs = FXCollections.observableHashMap();
		
	public void initialize() {
		// add listener
		noStreet.addListener((observable, oldVal, newVal) -> removeFromMap(noStreet.getName()));
		postCode.addListener((observable, oldVal, newVal) -> removeFromMap(postCode.getName()));
		city.addListener((observable, oldVal, newVal) -> removeFromMap(city.getName()));
		firstName.addListener((observable, oldVal, newVal) -> removeFromMap(firstName.getName()));
		lastName.addListener((observable, oldVal, newVal) -> removeFromMap(lastName.getName()));
	}
	
	// getter
	public StringProperty firstnameProperty() {
		return firstName;
	}
	
	// getter
	public StringProperty lastnameProperty() {
		return lastName;
	}
	
	// getter
	public ObjectProperty<LocalDate> birthdayProperty() {
		return birthday;
	}
	
	// getter
	public StringProperty sexProperty() {
		return sex;
	}
	
	// getter
	public StringProperty noStreetProperty() {
		return noStreet;
	}
	
	// getter
	public StringProperty postCodeProperty() {
		return postCode;
	}
	
	// getter
	public StringProperty cityProperty() {
		return city;
	}
	
	// getter
	public StringProperty countryProperty() {
		return country;
	}
	
	// getter
	public StringProperty groupProperty() {
		return group;
	}
	
	// setter
	public void setSex(String s) {
		sex.set(s);
	}
	
	// setter
	public void setCountry(String c) {
		country.set(c);
	}
	
	// setter
	public void setGroup(String g) {
		group.set(g);
	}
	//check the format, return key of the error
	public boolean checkFormat(){
		boolean res = true;
		// Address
		String street = noStreet.getValue().toString(); 
		String pc = postCode.getValue().toString();
		String textCity = city.getValue().toString(); 
		
		// Person
		String firstname = firstName.getValue().toString();
		String lastname = lastName.getValue().toString(); 
		
		if (street.isEmpty()) {
			errorMsgs.put(noStreet.getName(), "Not empty!");
			res = false;
		}
		
		if (pc.isEmpty()) {
			errorMsgs.put(postCode.getName(), "Not empty!");
			res = false;
		}
		
		if (textCity.isEmpty()) {
			errorMsgs.put(city.getName(), "Not empty!");
			res = false;
		}
		
		if (firstname.isEmpty()) {
			errorMsgs.put(firstName.getName(), "Not empty!");
			res = false;
		}
		
		if (lastname.isEmpty()) {
			errorMsgs.put(lastName.getName(), "Not empty!");
			res = false;
		}
		
		return res;
	}
	
	// save
	public void save() {
		// check if msgs valid
		if (!checkFormat())
			return;
		//System.out.println(noStreet.getValue().toString());
		
		// save
	}
	
	private void removeFromMap(String key) {
		if (errorMsgs.containsKey(key))
			errorMsgs.remove(key);
	}
}
