package build.fixmandu;

import build.fixmandu.config.DatabaseConfig;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("FixMandu");

        GridPane grid = new GridPane(10, 8);


        // Inserting Logo Image into the top left corner of the page
        Image logoImage = new Image("file:/Users/saishtiwari/Documents/logo.png");
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(200);
        logoImageView.setPreserveRatio(true);
        GridPane.setConstraints(logoImageView, 0, 0, 2, 1);


        // Inserting a box to take input from the Users and save it as Username
        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 1);
        TextField usernameInsert = new TextField();
        usernameInsert.setPromptText("Username");
        GridPane.setConstraints(usernameInsert, 1, 1);

        // Making a box to insert password, using passwordfield so the password is not seen
        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 2);
        PasswordField passwordInsert = new PasswordField();
        passwordInsert.setPromptText("Password");
        GridPane.setConstraints(passwordInsert, 1, 2);

        // Login Button
        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 3);
        loginButton.setOnAction(e -> {
            String username = usernameInsert.getText();
            String password = passwordInsert.getText();
            login(username, password, primaryStage);
        });


        //Message for new users
        Hyperlink newUserLink = new Hyperlink("New user? Click here");
        GridPane.setConstraints(newUserLink, 1, 5); // Showing the button below the login page

        newUserLink.setOnAction(e -> {
            SignupPage signupPage = new SignupPage();
            signupPage.start(primaryStage);
        });

        grid.getChildren().addAll(logoImageView, usernameLabel, newUserLink, usernameInsert, passwordLabel, passwordInsert, loginButton);

        Scene scene = new Scene(grid, 550, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void login(String username, String password, Stage primaryStage) {
        try (Connection connection = DatabaseConfig.getInstance().getConnection()) {
            String sql = "SELECT * FROM Signup WHERE Name = ? AND Password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                showAlert("Success", "Login successful!");
                String userType = resultSet.getString("UserType");
                UserLogin(username, userType);

                if ("customer".equalsIgnoreCase(userType)) {
                    ToCustomerHomePage(primaryStage);
                } else if ("staff".equalsIgnoreCase(userType)) {
                    ToStaffHomePage(primaryStage);
                } else {
                    showAlert("Error", "User Type is invalid");
                }
            } else {
                showAlert("Error", "Username or Password is invalid");
            }
        } catch (SQLException ex) {
            showAlert("Error", "Logging in Error " + ex.getMessage());
        }
    }

    private void UserLogin(String username, String userType) {
        try (Connection connection = DatabaseConfig.getInstance().getConnection()) {
            String sql = "INSERT INTO UserLog (username, user_type) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, userType);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error inserting user log: " + ex.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void ToCustomerHomePage(Stage primaryStage) {
        CustomerHomePage customerHomePage = new CustomerHomePage();
        customerHomePage.start(primaryStage);
    }

    private void ToStaffHomePage(Stage primaryStage) {
        StaffHomePage staffHomePage = new StaffHomePage();
        staffHomePage.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
