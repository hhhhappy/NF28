package TD04;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Group implements Externalizable {
	private StringProperty groupName = new SimpleStringProperty(null, "", "new group");;
	public ObservableList<Contact> contacts = FXCollections.observableArrayList();;
	
	public StringProperty getNameProperty() {
		return groupName;
	}
	
	public String toString() { 
		return groupName.getValue();
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeUTF(groupName.get());
		out.writeObject(contacts.toArray());
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		groupName.set(in.readUTF());
		Object[] objs = (Object[])in.readObject();
		contacts.clear();
		for (Object obj : objs) {	
			contacts.add((Contact)obj);
		}
	}
}
