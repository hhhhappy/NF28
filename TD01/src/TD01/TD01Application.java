package TD01;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class TD01Application extends Application{

	@Override
	public void start(Stage stage) throws Exception {

		stage.setTitle("TD01");
		// 1. Create View
		RootViewElement root = new RootViewElement();

		// 2. Create Model
		TDModel model =  new TDModel();
		
		// 3. Create Controller
		TDController controller = new TDController(root, model);
		
		// 4. binding View -> Controller
		root.setController(controller);
		
		// 5. binding Model -> Controller -> view
		model.initialize();
		controller.initialize();

		Scene scene = new Scene(root, 800, 600);
		stage.setScene(scene);
		stage.show();
	}
		
	
	public static void main(String[] args) {
		launch(args);
	}
}