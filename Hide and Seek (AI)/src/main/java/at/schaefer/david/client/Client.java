package at.schaefer.david.client;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;

public class Client extends Application
{
	public static void main(String[] args)
	{
		Application.launch();
	}

	@Override
	public void start(Stage stage)
	{
		stage.setTitle("Hide and Seek(AI)");
		BorderPane root = new BorderPane();
		root.setMinSize(350, 250);
		Text text = new Text("100");
		root.getChildren().add(text);
		Scene scene = new Scene(root);

		// Set the Properties of the Stage
		stage.setX(100);
		stage.setY(200);
		stage.setMinHeight(300);
		stage.setMinWidth(400);
		stage.setScene(scene);
		stage.show();
	}
}