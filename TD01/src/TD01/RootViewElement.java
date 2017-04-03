package TD01;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class RootViewElement extends GridPane{

	private TDController controller;
	private ImageView iv;
	private Slider slider;
	
	// setter
	public void SetImageView(Image im) {
		iv.setImage(im);
	}
	
	// getter 
	public Slider getSlider()
	{
		return slider;
	}
	
	public RootViewElement() {		
		//Buttons
		Button buttonStart = new Button("Start");
		buttonStart.setOnAction(evt -> {
			//System.out.println("ButtonStart");
			controller.StartTimer();
			});
		Button buttonStop = new Button("Stop");
		buttonStop.setOnAction(evt -> {
			//System.out.println("ButtonStop");
			controller.StopTimer();
		});
		HBox hbButton = new HBox();
		hbButton.setPadding(new Insets(15, 12, 15, 12));
		hbButton.setSpacing(10);
		hbButton.getChildren().addAll(buttonStart, buttonStop);
		
		// Text field
		Label label = new Label("Intervalle (ms) :");
		TextField textField = new TextField();
		HBox hbtext = new HBox();
		hbtext.setPadding(new Insets(10, 10, 10, 10));
		hbtext.getChildren().addAll(label, textField);
		hbtext.setSpacing(10);
		
		// Slider
		Slider slider = new Slider();
		slider.setMin(0);
		slider.setMax(10);
		slider.setValue(5);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(1);
		slider.setMinorTickCount(5);
		slider.setBlockIncrement(1);
		
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				
				if (newValue.floatValue() > 0)
					controller.SetInterval((int)(newValue.floatValue() *100));
				else 
					controller.StopTimer();
				textField.setText(String.valueOf((int)(newValue.floatValue() *1000))); //refresh the interval of the field of textField
			}
        });
		
		// Image View
		iv = new ImageView();
		iv.setImage(new Image("file:res/image0.jpg")); //default Image 
		
		// Add to border
		BorderPane bp = new BorderPane();
		bp.setTop(hbtext);
		bp.setRight(iv);
		bp.setLeft(hbButton);
		bp.setBottom(slider);
		
		 getChildren().add(bp);
	}
	
	public void setController(TDController controller) {
		this.controller = controller;
	}
}
