package TD01;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;

public class TDModel {

	private IntegerProperty interval = new SimpleIntegerProperty();
	private List<Image> listImage =  new ArrayList<Image>(); 
	private IntegerProperty idxImg = new SimpleIntegerProperty();
	private List<String> imageNames = null;
	private ImageTimerTask task = new ImageTimerTask();
	private Timer timer;
	
	// getter
	public IntegerProperty intervalProperty() {
		return interval;
	}
	
	// getter
	public IntegerProperty imageProperty() {
		return idxImg;
	}
	
	// getter
	public Image getCurrImage() {
		return listImage.get(idxImg.get());
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
		idxImg.set(0);
		interval.set(150);  //Give interval a initial value
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
		int index = idxImg.get(); // increment image index
		index = ++index % 8;
		idxImg.set(index);
		//System.out.println(idxImg.get());
	}
	
	// ImageTimerTask
	public class ImageTimerTask extends TimerTask {

		@Override
		public void run() {
			changeImage();
			//System.out.println("changeImage");
		}
	}
}