package build.fixmandu;

import build.fixmandu.config.DatabaseConfig;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentHistory extends Application {

    private TableView<CustomerQuotation> tableView;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Appointment History");

        // Logo
        Image logoImage = new Image("file:/Users/saishtiwari/Documents/logo.png");
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(200); // Adjust width as needed
        logoImageView.setPreserveRatio(true);

        // Navigation Buttons
        Button homeButton = new Button("Home");
        Button quotationButton = new Button("Quotation");
        Button appointmentButton = new Button("Appointment History");
        Button aboutButton = new Button("About Us");
        Button logoutButton = new Button("Logout");

        // Button actions
        homeButton.setOnAction(e -> showHomePage(primaryStage));
        quotationButton.setOnAction(e -> showQuotationPage(primaryStage));
        appointmentButton.setOnAction(e -> {}); // No action needed as we're already on the appointment history page
        aboutButton.setOnAction(e -> showAboutUsPage(primaryStage));
        logoutButton.setOnAction(e -> showLoginPage(primaryStage));

        HBox navigationBar = new HBox(10);
        navigationBar.getChildren().addAll(homeButton, quotationButton, appointmentButton, aboutButton, logoutButton);
        navigationBar.setPadding(new Insets(10));

        // Separator for the horizontal line
        Separator separator = new Separator();

        // Top section layout
        HBox topSection = new HBox(10);
        topSection.getChildren().addAll(logoImageView, separator, navigationBar);
        topSection.setPadding(new Insets(10));
        topSection.setStyle("-fx-background-color: white");

        // Table view to display appointment history
        tableView = new TableView<>();
        TableColumn<CustomerQuotation, String> serviceTypeCol = new TableColumn<>("Service Type");
        serviceTypeCol.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        TableColumn<CustomerQuotation, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        TableColumn<CustomerQuotation, String> locationCol = new TableColumn<>("Location");
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        TableColumn<CustomerQuotation, Boolean> urgentCol = new TableColumn<>("Urgent");
        urgentCol.setCellValueFactory(new PropertyValueFactory<>("urgent"));
        TableColumn<CustomerQuotation, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<CustomerQuotation, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableView.getColumns().addAll(serviceTypeCol, descriptionCol, locationCol, urgentCol, priceCol, statusCol);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topSection);
        borderPane.setCenter(tableView);

        Scene scene = new Scene(borderPane, 730, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load data from the database initially
        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        ObservableList<CustomerQuotation> quotationList = FXCollections.observableArrayList();

        // SQL query to retrieve quotation data
        String sql = "SELECT * FROM Quotations";

        try (
                // Establish a database connection
                Connection conn = DatabaseConfig.getInstance().getConnection();
                // Create a PreparedStatement with the SQL query
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            // Execute the query and get the result set
            ResultSet resultSet = pstmt.executeQuery();

            // Iterate through the result set and add quotation data to the list
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int customerId = resultSet.getInt("customerId");

                String serviceType = resultSet.getString("serviceType");
                String description = resultSet.getString("description");
                String location = resultSet.getString("location");
                boolean urgent = resultSet.getBoolean("urgent");
                double price = resultSet.getDouble("price");
                String status = resultSet.getString("status");

                CustomerQuotation quotation = new CustomerQuotation(id, customerId, serviceType, description, location, urgent, price, status);
                quotationList.add(quotation);
            }

            // Set the data to the table view
            tableView.setItems(quotationList);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load appointment history from the database.");
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
        // Navigate to the home page
        CustomerHomePage homePage = new CustomerHomePage();
        homePage.start(primaryStage);
    }

    private void showQuotationPage(Stage primaryStage) {
        // Navigate to the quotation page
        Quotation quotationPage = new Quotation();
        quotationPage.start(primaryStage);
    }

    private void showAboutUsPage(Stage primaryStage) {
        // Navigate to the about us page
        AboutUs aboutUsPage = new AboutUs();
        aboutUsPage.start(primaryStage);
    }

    private void showLoginPage(Stage primaryStage) {
        // Navigate to the login page
        LoginPage loginPage = new LoginPage();
        loginPage.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
