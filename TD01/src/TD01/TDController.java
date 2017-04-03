package TD01;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class TDController {

	private RootViewElement root;
	private TDModel model;
	
	public TDController(RootViewElement root, TDModel model) {
		this.root = root;
		this.model = model;
	}
	
	public void initialize() {
		// interval property listener
		model.intervalProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (model.getTimer() != null)
					StopTimer();
				StartTimer();
				//System.out.println("intervalPropertyListener");
			}
        });
		
		// image property listener
		model.imageProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				root.SetImageView(model.getCurrImage());
			}
        });
		
		// slider property listener
		
		/*root.getSlider().valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				SetInterval((int)(newValue.floatValue() *100));
			}
        });*/
	}

	public void StopTimer() {
		model.stopTimer();
	}

	public void StartTimer() {
		model.createTimer();
	}
	
	public void SetInterval(int newValue) {
		model.intervalProperty().set(newValue);
	}
}