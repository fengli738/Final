package controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;

import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import models.ClientModel;
import models.UserModel;
 

public class ClientController implements Initializable {
	
	static int userid;
	UserModel um;
	

	@FXML
	private TableView<UserModel> tblNotes;
	@FXML
	private TableColumn<UserModel, String> tid;
	@FXML
	private TableColumn<UserModel, String> notes;
	@FXML
	private Text users;

	

	public void initialize(URL location, ResourceBundle resources) {
				
		tid.setCellValueFactory(new PropertyValueFactory<UserModel, String>("tid"));
		notes.setCellValueFactory(new PropertyValueFactory<UserModel, String>("note"));
		
		users.setText("Welcome User");
		
		// auto adjust width of columns depending on their content
		tblNotes.setColumnResizePolicy((param) -> true);
		Platform.runLater(() -> customResize(tblNotes));

		tblNotes.setVisible(false); // set invisible initially
		
	}

    public void customResize(TableView<?> view) {

        AtomicLong width = new AtomicLong();
        view.getColumns().forEach(col -> {
            width.addAndGet((long) col.getWidth());
        });
        double tableWidth = view.getWidth();

        if (tableWidth > width.get()) {
            view.getColumns().forEach(col -> {
                col.setPrefWidth(col.getWidth()+((tableWidth-width.get())/view.getColumns().size()));
            });
        }
    }
    
	public void viewNotes() throws IOException {

		tblNotes.getItems().setAll(um.getNotes(userid)); // load table data from UserModel List
		tblNotes.setVisible(true); // set tableview to visible if not

	}

	public void logout() {
		// System.exit(0);
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			Main.stage.setScene(scene);
			Main.stage.setTitle("Login");
		} catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}
	}

	public void createNotes() {

		TextInputDialog dialog = new TextInputDialog("Enter note");
		dialog.setTitle("Note add system");
		dialog.setHeaderText("Enter Notes");
		dialog.setContentText("Please enter your notes:");
		

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			System.out.println("Note entry: " + result.get());
			um.insertRecord(userid, result.get());
		}

		// The Java 8 way to get the response value (with lambda expression).
		result.ifPresent(note -> System.out.println("Note Entry: " + notes));

	}

	public static void setUserid(int user_id) {
		userid = user_id;
		System.out.println("Welcome id " + userid);
	}
	

	public ClientController() {

		um = new UserModel();

	}

}
