package TD04;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.control.TreeItem;

public class TD04Model {
	
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
		
	// Observable list 
	ObservableList<Group> groups = FXCollections.observableArrayList();
	
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
	
	// setter
	public void setSex(String s) {
		sex.set(s);
	}
	
	// setter
	public void setCountry(String c) {
		country.set(c);
	}
	
	//check the format, return key of the error
	public boolean validate(Contact c){
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
		
		// validate
		if (res) {
			c.setBirthday(birthday.getValue());
			c.setCity(city.getValue());
			c.setCountry(country.getValue());
			c.setFirstName(firstName.getValue());
			c.setLastName(lastName.getValue());
			c.setNoStreet(noStreet.getValue());
			c.setPostCode(postCode.getValue());
			c.setSex(sex.getValue());
		}
		
		return res;
	}
	
	// save
	public void save() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("contacts"));
			oos.writeObject(groups.toArray());
			oos.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// load
	public void load(File file) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			Object[] objs = (Object[])ois.readObject();
			groups.clear();
			for (Object obj : objs) {	
				groups.add((Group)obj);
				System.out.println(((Group)obj).contacts.size());
			}
			ois.close();
		} catch(IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void removeFromMap(String key) {
		if (errorMsgs.containsKey(key))
			errorMsgs.remove(key);
	}
	
	public void setContact(Contact c) {
		noStreet.set(c.getNoStreet());
		postCode.set(c.getPostCode());
		city.set(c.getCity());
		country.set(c.getCountry());
		firstName.set(c.getFirstName());
		lastName.set(c.getLastName());
		// add set listener
		birthday.set(c.getBirthday());
		sex.set(c.getSex());
	}
	
	public void create(TreeItem<Object> select) {
		if (select.getValue() instanceof String) {
			createGroup();
		}
		
		if (select.getValue() instanceof Group) {
			createContact((Group)select.getValue());
		}
		
		if (select.getValue() instanceof Contact) {
			return;
		}
	}
	
	public void delete(TreeItem<Object> select) {
		if (select.getValue() instanceof String) {
			return;
		}
		
		if (select.getValue() instanceof Group) {
			groups.remove((Group)select.getValue());
		}
		
		if (select.getValue() instanceof Contact) {
			Contact contact = (Contact)select.getValue();
			Group grp = contact.getGroup();
			grp.contacts.remove(contact);
		}
	}
	
	private void createGroup() {
		groups.add(new Group());
	}
	
	private void createContact(Group grp) {
		grp.contacts.add(new Contact(grp));
	}
}
