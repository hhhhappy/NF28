package TD01;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class TD01Application extends Application{

	@Override
	public void start(Stage stage) throws Exception {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/format.fxml"));
		
		// Create View
		GridPane root = (GridPane) fxmlLoader.load();
		Scene scene = new Scene(root, 600, 400);
		//scene.getStylesheets().add("td2/main/stylesheet.css");				
		stage.setTitle("TD02");
		stage.setScene(scene);
		stage.show();
	}
		
	
	public static void main(String[] args) {
		launch(args);
	}
}