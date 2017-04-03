package TD01;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

public class TDModel {

	private IntegerProperty interval = new SimpleIntegerProperty();
	private List<Image> listImage =  new ArrayList<Image>(); 
	private ObjectProperty<Image> image = new SimpleObjectProperty<>();
	private List<String> imageNames = null;
	private ImageTimerTask task = new ImageTimerTask();
	private Timer timer;
	private int currIndex = 0;
	
	// getter
	public IntegerProperty intervalProperty() {
		return interval;
	}
	
	// getter
	public Property<Image> imageProperty() {
		return image;
	}
	
	// getter
	public Timer getTimer() {
		return timer;
	}
	
	// Init
	public void initialize()
	{
		// load the image
		File f = new File("res");
		imageNames = new ArrayList<String>(Arrays.asList(f.list())); 
		for (String s : imageNames) {
			Image image = new Image("file:res/" + s);
			//System.out.println(s);
			listImage.add(image);
		}
		
		// Init
		image.set(listImage.get(currIndex));
		interval.set(150);  //Give interval a initial value
		
		intervalProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				stopTimer();
				createTimer();
			}
        });
	}
	
	public void createTimer() {
		timer = new Timer();
		task = new ImageTimerTask();	//each time when u restart a timer, u need to create a new task
		timer.schedule(task, 0, interval.get());
	}
	
	public void stopTimer(){
		timer.cancel();
	}
	
	public void changeImage() {
		currIndex = ++currIndex % 8;
		image.set(listImage.get(currIndex));
	}
	
	// ImageTimerTask
	public class ImageTimerTask extends TimerTask {

		@Override
		public void run() {
			changeImage();
		}
	}
}