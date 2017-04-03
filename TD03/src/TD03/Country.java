package TD03;

import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Country {
	private static String[] getCountryList() {
		return Locale.getISOCountries();
	};
	
	private static String getCountryName(String iso) {
		Locale l = new Locale("", iso);
		return l.getDisplayCountry();
	};
	
	public static ObservableList<String> getCountryNameList() {
		ObservableList<String> countries = FXCollections.observableArrayList();
		for (String c : Country.getCountryList()) {
			countries.add(Country.getCountryName(c));
		}
		return countries;
		
	}
}
