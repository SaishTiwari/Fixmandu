package build.fixmandu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StaffHomePage extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Staff Home");

        // Logo
        ImageView logoImageView = new ImageView(new Image("file:/Users/saishtiwari/Documents/logo.png"));
        logoImageView.setFitWidth(200); // Setting the width of the logo
        logoImageView.setPreserveRatio(true);

        // Buttons
        Button homeButton = new Button("Home"); //Creating home button
        Button appointmentsButton = new Button("Appointments"); // Creating Appointment Button
        Button reportsButton = new Button("Reports"); //Creating Reports Button
        Button registerCustomerButton = new Button("Register New Customer"); //Creating RegisterCustomer Button
        Button logoutButton = new Button("Logout"); //Creating Logout button

        //Using Event Handler to navigate to different pages while clicking on button
        homeButton.setOnAction(e -> {
            // No need to implement navigation as we are already in this page
        });

        appointmentsButton.setOnAction(e -> {
            //Navigating to Appointment page
            showAppointmentsPage(primaryStage);
        });

        reportsButton.setOnAction(e -> {
            //Navigating to Reports page
            showReportsPage(primaryStage);
        });

        registerCustomerButton.setOnAction(e -> {
            // Navigating to register Customer page
            showStaffRegisterCustomerPage(primaryStage);
        });

        logoutButton.setOnAction(e -> {
           // Logging out
            primaryStage.close(); // Closing the current window
            // Opening the login page
            LoginPage loginPage = new LoginPage();
            loginPage.start(new Stage());
        });

        // Using Horizontal Box and Button Box
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(homeButton, appointmentsButton, reportsButton, registerCustomerButton, logoutButton);
        buttonBox.setPadding(new Insets(10));

        //Creating layout page
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(createTopSection(logoImageView, buttonBox));
        borderPane.setCenter(createCenterSection());

        //Setting the stage and scene with borders height and width
        Scene scene = new Scene(borderPane, 730, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    //Creation top section with logo and navigation buttons.
    private HBox createTopSection(ImageView logoImageView, HBox buttonBox) {
        HBox topSection = new HBox(10);
        topSection.getChildren().addAll(logoImageView, buttonBox, createSeparator()); // Add separator
        topSection.setPadding(new Insets(10));
        topSection.setStyle("-fx-background-color: white");
        return topSection;
    }

    //Creating a seprator
    private Separator createSeparator() {
        Separator separator = new Separator();
        separator.setStyle("-fx-border-style: none");
        return separator;
    }
    //Function to Show Appointment Page
    private void showAppointmentsPage(Stage primaryStage) {
        StaffAppointmentManagement appointmentsPage = new StaffAppointmentManagement(); // Implement your appointments page
        appointmentsPage.start(primaryStage);
    }

    // Function to show Reports Page
    private void showReportsPage(Stage primaryStage) {
        //StaffReportViewer reportsPage = new StaffReportViewer(); // Implement your reports page
     //    reportsPage.start(primaryStage);
    }

    // Function to show Register Customer Page
    private void showStaffRegisterCustomerPage(Stage primaryStage) {
        // Create and start the customer signup page
        StaffRegisterCustomerPage staffRegisterCustomerPage = new StaffRegisterCustomerPage();
        staffRegisterCustomerPage.start(primaryStage);
    }

    // Function to create Vertical Box
    private VBox createCenterSection() {
        VBox centerSection = new VBox();
        centerSection.setFillWidth(true);
        centerSection.setStyle("-fx-background-color: #f0f0f0"); // Set background color

        // Inserting Center Image in the home page
        Image centerImage = new Image("file:/Users/saishtiwari/Documents/Homeimage.jpeg");
        ImageView centerImageView = new ImageView(centerImage);
        centerImageView.setPreserveRatio(true);
        centerImageView.setFitWidth(730);

        centerSection.getChildren().add(centerImageView);
        centerSection.setPadding(new Insets(10));
        centerSection.setSpacing(0);

        return centerSection;
    }

    //Main Function
    public static void main(String[] args) {
        launch(args);
    }
}
