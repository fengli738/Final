package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;

import Dao.DBConnect;
import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import models.AdminModel;
import models.UserModel;

public class AdminController implements Initializable{
	
	AdminModel am;

	@FXML
	private Pane pane1;
	@FXML
	private Pane pane3;
	@FXML
	private Pane pane4;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtAddress;
	@FXML
	private TextField Update_Note_tid;
	@FXML
	private TextField Update_Note;
	@FXML
	private TableView<AdminModel> tblNotes;
	@FXML
	private TableColumn<AdminModel, String> tid;
	@FXML
	private TableColumn<AdminModel, String> notes;
	@FXML
	private TableColumn<AdminModel, String> cid1;
	@FXML
	private TextField tidInsert;

	// Declare DB objects
	DBConnect conn = null;
	Statement stmt = null;

	public AdminController() {
		conn = new DBConnect();
		am = new AdminModel();
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		
		tid.setCellValueFactory(new PropertyValueFactory<AdminModel, String>("tid"));
		cid1.setCellValueFactory(new PropertyValueFactory<AdminModel, String>("cid"));
		notes.setCellValueFactory(new PropertyValueFactory<AdminModel, String>("note"));
		
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
    

	public void viewAccounts() {
		pane3.setVisible(false);
		pane1.setVisible(false);
		pane4.setVisible(true);
		tblNotes.getItems().setAll(am.getNotes());
		tblNotes.setVisible(true);

	}

	public void deleteNote() {
		
		pane4.setVisible(false);
		pane3.setVisible(false);
		pane1.setVisible(true);	

	}

	public void updateNote() {

		pane1.setVisible(false);
		pane3.setVisible(true);
		pane4.setVisible(false);
	}
	
	public void submitupdateNote() {
		String a = Update_Note.getText();
		int b = Integer.parseInt(Update_Note_tid.getText());
		am.updateNote(b, a);		
	}
	
	public void logout() {
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
					
	public void submitDeleteNote() {

		int a = Integer.parseInt(tidInsert.getText());
		am.deleteNote(a);
		System.out.println("Successful delete tid " + a);

	}

}
