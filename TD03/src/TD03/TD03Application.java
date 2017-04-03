package TD03;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class TD03Application extends Application{

	@Override
	public void start(Stage stage) throws Exception {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/layout.fxml"));
		
		// Create View
		GridPane root = (GridPane) fxmlLoader.load();
		Scene scene = new Scene(root, 600, 500);
		//scene.getStylesheets().add("td2/main/stylesheet.css");				
		stage.setTitle("TD03");
		stage.setScene(scene);
		stage.show();
	}
		
	
	public static void main(String[] args) {
		launch(args);
	}
}
