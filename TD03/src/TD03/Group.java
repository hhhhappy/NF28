package TD03;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Group {
	private static String[] groups = {"grp1", "grp2", "grp3"};
	private static String[] getGroupList() {
		return groups;
	}
	
	public static ObservableList<String> getGroupObList() {
		ObservableList<String> groups = FXCollections.observableArrayList();
		for (String g : Group.getGroupList()) {
			groups.add(g);
		}
		return groups;
	}
}
