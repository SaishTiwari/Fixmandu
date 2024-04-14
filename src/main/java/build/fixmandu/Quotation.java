package build.fixmandu;

import build.fixmandu.config.DatabaseConfig;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Quotation extends Application {

    private Connection conn;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Quotation Submission");

        // Load logo image
        Image logoImage = new Image("file:/Users/saishtiwari/Documents/logo.png");
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(200); // Adjust width as needed
        logoImageView.setPreserveRatio(true);

        // Navigation buttons
        Button homeButton = new Button("Home");
        Button quotationButton = new Button("Quotation");
        Button appointmentButton = new Button("Appointment History");
        Button aboutButton = new Button("About Us");
        Button logoutButton = new Button("Logout");

        // Button actions
        homeButton.setOnAction(e -> showHomePage(primaryStage));
        quotationButton.setOnAction(e -> showQuotationPage(primaryStage));
        appointmentButton.setOnAction(e -> showAppointmentsPage(primaryStage));
        aboutButton.setOnAction(e -> showAboutUsPage(primaryStage));
        logoutButton.setOnAction(e -> logout());

        // Top section layout
        HBox topSection = new HBox(10);
        topSection.getChildren().addAll(
                logoImageView, homeButton, quotationButton, appointmentButton, aboutButton, logoutButton
        );
        topSection.setPadding(new Insets(10));
        topSection.setStyle("-fx-background-color: #f0f0f0");

        // UI Components for quotation submission
        Label serviceTypeLabel = new Label("Service Type:");
        ComboBox<String> serviceTypeComboBox = new ComboBox<>();
        serviceTypeComboBox.getItems().addAll("Plumber", "Electrician", "Cleaner", "Carpenter");

        Label descriptionLabel = new Label("Description:");
        TextArea descriptionTextArea = new TextArea();

        Label locationLabel = new Label("Location:");
        TextField locationTextField = new TextField();

        CheckBox urgentCheckBox = new CheckBox("Urgent Service (Extra charges are applied)");

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String serviceType = serviceTypeComboBox.getValue();
            String description = descriptionTextArea.getText();
            String location = locationTextField.getText();
            boolean urgent = urgentCheckBox.isSelected();
            saveQuotation(serviceType, description, location, urgent);
        });

        // Layout for the form
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.add(serviceTypeLabel, 0, 0);
        gridPane.add(serviceTypeComboBox, 1, 0);
        gridPane.add(descriptionLabel, 0, 1);
        gridPane.add(descriptionTextArea, 1, 1);
        gridPane.add(locationLabel, 0, 2);
        gridPane.add(locationTextField, 1, 2);
        gridPane.add(urgentCheckBox, 1, 3);
        gridPane.add(submitButton, 1, 5);

        GridPane.setHgrow(locationTextField, Priority.ALWAYS);

        BorderPane root = new BorderPane();
        root.setTop(topSection);
        root.setCenter(gridPane);

        Scene scene = new Scene(root, 730, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Establish database connection
        establishDatabaseConnection();
    }

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

    private void saveQuotation(String serviceType, String description, String location, boolean urgent) {
        if (conn != null) {
            try {
                // Calculate price based on urgency
                double basePrice = 100.0;
                if (urgent) {
                    basePrice += 15.0;
                }

                // Insert quotation into the database
                String sql = "INSERT INTO Quotations (ServiceType, Description, Location, Urgent, Price) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, serviceType);
                pstmt.setString(2, description);
                pstmt.setString(3, location);
                pstmt.setBoolean(4, urgent);
                pstmt.setDouble(5, basePrice);

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    showAlert("Quotation Submitted", "Your quotation has been submitted successfully!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Database Error", "Failed to save quotation.");
            }
        } else {
            showAlert("Database Error", "Database connection is not established.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showHomePage(Stage primaryStage) {
        // Implement logic to show the Home page
        CustomerHomePage customerHomePage = new CustomerHomePage();
        customerHomePage.start(primaryStage);
    }

    private void showAboutUsPage(Stage primaryStage) {
        // Implement logic to show the About Us page
        AboutUs aboutUs = new AboutUs();
        aboutUs.start(primaryStage);
    }

    private void showAppointmentsPage(Stage primaryStage) {
        // Implement logic to show the Appointments page
        AppointmentHistory appointmentHistory = new AppointmentHistory();
        appointmentHistory.start(primaryStage);
    }

    private void showQuotationPage(Stage primaryStage) {
        // Implement logic to show the Quotation page
        Quotation quotation = new Quotation();
        quotation.start(primaryStage);
    }

    private void logout() {
        // Close the current stage (Quotation application)
        primaryStage.close();

        // Open the login page
        Stage loginStage = new Stage();
        LoginPage loginPage = new LoginPage();
        loginPage.start(loginStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
