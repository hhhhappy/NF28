package TD04;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class TD04Application extends Application{

	@Override
	public void start(Stage stage) throws Exception {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/layout.fxml"));
		
		// Create View
		GridPane root = (GridPane) fxmlLoader.load();
		Scene scene = new Scene(root, 830, 430);
		stage.setTitle("TD04");
		stage.setScene(scene);
		stage.show();
	}
		
	
	public static void main(String[] args) {
		launch(args);
	}
}
