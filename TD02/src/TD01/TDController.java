package TD01;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.converter.NumberStringConverter;

public class TDController {

	private TDModel model;
	
	@FXML
	Button button_start;
	@FXML
	Button button_stop;
	@FXML
	Slider slider;
	@FXML
	ImageView image;
	@FXML
	TextField interval;
	
	public TDController() {
		model =  new TDModel();
	}
	
	public void initialize() {
		model.initialize();
		
		// Binding VM
		interval.textProperty().bindBidirectional(model.intervalProperty(),	new NumberStringConverter());
		slider.valueProperty().bindBidirectional(model.intervalProperty());
		image.imageProperty().bindBidirectional(model.imageProperty());
		button_start.setOnAction(evt -> model.createTimer());
		button_stop.setOnAction(evt -> model.stopTimer());
		
	}
	
	public void SetInterval(int newValue) {
		model.intervalProperty().set(newValue);
	}
}