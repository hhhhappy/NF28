package TD04;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.time.LocalDate;

public class Contact implements Externalizable {
	// Address
		private String noStreet = "";
		private String postCode = "";
		private String city = "";
		private String country = "";
		
		// Person
		private String firstName = "";
		private String lastName = "";
		private LocalDate birthday = null;
		private String sex = "";
		private Group group = null;
		
		public Contact() {	//need a empty constructor for the IoStream
		}
		public Contact(Group grp) {
			group = grp;
		}
		public void setGroup(Group grp) {
			this.group = grp;
		}
		
		public String getNoStreet() {
			return noStreet;
		}
		public void setNoStreet(String noStreet) {
			this.noStreet = noStreet;
		}
		public String getPostCode() {
			return postCode;
		}
		public void setPostCode(String postCode) {
			this.postCode = postCode;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public LocalDate getBirthday() {
			return birthday;
		}
		public void setBirthday(LocalDate birthday) {
			this.birthday = birthday;
		}
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		public Group getGroup() {
			return group;
		}
		
		public String toString() {
			if (firstName.isEmpty() && lastName.isEmpty())
				return "new contact";
			else
				return firstName + " " + lastName;
		}

		@Override
		public void writeExternal(ObjectOutput out) throws IOException {
			out.writeUTF(noStreet);
			out.writeUTF(postCode);
			out.writeUTF(city);
			out.writeUTF(country);
			out.writeUTF(firstName);
			out.writeUTF(lastName);
			out.writeUTF(birthday.toString());
			out.writeUTF(sex);
		}

		@Override
		public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
			noStreet = in.readUTF();
			postCode = in.readUTF();
			city = in.readUTF();
			country = in.readUTF();
			firstName = in.readUTF();
			lastName = in.readUTF();
			birthday = LocalDate.parse(in.readUTF());
			sex = in.readUTF();
		}
		
		
}
