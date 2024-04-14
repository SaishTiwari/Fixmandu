package build.fixmandu;

import build.fixmandu.config.DatabaseConfig;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
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

public class StaffAppointmentManagement extends Application {

    private TableView<CustomerQuotation> tableView;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Staff Appointment Management");

        // Logo
        Image logoImage = new Image("file:/Users/saishtiwari/Documents/logo.png");
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(200); // Adjust width as needed
        logoImageView.setPreserveRatio(true);

        // Navigation Buttons
        Button homeButton = new Button("Home");
        Button appointmentButton = new Button("Appointments");

        Button appointmentsButton = new Button("Appointments");
        Button reportsButton = new Button("Reports");
        Button registerCustomerButton = new Button("Register New Customer"); // New button
        Button logoutButton = new Button("Logout");


        // Button actions
        homeButton.setOnAction(e -> showHomePage(primaryStage));
        appointmentButton.setOnAction(e -> {}); // No action needed as we're already on the staff appointment management page

        reportsButton.setOnAction(e -> {
            // Implement navigation to the reports page
            showReportsPage(primaryStage);
        });

        registerCustomerButton.setOnAction(e -> {
            // Implement navigation to the customer signup page
            showStaffRegisterCustomerPage(primaryStage);
        });

        logoutButton.setOnAction(e -> {
            // Implement logout functionality
            // For example:
            primaryStage.close(); // Close current window
            // Open login page
            LoginPage loginPage = new LoginPage();
            loginPage.start(new Stage());
        });

        // Button container
        HBox navigationBar = new HBox(10);
        navigationBar.getChildren().addAll(homeButton, appointmentButton, registerCustomerButton, reportsButton, logoutButton);
        navigationBar.setPadding(new Insets(10));

        // Separator for the horizontal line
        Separator separator = new Separator();

        // Top section layout
        HBox topSection = new HBox(10);
        topSection.getChildren().addAll(logoImageView, separator, navigationBar);
        topSection.setPadding(new Insets(10));
        topSection.setStyle("-fx-background-color: white");

        // Table view to display customer quotations
        tableView = new TableView<>();
        // Define table columns
        TableColumn<CustomerQuotation, Integer> customerIdCol = new TableColumn<>("Customer ID");
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId")); // Map to customerId

        TableColumn<CustomerQuotation, String> customerNameCol = new TableColumn<>("Customer Name");
        customerNameCol.setCellValueFactory(cellData -> {
            int customerId = cellData.getValue().getCustomerId();
            return new SimpleStringProperty(getCustomerName(customerId)); // Retrieve customer name based on customerId
        });

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

        TableColumn<CustomerQuotation, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button acceptButton = new Button("Accept");
            private final Button rejectButton = new Button("Reject");

            {
                acceptButton.setOnAction(event -> {
                    CustomerQuotation quotation = getTableView().getItems().get(getIndex());
                    updateQuotationStatus(quotation.getId(), "accepted");
                    showAlert("Success", "Quotation accepted!");
                    loadDataFromDatabase();
                });

                rejectButton.setOnAction(event -> {
                    CustomerQuotation quotation = getTableView().getItems().get(getIndex());
                    showRejectDialog(quotation);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttonBox = new HBox(5);
                    buttonBox.getChildren().addAll(acceptButton, rejectButton);
                    setGraphic(buttonBox);
                }
            }
        });

        // Set preferred width for the action column
        actionCol.setPrefWidth(150); // Adjust width as needed

        tableView.getColumns().addAll(customerIdCol, customerNameCol, serviceTypeCol, descriptionCol, locationCol, urgentCol, priceCol, statusCol, actionCol);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topSection);
        borderPane.setCenter(tableView);

        Scene scene = new Scene(borderPane, 800, 600);
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
            showAlert("Error", "Failed to load appointment data from the database.");
        }
    }

    private void updateQuotationStatus(int quotationId, String status) {
        String updateSql = "UPDATE Quotations SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, quotationId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to update quotation status in the database.");
        }
    }



    private String getCustomerName(int customerId) {
        String customerName = "";

        // SQL query to retrieve customer name from UserLog based on customerId
        String sql = "SELECT username FROM UserLog WHERE id = ? AND user_type = 'customer'";

        try (
                Connection conn = DatabaseConfig.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, customerId);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                customerName = resultSet.getString("username");
            } else {
                customerName = "Unknown"; // Default to "Unknown" if customer name not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to retrieve customer name from the database.");
        }

        return customerName;
    }

    private void showRejectDialog(CustomerQuotation quotation) {
        // Dialog for rejecting with description input
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Reject Quotation");
        dialog.setHeaderText(null);
        dialog.setContentText("Please provide a reason for rejection:");

        // Get user's input for the rejection reason
        dialog.showAndWait().ifPresent(description -> {
            updateQuotationStatus(quotation.getId(), "rejected");
            showAlert("Quotation Rejected", "Quotation rejected with description: " + description);
            loadDataFromDatabase();
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showHomePage(Stage primaryStage) {
        StaffHomePage homePage = new StaffHomePage();
        homePage.start(primaryStage);
    }

    private void showAppointmentsPage(Stage primaryStage) {
        StaffAppointmentManagement appointmentsPage = new StaffAppointmentManagement();
        appointmentsPage.start(primaryStage);
    }

    // Method to show the reports page
    private void showReportsPage(Stage primaryStage) {
         //StaffReportViewer reportsPage = new StaffReportViewer();
       // reportsPage.start(primaryStage);
    }

    // Method to show the customer signup page
    private void showStaffRegisterCustomerPage(Stage primaryStage) {
        StaffRegisterCustomerPage staffRegisterCustomerPage = new StaffRegisterCustomerPage();
        staffRegisterCustomerPage.start(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
