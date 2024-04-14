package build.fixmandu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AboutUs extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("About Us");

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
        homeButton.setOnAction(e -> showHomePage(primaryStage));
        quotationButton.setOnAction(e -> showQuotationPage(primaryStage));
        appointmentButton.setOnAction(e -> showAppointmentHistoryPage(primaryStage));
        aboutButton.setOnAction(e -> {});
        logoutButton.setOnAction(e -> showLoginPage(primaryStage));

        // Button container
        HBox buttonBox = new HBox(10); // spacing
        buttonBox.getChildren().addAll(homeButton, quotationButton, appointmentButton, aboutButton, logoutButton);
        buttonBox.setPadding(new Insets(10));

        // Layout
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(createTopSection(logoImageView, buttonBox));
        borderPane.setCenter(createCenterSection());
        borderPane.setBottom(createBottomSection());

        Scene scene = new Scene(borderPane, 730, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to create the top section with logo and navigation buttons
    private HBox createTopSection(ImageView logoImageView, HBox buttonBox) {
        HBox topSection = new HBox(10);
        topSection.getChildren().addAll(logoImageView, buttonBox);
        topSection.setPadding(new Insets(10));
        topSection.setStyle("-fx-background-color: white");
        return topSection;
    }

    private VBox createCenterSection() {
        Text aboutText = new Text("Welcome to Fixmandu, your premier destination for hassle-free fixing solutions in Nepal. At Fixmandu, we're not just a repair service provider; we're your trusted partner in making life easier through efficient solutions.\n\n"
                + "As the leading fix-it company in Nepal, we pride ourselves on our commitment to delivering top-notch service, ensuring that your valuable time is never wasted. We understand that every minute counts, which is why we're here to help. With Fixmandu, you can count on timely and reliable fixes so you can focus on what truly matters.\n\n"
                + "Our comprehensive range of services includes plumbing, electrician, and cleaning solutions, all aimed at making your life more convenient. Whether it's a leaky faucet, faulty wiring, or a home in need of a thorough cleaning, Fixmandu has got you covered.\n\n"
                + "With just a few clicks on our website, mobile app, or a simple call, you can avail yourself of our services from the comfort of your home. Our team of skilled professionals ensures that your needs are met with efficiency and precision.\n\n"
                + "Experience the convenience of Fixmandu today. Sit back, relax, and let us take care of the rest.");

        aboutText.setWrappingWidth(680); // Set the width of the text

        double fontSize = calculateOptimalFontSize(aboutText.getText(), 360);

        aboutText.setFont(Font.font(fontSize));

        // Set the text alignment to justify
        aboutText.setStyle("-fx-text-alignment: justify");

        VBox centerSection = new VBox(10);
        centerSection.getChildren().addAll(aboutText);
        centerSection.setPadding(new Insets(20));

        return centerSection;
    }

    // Method to create the bottom section with contact information
    private HBox createBottomSection() {
        Text contactText = new Text("Contact Us: +977-9861938402");
        contactText.setFont(Font.font("Arial", 16));

        HBox bottomSection = new HBox();
        bottomSection.getChildren().add(contactText);
        bottomSection.setPadding(new Insets(10));
        bottomSection.setStyle("-fx-background-color: lightgrey");
        bottomSection.setAlignment(javafx.geometry.Pos.CENTER);

        return bottomSection;
    }

    private double calculateOptimalFontSize(String text, double wrappingWidth) {
        Text helperText = new Text();
        helperText.setText(text);
        helperText.setWrappingWidth(wrappingWidth);

        double fontSize = 100;

        while (helperText.getBoundsInLocal().getHeight() > wrappingWidth) {
            fontSize -= 2;
            helperText.setFont(Font.font(fontSize));
        }

        return fontSize;
    }

    private void showHomePage(Stage primaryStage) {
        CustomerHomePage homePage = new CustomerHomePage();
        homePage.start(primaryStage);
    }

    private void showQuotationPage(Stage primaryStage) {
        Quotation quotationPage = new Quotation();
        quotationPage.start(primaryStage);
    }

    private void showAppointmentHistoryPage(Stage primaryStage) {
        AppointmentHistory appointmentHistoryPage = new AppointmentHistory();
        appointmentHistoryPage.start(primaryStage);
    }

    private void showLoginPage(Stage primaryStage) {
        LoginPage loginPage = new LoginPage();
        loginPage.start(primaryStage);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
