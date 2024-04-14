package build.fixmandu;

import build.fixmandu.config.DatabaseConfig;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupPage extends Application {


    @Override
    public void start(Stage primaryStage) {
        // Setting the title as Signup
        primaryStage.setTitle("Signup");

        //Setting the grid with Vertical and Horizontal Gap
        GridPane grid = new GridPane(10,8);

        // Inserting Logo Image in the left top corner
        Image logoImage = new Image("file:/Users/saishtiwari/Documents/logo.png");
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(200); // Width is adjusted accordingly
        logoImageView.setPreserveRatio(true);
        GridPane.setConstraints(logoImageView, 0, 0, 2, 1); // Span 2 columns

        // Taking name as input
        Label nameLabel = new Label("Name:");
        GridPane.setConstraints(nameLabel, 0, 1);
        TextField nameInput = new TextField();
        nameInput.setPromptText("Name");
        GridPane.setConstraints(nameInput, 1, 1);

        // Taking Contact Number as input
        Label contactLabel = new Label("Contact Number:");
        GridPane.setConstraints(contactLabel, 0, 2);
        TextField contactInput = new TextField();
        contactInput.setPromptText("Contact Number");
        GridPane.setConstraints(contactInput, 1, 2);

        // Taking Email Address as input
        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 3);
        TextField emailInput = new TextField();
        emailInput.setPromptText("Email");
        GridPane.setConstraints(emailInput, 1, 3);

        // Taking User Type as input whether it is Staff or Customer
        Label userTypeLabel = new Label("User Type:");
        GridPane.setConstraints(userTypeLabel, 0, 4);
        ComboBox<String> userTypeComboBox = new ComboBox<>();
        userTypeComboBox.getItems().addAll("Customer", "Staff");
        userTypeComboBox.setValue("Customer");
        GridPane.setConstraints(userTypeComboBox, 1, 4);

        // Taking Password as input
        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 5);
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Password");
        GridPane.setConstraints(passwordInput, 1, 5);

        // Confirming Password to check whether the passsword is same or not
        Label confirmPasswordLabel = new Label("Confirm Password:");
        GridPane.setConstraints(confirmPasswordLabel, 0, 6);
        PasswordField confirmPasswordInput = new PasswordField();
        confirmPasswordInput.setPromptText("Confirm Password");
        GridPane.setConstraints(confirmPasswordInput, 1, 6);


        // Register Button to register with the information they provided
        Button registerButton = new Button("Register");
        GridPane.setConstraints(registerButton, 1, 7);

        registerButton.setOnAction(e -> {
            String name = nameInput.getText();
            String contact = contactInput.getText();
            String email = emailInput.getText();
            String userType = userTypeComboBox.getValue();
            String password = passwordInput.getText();
            String confirmPassword = confirmPasswordInput.getText();

            if (password.equals(confirmPassword)) {
                try (Connection connection = DatabaseConfig.getInstance().getConnection()) {
                    String sql = "INSERT INTO Signup (Name, ContactNumber, Email, UserType, Password) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, contact);
                    preparedStatement.setString(3, email);
                    preparedStatement.setString(4, userType);
                    preparedStatement.setString(5, password);
                    int rowsInserted = preparedStatement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("User registered successfully!");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Registration");
                        alert.setHeaderText(null);
                        alert.setContentText("User registered successfully!");
                        alert.showAndWait();

                        // Close the current signup window
                        Stage stage = (Stage) registerButton.getScene().getWindow();
                        stage.close();

                        // Open the login page
                        new LoginPage().start(new Stage());
                    }
                } catch (SQLException ex) {
                    System.out.println("Error registering user: " + ex.getMessage());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Error registering user: " + ex.getMessage());
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Passwords do not match!");
                alert.showAndWait();
            }
        });




        grid.getChildren().addAll(logoImageView, nameLabel, nameInput, contactLabel, contactInput, emailLabel, emailInput,
                userTypeLabel, userTypeComboBox, passwordLabel, passwordInput, confirmPasswordLabel, confirmPasswordInput,
                registerButton);

        // Scroll Pane and adding grid to it
        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true); // Allow the ScrollPane to resize horizontally
        scrollPane.setFitToHeight(true); // Allow the ScrollPane to resize vertically

        // Scene set up
        Scene scene = new Scene(scrollPane, 550, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}