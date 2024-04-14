package build.fixmandu;

import build.fixmandu.config.DatabaseConfig;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StaffRegisterCustomerPage extends Application {

    private Connection conn;
    private TextField nameField;
    private TextField contactNumberField;
    private TextField emailField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Staff Register Customer");

        // Logo
        ImageView logoImageView = new ImageView(new Image("file:/Users/saishtiwari/Documents/logo.png"));
        logoImageView.setFitWidth(200); // Adjust width as needed
        logoImageView.setPreserveRatio(true);

        // TextFields
        nameField = new TextField();
        nameField.setPromptText("Name");
        contactNumberField = new TextField();
        contactNumberField.setPromptText("Contact Number");
        emailField = new TextField();
        emailField.setPromptText("Email");
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");

        // Register Button
        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> registerCustomer());

        // Button container
        HBox buttonBox = new HBox(10); // spacing
        Button homeButton = new Button("Home");
        homeButton.setOnAction(e -> {
            // Implement navigation to the staff home page (do nothing as we're already on the home page)
        });
        Button appointmentsButton = new Button("Appointments");
        appointmentsButton.setOnAction(e -> showAppointmentsPage(primaryStage));
        Button reportsButton = new Button("Reports");
        reportsButton.setOnAction(e -> showReportsPage(primaryStage));
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            // Implement logout functionality
            primaryStage.close(); // Close current window
            // Open login page
            LoginPage loginPage = new LoginPage();
            loginPage.start(new Stage());
        });
        buttonBox.getChildren().addAll(homeButton, appointmentsButton, reportsButton, logoutButton);
        buttonBox.setPadding(new Insets(10));

        // Layout
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(createTopSection(logoImageView, buttonBox));
        borderPane.setCenter(createCenterSection());

        // Add register button to the bottom of the layout
        borderPane.setBottom(registerButton);
        BorderPane.setMargin(registerButton, new Insets(10));

        Scene scene = new Scene(borderPane, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Establish database connection
        establishDatabaseConnection();
    }

    // Method to create the top section with logo and navigation buttons
    private HBox createTopSection(ImageView logoImageView, HBox buttonBox) {
        HBox topSection = new HBox(10);
        topSection.getChildren().addAll(logoImageView, buttonBox, createSeparator()); // Add separator
        topSection.setPadding(new Insets(10));
        topSection.setStyle("-fx-background-color: white");
        return topSection;
    }

    // Method to create a separator without a border
    private Separator createSeparator() {
        Separator separator = new Separator();
        separator.setStyle("-fx-border-style: none");
        return separator;
    }

    // Method to create the center section with text fields
    private VBox createCenterSection() {
        VBox centerSection = new VBox(10);
        centerSection.setFillWidth(true);
        centerSection.setStyle("-fx-background-color: #f0f0f0"); // Set background color

        centerSection.getChildren().addAll(
                nameField, contactNumberField, emailField, passwordField, confirmPasswordField);
        centerSection.setPadding(new Insets(20));

        return centerSection;
    }

    // Method to establish database connection
    private void establishDatabaseConnection() {
        try {
            // Register MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            conn = DatabaseConfig.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            showAlert("Database Connection Error", "Failed to connect to the database.");
        }
    }

    // Method to register a new customer
    private void registerCustomer() {
        String name = nameField.getText();
        String contactNumber = contactNumberField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            showAlert("Password Mismatch", "Passwords do not match. Please re-enter.");
            passwordField.clear();
            confirmPasswordField.clear();
            return; // Exit method if passwords do not match
        }

        // Perform customer registration logic (insert into database)
        try {
            String sql = "INSERT INTO Signup (Name, ContactNumber, Email, UserType, Password) VALUES (?, ?, ?, 'Customer', ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, contactNumber);
            pstmt.setString(3, email);
            pstmt.setString(4, password);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                showAlert("Customer Registered", "New customer registered successfully!");
                // Clear input fields after successful registration
                nameField.clear();
                contactNumberField.clear();
                emailField.clear();
                passwordField.clear();
                confirmPasswordField.clear();
            }
        } catch (SQLException ex) {
            showAlert("Database Error", "Failed to register customer.");
            ex.printStackTrace();
        }
    }

    // Method to show the appointments page
    private void showAppointmentsPage(Stage primaryStage) {
        // Implement navigation to the appointments page
        StaffAppointmentManagement appointmentsPage = new StaffAppointmentManagement();
        appointmentsPage.start(primaryStage);
    }

    // Method to show the reports page
    private void showReportsPage(Stage primaryStage) {
        // Implement navigation to the reports page
        //StaffReportViewer reportsPage = new StaffReportViewer();
      //  reportsPage.start(primaryStage);
    }

    // Method to display an alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
