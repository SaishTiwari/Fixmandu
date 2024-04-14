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

public class CustomerHomePage extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Home");

        // Logo
        ImageView logoImageView = new ImageView(new Image("file:/Users/saishtiwari/Documents/logo.png"));
        logoImageView.setFitWidth(200); // Adjust width as needed
        logoImageView.setPreserveRatio(true);

        // Buttons
        Button homeButton = new Button("Home");
        Button quotationButton = new Button("Quotation");
        Button appointmentButton = new Button("Appointment History");
        Button aboutButton = new Button("About Us");
        Button logoutButton = new Button("Logout");

        // Button actions
        homeButton.setOnAction(e -> {
            // Implement navigation to the home page (do nothing as we're already on the home page)
        });

        quotationButton.setOnAction(e -> {
            // Implement navigation to the quotation page
            showQuotation(primaryStage);
        });

        appointmentButton.setOnAction(e -> {
            showAppointment(primaryStage);
        });

        aboutButton.setOnAction(e -> {
            // Create and show the About Us page
            showAboutUs(primaryStage);
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
        HBox buttonBox = new HBox(10); // spacing
        buttonBox.getChildren().addAll(homeButton, quotationButton, appointmentButton, aboutButton, logoutButton);
        buttonBox.setPadding(new Insets(10));

        // Layout
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(createTopSection(logoImageView, buttonBox));
        borderPane.setCenter(createCenterSection());

        Scene scene = new Scene(borderPane, 730, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
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

    // Method to create and show the About Us page
    private void showAboutUs(Stage primaryStage) {
        AboutUs aboutUsPage = new AboutUs();
        aboutUsPage.start(primaryStage);
    }

    // Method to create and show the Quotation page
    private void showQuotation(Stage primaryStage) {
        Quotation quotationPage = new Quotation();
        quotationPage.start(primaryStage);
    }

    private void showAppointment(Stage primaryStage) {
        AppointmentHistory appointmentHistorypage = new AppointmentHistory();
        appointmentHistorypage.start(primaryStage);
    }

    // Method to create the center section with image
    private VBox createCenterSection() {
        VBox centerSection = new VBox();
        centerSection.setFillWidth(true);
        centerSection.setStyle("-fx-background-color: #f0f0f0"); // Set background color

        // Image
        Image centerImage = new Image("file:/Users/saishtiwari/Documents/Homeimage.jpeg");
        ImageView centerImageView = new ImageView(centerImage);
        centerImageView.setPreserveRatio(true);
        centerImageView.setFitWidth(730); // Adjust width as needed

        centerSection.getChildren().add(centerImageView);
        centerSection.setPadding(new Insets(10));
        centerSection.setSpacing(0);

        return centerSection;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
