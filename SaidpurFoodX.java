/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package saidpurfoodx;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.stage.Modality;
public class SaidpurFoodX extends Application {     
    private List<Restaurant> restaurants;
    private ArrayList<User> users;
    private ArrayList<Owner> owners;
    private List<Review> reviews;
    private List<Order> orders;
    private User currentUser;
    private Owner currentOwner;
    private Stage primaryStage;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SaidpurFoodX");
        Image icon = new Image("file:///C:/Users/USER/Downloads/SAIDPUR.JPG/");
        primaryStage.getIcons().add(icon);

        initializeData();
        showRoleSelectionScene(primaryStage);
    }
    private void initializeData() {
    restaurants = new ArrayList<>();
    users = new ArrayList<>();
    owners = new ArrayList<>();
    reviews = new ArrayList<>();
    orders = new ArrayList<>();
   
    loadUsersFromFile();
    loadOwnersFromFile();
    loadRestaurantsFromFile();
    loadReviewsFromFile();
    loadOrdersFromFile();
    
    if (owners.isEmpty()) {
        owners.add(new Owner("owner1", "password1", "Eque", "0001")); // Example restaurant name and code
        owners.add(new Owner("owner2", "password2", "Karifarms kitchen", "0002")); // Example restaurant name and code
        saveOwnersToFile();
    }
    
    if (restaurants.isEmpty()) {
        initializeSampleRestaurants();
        saveRestaurantsToFile();
    }
}
    private void showRoleSelectionScene(Stage primaryStage) {  
        Image backgroundImage = new Image("file:///C:/Users/USER/Downloads/SAIDPUR_logo.JPG");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1000);
        backgroundImageView.setFitHeight(700);
        backgroundImageView.setPreserveRatio(false);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(backgroundImageView);  
    VBox layout = new VBox(20);
    layout.setAlignment(Pos.BOTTOM_CENTER);
    layout.setPadding(new Insets(50));

    Label titleLabel = new Label("Welcome to SaidpurFoodX");
    titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

    Button userButton = new Button("Continue as User");
    userButton.setPrefWidth(170);
    userButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white; -fx-font-size: 16px;");
    userButton.setOnAction(e -> showUserLoginScene(primaryStage));

    Button ownerButton = new Button("Continue as Owner");
    ownerButton.setPrefWidth(170);
    ownerButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white; -fx-font-size: 16px;");
    ownerButton.setOnAction(e -> showOwnerLoginScene(primaryStage));

    layout.getChildren().addAll(titleLabel, userButton, ownerButton);
    stackPane.getChildren().add(layout);
    
    Scene scene = new Scene(stackPane, 1000, 700);
    primaryStage.setScene(scene);
    primaryStage.show();
}
     private void showUserLoginScene(Stage primaryStage) {
    Image backgroundImage = new Image("file:///C:/Users/USER/Downloads/foodmenu.jpg");
    ImageView backgroundImageView = new ImageView(backgroundImage);
    backgroundImageView.setFitWidth(1000);
    backgroundImageView.setFitHeight(700);
    backgroundImageView.setPreserveRatio(false);

    StackPane stackPane = new StackPane();
    stackPane.getChildren().add(backgroundImageView);

    VBox loginLayout = new VBox(20);
    loginLayout.setAlignment(Pos.CENTER);
    loginLayout.setPadding(new Insets(50));

    Label loginLabel = new Label("User  Login");
    loginLabel.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF");

    TextField usernameField = new TextField();
    usernameField.setPromptText("Username");
    PasswordField passwordField = new PasswordField();
    passwordField.setPromptText("Password");

    Button loginButton = new Button("Login");
    loginButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white; -fx-font-size: 16px;");
    loginButton.setPrefWidth(150);
    loginButton.setOnAction(e -> {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (validateUser (username, password)) {
            currentUser  = getUserByUsername(username);
            showUserWelcomeScene(primaryStage);
        } else {
            showAlert("Invalid username or password!");
        }
    });

    Button registerButton = new Button("Register");
    registerButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white; -fx-font-size: 16px;");
    registerButton.setPrefWidth(150);
    registerButton.setOnAction(e -> showUserRegistrationScene(primaryStage));

    HBox backButtonLayout = new HBox(30);
    backButtonLayout.setAlignment(Pos.BOTTOM_RIGHT); 
    Button backButton = new Button("Back");
    backButton.setOnAction(e -> showRoleSelectionScene(primaryStage));
    backButtonLayout.getChildren().add(backButton);

    Button forgotPasswordButton = new Button("Forgot Password?");
    forgotPasswordButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white; -fx-font-size: 16px;");
    forgotPasswordButton.setPrefWidth(150);
    forgotPasswordButton.setOnAction(e -> showForgotPasswordScene(primaryStage));

    loginLayout.getChildren().addAll(loginLabel, usernameField, passwordField, loginButton, registerButton, forgotPasswordButton);

    VBox mainLayout = new VBox();
    mainLayout.getChildren().addAll(loginLayout, backButtonLayout);
    VBox.setVgrow(loginLayout, Priority.ALWAYS); 

    stackPane.getChildren().add(mainLayout);

    Scene loginScene = new Scene(stackPane, 1000, 700);
    primaryStage.setScene(loginScene);
}

    private void showUserRegistrationScene(Stage primaryStage) {
        Image backgroundImage = new Image("file:///C:/Users/USER/Downloads/foodmenu.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1000);
        backgroundImageView.setFitHeight(700);
        backgroundImageView.setPreserveRatio(false);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(backgroundImageView);

        VBox registerLayout = new VBox(20);
        registerLayout.setAlignment(Pos.CENTER);
        registerLayout.setPadding(new Insets(50));

        Label registerLabel = new Label("User Registration");
        registerLabel.setStyle("-fx-font-size: 40px; -fx-font-weight: bold;-fx-text-fill: #FFFFFF");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Phone Number");
        passwordField.setPromptText("Password");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        

    Button registerButton = new Button("Register");
    registerButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white; -fx-font-size: 16px;");
    registerButton.setPrefWidth(150);
    registerButton.setOnAction(e -> {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String phoneNumber = phoneNumberField.getText();

        if (username.isEmpty() || password.isEmpty() || phoneNumber.isEmpty()) {
            showAlert("Username, password, and phone number cannot be empty!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Passwords do not match!");
            return;
        }

        if (isUsernameExists(username)) {
            showAlert("Username already exists!");
            return;
        }

        if (!isValidPhoneNumber(phoneNumber)) {
            showAlert("Invalid phone number!");
            return;
        }

        users.add(new User(username, password, phoneNumber));
        saveUsersToFile();
        showAlert("Registration successful!");
        showUserLoginScene(primaryStage);
    });

        Button backButton = new Button("Back to Login");
        backButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white; -fx-font-size: 16px;");
        backButton.setPrefWidth(150);
        backButton.setOnAction(e -> showUserLoginScene(primaryStage));

        registerLayout.getChildren().addAll(registerLabel, usernameField,phoneNumberField, passwordField, confirmPasswordField, registerButton, backButton);
        stackPane.getChildren().add(registerLayout);

        Scene registerScene = new Scene(stackPane, 1000, 700);
        primaryStage.setScene(registerScene);
    }
    
    private void showForgotPasswordScene(Stage primaryStage) {
    Image backgroundImage = new Image("file:///C:/Users/USER/Downloads/foodmenu.jpg");
    ImageView backgroundImageView = new ImageView(backgroundImage);
    backgroundImageView.setFitWidth(1000);
    backgroundImageView.setFitHeight(700);
    backgroundImageView.setPreserveRatio(false);

    StackPane stackPane = new StackPane();
    stackPane.getChildren().add(backgroundImageView);

    VBox layout = new VBox(20);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(50));

    Label forgotPasswordLabel = new Label("Reset Password");
    forgotPasswordLabel.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF");

    TextField usernameField = new TextField();
    usernameField.setPromptText("Username");

    TextField phoneNumberField = new TextField();
    phoneNumberField.setPromptText("Phone Number");

    Button resetButton = new Button("Reset Password");
    resetButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white; -fx-font-size: 16px;");
    resetButton.setOnAction(e -> {
        String username = usernameField.getText();
        String phoneNumber = phoneNumberField.getText();

        User user = getUserByUsernameAndPhoneNumber(username, phoneNumber);
        if (user != null) {
            showNewPasswordScene(primaryStage, user);
        } else {
            showAlert("Username or phone number does not exist!");
        }
    });

    Button backButton = new Button("Back to Login");
    backButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white; -fx-font-size: 16px;");
    backButton.setOnAction(e -> showUserLoginScene(primaryStage));

    layout.getChildren().addAll(forgotPasswordLabel, usernameField, phoneNumberField, resetButton, backButton);
    stackPane.getChildren().add(layout);

    Scene scene = new Scene(stackPane, 1000, 700);
    primaryStage.setScene(scene);
}
    
    private void showNewPasswordScene(Stage primaryStage, User user) {
    Stage dialog = new Stage();
    dialog.setTitle("Set New Password");

    VBox layout = new VBox(20);
    layout.setPadding(new Insets(20));
    layout.setStyle("-fx-background-color: #E9F4E9;");

    Label newPasswordLabel = new Label("Enter New Password:");
    PasswordField newPasswordField = new PasswordField();
    newPasswordField.setPromptText("New Password");

    Button submitButton = new Button("Submit");
    submitButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white;");
    submitButton.setOnAction(e -> {
        String newPassword = newPasswordField.getText();
        if (newPassword.isEmpty()) {
            showAlert("Password cannot be empty!");
            return;
        }
        user.setPassword(newPassword); 
        saveUsersToFile(); 
        showAlert("Password reset successfully!");
        dialog.close();
        showUserLoginScene(primaryStage); 
    });

    Button cancelButton = new Button("Cancel");
    cancelButton.setOnAction(e -> dialog.close());

    layout.getChildren().addAll(newPasswordLabel, newPasswordField, submitButton, cancelButton);
    Scene scene = new Scene(layout, 400, 300);
    dialog.setScene(scene);
    dialog.show();
}

    private void showUserWelcomeScene(Stage primaryStage) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50));
        layout.setStyle("-fx-background-color: #E9F4E9");

        Label welcomeLabel = new Label("Welcome, " + currentUser.getUsername() + "!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button browseRestaurantsButton = new Button("Browse Restaurants");
        browseRestaurantsButton.setPrefWidth(170);
        browseRestaurantsButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white; -fx-font-size: 16px;");
        browseRestaurantsButton.setOnAction(e -> showRestaurantList(primaryStage));

        Button viewOrdersButton = new Button("View My Orders");
        viewOrdersButton.setPrefWidth(170);
        viewOrdersButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white; -fx-font-size: 16px;");
        viewOrdersButton.setOnAction(e -> showUserOrders(primaryStage));

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            currentUser = null;
            showRoleSelectionScene(primaryStage);
        });
        layout.getChildren().addAll(welcomeLabel, browseRestaurantsButton, viewOrdersButton, logoutButton);     
        Scene scene = new Scene(layout, 1000, 700);
        primaryStage.setScene(scene);
    }
   private void showRestaurantList(Stage primaryStage) {
        ListView<String> restaurantListView = new ListView<>();
        for (Restaurant restaurant : restaurants) {
            restaurantListView.getItems().add(restaurant.getName());
        }

        TextField searchField = new TextField();
        searchField.setPromptText("Search for restaurants");
        searchField.setStyle("-fx-font-size: 16px;");

        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white; -fx-font-size: 16px;");
        searchButton.setOnAction(event -> {
            String searchText = searchField.getText().toLowerCase();
            restaurantListView.getItems().clear();
            for (Restaurant restaurant : restaurants) {
                if (restaurant.getName().toLowerCase().contains(searchText)) {
                    restaurantListView.getItems().add(restaurant.getName());
                }
            }
        });

        restaurantListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedRestaurant = restaurantListView.getSelectionModel().getSelectedItem();
                showRestaurantDetails(primaryStage, selectedRestaurant);
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showUserWelcomeScene(primaryStage));

        VBox layout = new VBox(20, searchField, searchButton, restaurantListView, backButton);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #E9F4E9;");

        Scene scene = new Scene(layout, 1000, 700);
        primaryStage.setScene(scene);
    }

    private void showRestaurantDetails(Stage primaryStage, String restaurantName) {
        Restaurant restaurant = getRestaurantByName(restaurantName);
        if (restaurant == null) return;

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #E9F4E9;");

        Label nameLabel = new Label(restaurant.getName());
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        ImageView imageView = new ImageView(new Image(restaurant.getImagePath()));
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);

        Label locationLabel = new Label("Location: " + restaurant.getLocation());
        Label contactLabel = new Label("Pre-order Contact: " + restaurant.getContactNumber());

        Button menuButton = new Button("View Menu");
        menuButton.setPrefWidth(100);
        menuButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white;");
        menuButton.setOnAction(e -> showRestaurantMenu(primaryStage, restaurant));

        Button reviewsButton = new Button("View Reviews");
        reviewsButton .setPrefWidth(100);
        reviewsButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white;");
        reviewsButton.setOnAction(e -> showRestaurantReviews(primaryStage, restaurant));

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showRestaurantList(primaryStage));

        layout.getChildren().addAll(nameLabel, imageView, locationLabel, contactLabel, 
                                 menuButton, reviewsButton, backButton);

        Scene scene = new Scene(layout, 1000, 700);
        primaryStage.setScene(scene);
    }

    private void showRestaurantMenu(Stage primaryStage, Restaurant restaurant) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #E9F4E9;");

        Label titleLabel = new Label(restaurant.getName() + " Menu");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ScrollPane scrollPane = new ScrollPane();
        VBox menuItemsLayout = new VBox(10);

        for (FoodItem item : restaurant.getMenuItems()) {
            HBox itemBox = new HBox(20);
            itemBox.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-radius: 5;");

            ImageView imageView = new ImageView(new Image(item.getImagePath()));
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);

            VBox detailsBox = new VBox(5);
            Label nameLabel = new Label(item.getName());
            nameLabel.setStyle("-fx-font-weight: bold;");
            Label priceLabel = new Label("Price: " + item.getPrice());
            Label descLabel = new Label(item.getDescription());
            descLabel.setWrapText(true);

            Spinner<Integer> quantitySpinner = new Spinner<>(1, 10, 1);
            Button orderButton = new Button("Order");
            orderButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white;");
            orderButton.setOnAction(e -> {
                int quantity = quantitySpinner.getValue();
                showOrderConfirmation(primaryStage, restaurant, item, quantity);
            });

            detailsBox.getChildren().addAll(nameLabel, priceLabel, descLabel, quantitySpinner, orderButton);
            itemBox.getChildren().addAll(imageView, detailsBox);
            menuItemsLayout.getChildren().add(itemBox);
        }

        scrollPane.setContent(menuItemsLayout);
        scrollPane.setFitToWidth(true);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showRestaurantDetails(primaryStage, restaurant.getName()));

        layout.getChildren().addAll(titleLabel, scrollPane, backButton);

        Scene scene = new Scene(layout, 1000, 700);
        primaryStage.setScene(scene);
    }

    private void showOrderConfirmation(Stage primaryStage, Restaurant restaurant, FoodItem item, int quantity) {
    VBox layout = new VBox(20);
    layout.setPadding(new Insets(20));
    layout.setStyle("-fx-background-color: #E9F4E9;");

    Label titleLabel = new Label("Confirm Your Order");
    titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

    Label itemLabel = new Label("Item: " + item.getName());
    Label quantityLabel = new Label("Quantity: " + quantity);
    Label priceLabel = new Label("Price: " + item.getPrice());

    ComboBox<String> locationComboBox = new ComboBox<>();
    locationComboBox.getItems().addAll(
        "Parbotipur", "Plaza", "Kayanizpara", "Munshipara", 
        "Nutun Babupara", "Puraton Babupara", "Dinajpur Road", 
        "Rangpur Road", "Paoroshova", "Airport Road", 
        "CSD", "Cantonment Public School", "Terminal", 
        "BAUST", "100bed hospital", "Darlum", 
        "Upazila", "Noyatola", "5star math"
    );
    locationComboBox.setPromptText("Select Delivery Location");

    TextField phoneField = new TextField();
    phoneField.setPromptText("Phone Number");

    Label deliveryChargeLabel = new Label("Delivery Charge: 0.00 BDT"); 
    Label totalPriceLabel = new Label("Total: 0.00 BDT"); 

    locationComboBox.setOnAction(event -> {
        String selectedLocation = locationComboBox.getValue();
        double deliveryCharge = calculateDeliveryCharge(selectedLocation);
        double itemPrice = Double.parseDouble(item.getPrice().replaceAll("[^0-9.]", ""));
        double totalPrice = (itemPrice * quantity) + deliveryCharge;

        deliveryChargeLabel.setText(String.format("Delivery Charge: %.2f BDT", deliveryCharge));
        totalPriceLabel.setText(String.format("Total: %.2f BDT", totalPrice));
    });

    Button confirmButton = new Button("Confirm Order");
    confirmButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white;");
    confirmButton.setOnAction(e -> {
    if (locationComboBox.getValue() == null || phoneField.getText().isEmpty()) {
        showAlert("Please select a delivery location and enter your phone number!");
        return;
    }

    String phoneNumber = phoneField.getText().trim();
    if (!isValidPhoneNumber(phoneNumber)) {
        showAlert("Please enter a valid 11-digit phone number (without +88)!");
        return;
    }

    double deliveryCharge = calculateDeliveryCharge(locationComboBox.getValue());
    double itemPrice = Double.parseDouble(item.getPrice().replaceAll("[^0-9.]", ""));
    double totalPrice = (itemPrice * quantity) + deliveryCharge;

    Order order = new Order(
        currentUser .getUsername(),
        restaurant.getName(),
        item.getName(),
        quantity,
        item.getPrice(),
        locationComboBox.getValue(),
        phoneNumber,
        "Pending"
    );

    orders.add(order);
    saveOrdersToFile();
    showAlert("Order placed successfully!");
    showRestaurantMenu(primaryStage, restaurant);
});
   
    Button cancelButton = new Button("Cancel");
    Stage stage = new Stage();
    cancelButton.setOnAction(e -> {
        stage.close();
    });

    layout.getChildren().addAll(titleLabel, itemLabel, quantityLabel, priceLabel, 
                                 deliveryChargeLabel, totalPriceLabel,
                                 locationComboBox, phoneField, confirmButton, cancelButton);

    Scene scene = new Scene(layout, 700, 500);
    stage.setScene(scene);
    stage.setTitle("Order Confirmation");
    stage.show();
}
private double calculateDeliveryCharge(String location) {
    if (location.equalsIgnoreCase("Parbotipur")) {
        return 75.0; 
    } else if (location.equalsIgnoreCase("Plaza"))   {    
        return 30.0; 
    }  else if(location.equalsIgnoreCase("Kayanizpara")){
        return 35.0;
    } else if(location.equalsIgnoreCase("Munshipara")){
        return 37.0;
    } else if(location.equalsIgnoreCase("Nutun Babupara")){
        return 40.0;
    } else if(location.equalsIgnoreCase("Puraton Babupara")){
        return 45.0;
    } else if(location.equalsIgnoreCase("Dinajpur Road")){
        return 50.0;
    } else if(location.equalsIgnoreCase("Rangpur Road")){
        return 60.0;
    } else if(location.equalsIgnoreCase("Paoroshova")){
        return 45.0;
    } else if(location.equalsIgnoreCase("Airport Road")){
        return 47.0;
    } else if(location.equalsIgnoreCase("CSD")){
        return 42.0;
    } else if(location.equalsIgnoreCase("Cantonment Public School")){
        return 43.0;
    } else if(location.equalsIgnoreCase("Terminal")){
        return 65.0;
    } else if(location.equalsIgnoreCase("BAUST")){
        return 68.0;
    } else if(location.equalsIgnoreCase("100bed hospital")){
        return 72.0;
    } else if(location.equalsIgnoreCase("Darlum")){
        return 49.0;
    } else if(location.equalsIgnoreCase("Upazila")){
        return 40.0;
    } else if(location.equalsIgnoreCase("Noyatola")){
        return 36.0;
    } else if(location.equalsIgnoreCase("5star math")){
        return 40.0;
    } else {
    
    return 100.0; 
}}
   private void showRestaurantReviews(Stage primaryStage, Restaurant restaurant) {
    VBox layout = new VBox(20);
    layout.setPadding(new Insets(20));
    layout.setStyle("-fx-background-color: #E9F4E9;");

    Label titleLabel = new Label("Reviews for " + restaurant.getName());
    titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

    Button addReviewButton = new Button("Add Review");
    addReviewButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white;");
    addReviewButton.setOnAction(e -> showAddReviewDialog(primaryStage, restaurant));

    ScrollPane scrollPane = new ScrollPane();
    VBox reviewsLayout = new VBox(10);

    List<Review> restaurantReviews = getReviewsForRestaurant(restaurant.getName());
    if (restaurantReviews.isEmpty()) {
        reviewsLayout.getChildren().add(new Label("No reviews yet."));
    } else {
        for (Review review : restaurantReviews) {
            VBox reviewBox = new VBox(10);
            reviewBox.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-radius: 5;");

            Label userLabel = new Label("User:   " + review.getUsername());
            Label ratingLabel = new Label("Rating: " + "★".repeat(review.getRating()) + "☆".repeat(5 - review.getRating()));
            Label commentLabel = new Label(review.getComment());
            commentLabel.setWrapText(true);

            VBox repliesBox = new VBox(5);
            for (Reply reply : review.getReplies()) {
                Label replyLabel = new Label(reply.getRestaurantName() + ": " + reply.getMessage());
                repliesBox.getChildren().add(replyLabel);
            }
            reviewBox.getChildren().addAll(userLabel, ratingLabel, commentLabel, repliesBox);
            reviewsLayout.getChildren().add(reviewBox);
        }
    }

    scrollPane.setContent(reviewsLayout);
    scrollPane.setFitToWidth(true);
    Button backButton = new Button("Back");
    backButton.setOnAction(e -> showRestaurantDetails(primaryStage, restaurant.getName()));
    layout.getChildren().addAll(titleLabel, addReviewButton, scrollPane, backButton);
    Scene scene = new Scene(layout, 1000, 700);
    primaryStage.setScene(scene);
}

   private void showAddReviewDialog(Stage primaryStage, Restaurant restaurant) {
    Stage dialog = new Stage();
    dialog.setTitle("Add Review");

    VBox layout = new VBox(20);
    layout.setPadding(new Insets(20));
    layout.setStyle("-fx-background-color: #E9F4E9");

    Label ratingLabel = new Label("Rating:");
    ComboBox<Integer> ratingComboBox = new ComboBox<>();
    ratingComboBox.getItems().addAll(1, 2, 3, 4, 5);
    ratingComboBox.setValue(5);

    TextArea commentArea = new TextArea();
    commentArea.setPromptText("Your review (max 150 words)");
    commentArea.setWrapText(true);
    commentArea.setPrefRowCount(5);

    Button submitButton = new Button("Submit Review");
    submitButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white;");
    submitButton.setOnAction(e -> {
        String comment = commentArea.getText();
        if (comment.isEmpty()) {
            showAlert("Please enter a review!");
            return;
        }

        if (comment.split("\\s+").length > 150) {
            showAlert("Review must be 150 words or less!");
            return;
        }

        Review review = new Review(
            currentUser .getUsername(), 
            restaurant.getName(),
            ratingComboBox.getValue(),
            comment
        );

        reviews.add(review);
        saveReviewsToFile(); 
        showAlert("Review submitted successfully!");
        dialog.close();
        showRestaurantReviews(primaryStage, restaurant); 
    });

    Button cancelButton = new Button("Cancel");
    cancelButton.setOnAction(e -> dialog.close());

    layout.getChildren().addAll(ratingLabel, ratingComboBox, commentArea, submitButton, cancelButton);

    Scene scene = new Scene(layout, 400, 400);
    dialog.setScene(scene);
    dialog.show();
}

    private void showUserOrders(Stage primaryStage) {
    VBox layout = new VBox(20);
    layout.setPadding(new Insets(20));
    layout.setStyle("-fx-background-color: #E9F4E9;");

    Label titleLabel = new Label("My Orders");
    titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

    ListView<Order> ordersListView = new ListView<>();
    List<Order> userOrders = getOrdersForUser (currentUser .getUsername());
    
    if (userOrders.isEmpty()) {
        ordersListView.getItems().add(new Order("No orders found", "", "", 0, "", "", "", ""));
    } else {
        ordersListView.getItems().addAll(userOrders);
    }

    ordersListView.setCellFactory(param -> new ListCell<Order>() {
        @Override
        protected void updateItem(Order order, boolean empty) {
            super.updateItem(order, empty);
            if (empty || order == null || order.getRestaurantName().isEmpty()) {
                setText(null);
            } else {
                setText(String.format(
                    "Restaurant: %s\nItem: %s\nQuantity: %d\nPrice: %s\nStatus: %s\nCancellation Requested: %s",
                    order.getRestaurantName(),
                    order.getItemName(),
                    order.getQuantity(),
                    order.getPrice(),
                    order.getStatus(),
                    order.isCancellationRequested() ? "Yes" : "No"
                ));
            }
        }
    });

    Button cancelOrderButton = new Button("Request Cancellation");
    cancelOrderButton.setOnAction(e -> {
        Order selected = ordersListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (!selected.isCancellationRequested()) {
                selected.requestCancellation("Cancellation requested by user.");
                saveOrdersToFile(); // Save the updated order
                showAlert("Cancellation request sent to the restaurant owner.");
            } else {
                showAlert("Cancellation already requested for this order.");
            }
        } else {
            showAlert("Please select an order to cancel.");
        }
    });

    Button backButton = new Button("Back");
    backButton.setOnAction(e -> showUserWelcomeScene(primaryStage));

    layout.getChildren().addAll(titleLabel, ordersListView, cancelOrderButton, backButton);
    Scene scene = new Scene(layout, 1000, 700);
    primaryStage.setScene(scene);
}

     private void showOwnerLoginScene(Stage primaryStage) {
    Image backgroundImage = new Image("file:///C:/Users/USER/Downloads/foodmenu.jpg");
    ImageView backgroundImageView = new ImageView(backgroundImage);
    backgroundImageView.setFitWidth(1000);
    backgroundImageView.setFitHeight(700);
    backgroundImageView.setPreserveRatio(false);

    StackPane stackPane = new StackPane();
    stackPane.getChildren().add(backgroundImageView);

    VBox layout = new VBox(20);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(50));
    //layout.setStyle("-fx-background-color: #E9F4E9;");

    Label loginLabel = new Label("Owner Login");
    loginLabel.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF");

    TextField usernameField = new TextField();
    usernameField.setPromptText("Username");
    PasswordField passwordField = new PasswordField();
    passwordField.setPromptText("Password");
    TextField restaurantCodeField = new TextField();
    restaurantCodeField.setPromptText("Restaurant Code");

    Button loginButton = new Button("Login");
    loginButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white; -fx-font-size: 16px;");
    loginButton.setPrefWidth(150);
     loginButton.setOnAction(e -> {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String restaurantCode = restaurantCodeField.getText();

        if (validateOwnerWithCode(username, password, restaurantCode)) {
            currentOwner = getOwnerByUsername(username);
            showOwnerDashboard(primaryStage);
        } else {
            showAlert("Invalid username, password, or restaurant code!");
        }
    });

    Button registerButton = new Button("Register");
    registerButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white; -fx-font-size: 16px;");
    registerButton.setPrefWidth(150);
    registerButton.setOnAction(e -> showOwnerRegistrationScene(primaryStage));

    HBox backButtonLayout = new HBox(30);
    backButtonLayout.setAlignment(Pos.BOTTOM_RIGHT); 
    Button backButton = new Button("Back");
    backButton.setOnAction(e -> showRoleSelectionScene(primaryStage));
    backButtonLayout.getChildren().add(backButton);

    layout.getChildren().addAll(loginLabel, usernameField, passwordField, restaurantCodeField, loginButton, registerButton);
    
    VBox mainLayout = new VBox();
    mainLayout.getChildren().addAll(layout, backButtonLayout);
    VBox.setVgrow(layout, Priority.ALWAYS); 
    
    stackPane.getChildren().add(mainLayout);

    Scene scene = new Scene(stackPane, 1000, 700);
    primaryStage.setScene(scene);
}

    private void showOwnerRegistrationScene(Stage primaryStage) {
    Image backgroundImage = new Image("file:///C:/Users/USER/Downloads/foodmenu.jpg");
    ImageView backgroundImageView = new ImageView(backgroundImage);
    backgroundImageView.setFitWidth(1000);
    backgroundImageView.setFitHeight(700);
    backgroundImageView.setPreserveRatio(false);

    StackPane stackPane = new StackPane();
    stackPane.getChildren().add(backgroundImageView);
    
    VBox layout = new VBox(20);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(50));

    Label registerLabel = new Label("Owner Registration");
    registerLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;-fx-text-fill: #FFFFFF");

    TextField usernameField = new TextField();
    usernameField.setPromptText("Username");
    PasswordField passwordField = new PasswordField();
    passwordField.setPromptText("Password");
    PasswordField confirmPasswordField = new PasswordField();
    confirmPasswordField.setPromptText("Confirm Password");
    TextField restaurantField = new TextField();
    restaurantField.setPromptText("Restaurant Name");
    TextField codeField = new TextField(); 
    codeField.setPromptText("Restaurant Code");

    Button registerButton = new Button("Register");
    registerButton.setPrefWidth(150);
    registerButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white; -fx-font-size: 16px;");
    registerButton.setOnAction(e -> {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String restaurantName = restaurantField.getText();
        String restaurantCode = codeField.getText();

        if (username.isEmpty() || password.isEmpty() || restaurantName.isEmpty() || restaurantCode.isEmpty()) {
            showAlert("All fields must be filled!");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            showAlert("Passwords do not match!");
            return;
        }
        
        if (isOwnerUsernameExists(username)) {
            showAlert("Username already exists!");
            return;
        }
        
        Optional<Owner> existingOwnerForRestaurant = owners.stream()
            .filter(owner -> owner.getRestaurantName().equals(restaurantName))
            .findFirst();
            
        if (existingOwnerForRestaurant.isPresent()) {
            if (!existingOwnerForRestaurant.get().getRestaurantCode().equals(restaurantCode)) {
                showAlert("Incorrect restaurant code! This restaurant already exists with a different code.");
                return;
            }
        } else {
            boolean codeAlreadyUsed = owners.stream()
                .anyMatch(owner -> owner.getRestaurantCode().equals(restaurantCode));
                
            if (codeAlreadyUsed) {
                showAlert("This restaurant code is already in use by another restaurant!");
                return;
            }
        }
        
        owners.add(new Owner(username, password, restaurantName, restaurantCode));
        saveOwnersToFile();
        showAlert("Registration successful!");
        showOwnerLoginScene(primaryStage);
    });

    Button backButton = new Button("Back to Login");
    backButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white; -fx-font-size: 16px;");
    backButton.setPrefWidth(150);
    backButton.setOnAction(e -> showOwnerLoginScene(primaryStage));

    layout.getChildren().addAll(registerLabel, usernameField, passwordField, confirmPasswordField, restaurantField, codeField, registerButton, backButton);
    stackPane.getChildren().add(layout);
       
    Scene scene = new Scene(stackPane, 1000, 700);
    primaryStage.setScene(scene);
}

    private void showOwnerDashboard(Stage primaryStage) {
    VBox layout = new VBox(20);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(50));
    layout.setStyle("-fx-background-color: #E9F4E9");

    Label welcomeLabel = new Label("Welcome, Owner " + currentOwner.getUsername() + " of " + currentOwner.getRestaurantName() + "!");
    welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

    Button manageMenuButton = new Button("Manage Menu");
    manageMenuButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white; -fx-font-size: 16px;");
    manageMenuButton.setOnAction(e -> {
        Restaurant restaurant = getRestaurantByName(currentOwner.getRestaurantName());
        if (restaurant != null) {
            showManageMenu(primaryStage, restaurant);
        } else {
            showAlert("Restaurant not found!");
        }
    });

    Button viewOrdersButton = new Button("View Orders");
    viewOrdersButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white; -fx-font-size: 16px;");
    viewOrdersButton.setOnAction(e -> showOwnerOrders(primaryStage));

    Button viewReviewsButton = new Button("View Reviews");
    viewReviewsButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white; -fx-font-size: 16px;");
    viewReviewsButton.setOnAction(e -> showOwnerReviews(primaryStage));

    Button logoutButton = new Button("Logout");
    logoutButton.setOnAction(e -> {
        currentOwner = null;
        showRoleSelectionScene(primaryStage);
    });

    layout.getChildren().addAll(welcomeLabel, manageMenuButton, viewOrdersButton, viewReviewsButton, logoutButton);
    
    Scene scene = new Scene(layout, 1000, 700);
    primaryStage.setScene(scene);
}

    private void showManageRestaurants(Stage primaryStage) {
    VBox layout = new VBox(20);
    layout.setPadding(new Insets(20));
    layout.setStyle("-fx-background-color: #E9F4E9;");

    Label titleLabel = new Label("Manage Restaurants");
    titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

    ListView<Restaurant> restaurantsListView = new ListView<>();
    
    restaurantsListView.getItems().addAll(restaurants.stream()
        .filter(restaurant -> restaurant.getName().equals(currentOwner.getRestaurantName()))
        .toList());

    restaurantsListView.setCellFactory(param -> new ListCell<Restaurant>() {
        @Override
        protected void updateItem(Restaurant restaurant, boolean empty) {
            super.updateItem(restaurant, empty);
            if (empty || restaurant == null) {
                setText(null);
            } else {
                setText(restaurant.getName() + " - " + restaurant.getLocation());
            }
        }
    });

    Button manageMenuButton = new Button("Manage Menu");
    manageMenuButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white;");
    manageMenuButton.setOnAction(e -> {
        Restaurant selected = restaurantsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showManageMenu(primaryStage, selected);
        } else {
            showAlert("Please select a restaurant to manage its menu!");
        }
    });

    Button backButton = new Button("Back");
    backButton.setOnAction(e -> showOwnerDashboard(primaryStage));

    layout.getChildren().addAll(titleLabel, restaurantsListView, manageMenuButton, backButton);

    Scene scene = new Scene(layout, 1000, 700);
    primaryStage.setScene(scene);
}
    
   private void showManageMenu(Stage primaryStage, Restaurant restaurant) {
    VBox layout = new VBox(20);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(50));
    layout.setStyle("-fx-background-color: #E9F4E9");

    Label restaurantLabel = new Label("Managing Menu for " + restaurant.getName());
    restaurantLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

    ListView<FoodItem> menuListView = new ListView<>();
    menuListView.getItems().addAll(restaurant.getMenuItems());

    menuListView.setCellFactory(param -> new ListCell<FoodItem>() {
        private final HBox hBox = new HBox(10);
        private final ImageView imageView = new ImageView();
        private final Label nameLabel = new Label();
        private final Label priceLabel = new Label();

        @Override
        protected void updateItem(FoodItem item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                imageView.setImage(new Image(item.getImagePath()));
                imageView.setFitWidth(100);
                imageView.setPreserveRatio(true);
                nameLabel.setText(item.getName());
                priceLabel.setText(item.getPrice());
                hBox.getChildren().setAll(imageView, nameLabel, priceLabel);
                setGraphic(hBox);
            }
        }
    });

    Button addButton = new Button("Add Item");
    addButton.setOnAction(e -> {
        FoodItem newItem = showAddFoodItemDialog();
        if (newItem != null) {
            restaurant.addMenuItem(newItem);
            menuListView.getItems().add(newItem);
            saveRestaurantsToFile();
        }
    });

    Button deleteButton = new Button("Delete Selected");
    deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
    deleteButton.setOnAction(e -> {
        FoodItem selectedItem = menuListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            restaurant.removeMenuItem(selectedItem);
            menuListView.getItems().remove(selectedItem);
            saveRestaurantsToFile();
        } else {
            showAlert("Please select an item to delete.");
        }
    });

      Button editButton = new Button("Edit Selected");
    editButton.setOnAction(e -> {
        FoodItem selectedItem = menuListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            FoodItem updatedItem = showModifyFoodItemDialog(selectedItem);
            if (updatedItem != null) {
                restaurant.updateMenuItem(selectedItem, updatedItem);
                menuListView.refresh(); 
                saveRestaurantsToFile(); 
            }
        } else {
            showAlert("Please select an item to modify.");
        }
    });
    HBox buttonLayout = new HBox(10, addButton, editButton, deleteButton);
    buttonLayout.setAlignment(Pos.CENTER); 
    buttonLayout.setPadding(new Insets(20)); 

    Button backButton = new Button("Back to Dashboard");
    backButton.setOnAction(e -> showOwnerDashboard(primaryStage));

    layout.getChildren().addAll(restaurantLabel, menuListView, buttonLayout, backButton);
    
    Scene scene = new Scene(layout, 1000, 700);
    primaryStage.setScene(scene);
}
   
   private FoodItem showAddFoodItemDialog() {
    Dialog<FoodItem> dialog = new Dialog<>();
    dialog.setTitle("Add Food Item");
    dialog.setHeaderText("Enter food item details:");

    ButtonType okButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
    TextField nameField = new TextField();
    nameField.setPromptText("Name");

    TextField priceField = new TextField();
    priceField.setPromptText("Price (e.g., 12 BDT)");

    TextArea descriptionArea = new TextArea();
    descriptionArea.setPromptText("Description");
    descriptionArea.setWrapText(true);
    descriptionArea.setPrefRowCount(3);

    TextField imageField = new TextField();
    imageField.setPromptText("Image Path");
    Button browseButton = new Button("Browse");
    browseButton.setOnAction(e -> {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Food Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(dialog.getOwner());
        if (selectedFile != null) {
            imageField.setText(selectedFile.toURI().toString());
        }
    });

    VBox content = new VBox(10);
    content.setPadding(new Insets(20));
    content.getChildren().addAll(
        new Label("Name:"), nameField,
        new Label("Price:"), priceField,
        new Label("Description:"), descriptionArea,
        new Label("Image Path:"), imageField, browseButton
    );

    dialog.getDialogPane().setContent(content);

    Node addButton = dialog.getDialogPane().lookupButton(okButtonType);
    addButton.setDisable(true);

    nameField.textProperty().addListener((observable, oldValue, newValue) -> {
        addButton.setDisable(!isValidName(newValue) || !isValidPrice(priceField.getText()));
    });

    priceField.textProperty().addListener((observable, oldValue, newValue) -> {
        addButton.setDisable(!isValidName(nameField.getText()) || !isValidPrice(newValue));
    });

    dialog.setResultConverter(dialogButton -> {
        if (dialogButton == okButtonType) {
            String name = nameField.getText().trim();
            String priceText = priceField.getText().trim();
            String price;

            priceText = priceText.replaceAll("[^\\d.]", ""); 
            try {
                double parsedPrice = Double.parseDouble(priceText);
                price = String.format("%.0f BDT", parsedPrice); 
            } catch (NumberFormatException e) {
                showAlert("Invalid price format! Please enter a numeric value.");
                return null;
            }
            String description = descriptionArea.getText().trim();
            String imagePath = imageField.getText().trim();
            return new FoodItem(name, price, description, imagePath); 
        }
        return null;
    });

    return dialog.showAndWait().orElse(null);
}

  private FoodItem showModifyFoodItemDialog(FoodItem item) {
    if (item == null) return null;
    Dialog<FoodItem> dialog = new Dialog<>();
    dialog.setTitle("Modify Food Item");
    dialog.setHeaderText("Modify details for: " + item.getName());

    ButtonType okButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

    TextField nameField = new TextField(item.getName());
    TextField priceField = new TextField(item.getPrice()); 
    TextArea descriptionArea = new TextArea(item.getDescription());
    descriptionArea.setWrapText(true);
    descriptionArea.setPrefRowCount(3);
    
    TextField imageField = new TextField(item.getImagePath());
    Button browseButton = new Button("Browse");
    browseButton.setOnAction(e -> {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Food Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(dialog.getOwner());
        if (selectedFile != null) {
            imageField.setText(selectedFile.toURI().toString());
        }
    });

    VBox content = new VBox(10);
    content.setPadding(new Insets(20));
    content.getChildren().addAll(
        new Label("Name:"), nameField,
        new Label("Price:"), priceField,
        new Label("Description:"), descriptionArea,
        new Label("Image Path:"), imageField, browseButton
    );

    dialog.getDialogPane().setContent(content);
    Node saveButton = dialog.getDialogPane().lookupButton(okButtonType);
    saveButton.setDisable(true);

    nameField.textProperty().addListener((observable, oldValue, newValue) -> {
        saveButton.setDisable(!isValidName(newValue) || !isValidPrice(priceField.getText()));
    });

    priceField.textProperty().addListener((observable, oldValue, newValue) -> {
        saveButton.setDisable(!isValidName(nameField.getText()) || !isValidPrice(newValue));
    });

    dialog.setResultConverter(dialogButton -> {
        if (dialogButton == okButtonType) {
            String name = nameField.getText().trim();
            String priceText = priceField.getText().trim();
            double price;
            priceText = priceText.replaceAll("[^\\d.]", ""); 
            try {
                price = Double.parseDouble(priceText);
            } catch (NumberFormatException e) {
                showAlert("Invalid price format! Please enter a numeric value.");
                return null;
            }

            String description = descriptionArea.getText().trim();
            String imagePath = imageField.getText().trim();
            item.setName(name);
            item.setPrice(formatPriceWithoutTrailingZeros(price) + " BDT");
            item.setDescription(description);
            item.setImagePath(imagePath);
            
            saveRestaurantsToFile(); 
            showAlert("Menu item updated successfully!");
            return item; 
        }
        return null;
    });

    return dialog.showAndWait().orElse(null);
}

    private void showEditMenuItemDialog(Stage primaryStage, Restaurant restaurant, FoodItem item) {
    Stage dialog = new Stage();
    dialog.setTitle("Edit Menu Item");

    VBox layout = new VBox(20);
    layout.setPadding(new Insets(20));

    TextField nameField = new TextField(item.getName());
    TextField priceField = new TextField(item.getPrice()); // Allow "120 BDT"
    TextArea descriptionArea = new TextArea(item.getDescription());
    descriptionArea.setWrapText(true);
    descriptionArea.setPrefRowCount(3);

    TextField imageField = new TextField(item.getImagePath());
    Button browseButton = new Button("Browse");
    browseButton.setOnAction(e -> {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Food Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(dialog);
        if (selectedFile != null) {
            imageField.setText(selectedFile.toURI().toString());
        }
    });

    Button saveButton = new Button("Save");
    saveButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white;");
    saveButton.setOnAction(e -> {
        if (nameField.getText().isEmpty() || priceField.getText().isEmpty()) {
            showAlert("Please fill in all required fields!");
            return;
        }

        String priceText = priceField.getText().trim();
        priceText = priceText.replaceAll("[^\\d.]", ""); 
        double price;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException ex) {
            showAlert("Invalid price format! Please enter a numeric value.");
            return;
        }

        item.setName(nameField.getText());
        item.setPrice(formatPriceWithoutTrailingZeros(price) + " BDT");  // Store the price with "BDT"
        item.setDescription(descriptionArea.getText());
        item.setImagePath(imageField.getText());

        saveRestaurantsToFile();
        showAlert("Menu item updated successfully!");
        dialog.close();

        showManageMenu(primaryStage, restaurant);
    });

    Button cancelButton = new Button("Cancel");
    cancelButton.setOnAction(e -> dialog.close());

    HBox imageBox = new HBox(10, imageField, browseButton);
    
    layout.getChildren().addAll(nameField, priceField, descriptionArea, imageBox, saveButton, cancelButton);

    Scene scene = new Scene(layout, 1000, 700);
    dialog.setScene(scene);
    dialog.show();
}
    private void showOwnerOrders(Stage primaryStage) {
    VBox layout = new VBox(20);
    layout.setPadding(new Insets(20));
    layout.setStyle("-fx-background-color: #E9F4E9;");

    Label titleLabel = new Label("Orders for " + currentOwner.getRestaurantName());
    titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

    ListView<Order> ordersListView = new ListView<>();
    
    List<Order> restaurantOrders = getOrdersForOwnerRestaurants().stream()
        .filter(order -> order.getRestaurantName().equals(currentOwner.getRestaurantName()))
        .toList();

    if (restaurantOrders.isEmpty()) {
        ordersListView.getItems().add(new Order("No orders found", "", "", 0, "", "", "", ""));
    } else {
        ordersListView.getItems().addAll(restaurantOrders);
    }

    ordersListView.setCellFactory(param -> new ListCell<Order>() {
        @Override
        protected void updateItem(Order order, boolean empty) {
            super.updateItem(order, empty);
            if (empty || order == null || order.getRestaurantName().isEmpty()) {
                setText(null);
            } else {
                String cancelRequestText = order.isCancellationRequested() ? "Yes" : "No";
                setText(String.format(
                    "Restaurant: %s\nItem: %s\nQuantity: %d\nPrice: %s\nCustomer: %s\nStatus: %s\nCancellation Requested: %s",
                    order.getRestaurantName(),
                    order.getItemName(),
                    order.getQuantity(),
                    order.getPrice(),
                    order.getUsername(),
                    order.getStatus(),
                    cancelRequestText

                ));
            }
        }
    });

    Button updateStatusButton = new Button("Update Status");
    updateStatusButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white;");
    updateStatusButton.setOnAction(e -> {
        Order selected = ordersListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showUpdateOrderStatusDialog(primaryStage, selected);
        } else {
            showAlert("Please select an order to update!");
        }
    }); Button manageCancelButton = new Button("Manage Cancellation Requests");
    manageCancelButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white;");
    manageCancelButton.setOnAction(e -> {
        Order selected = ordersListView.getSelectionModel().getSelectedItem();
        if (selected != null && selected.isCancellationRequested()) {
            showCancellationRequestDialog(primaryStage, selected);
        } else {
            showAlert("Please select an order with a cancellation request.");
        }
    });
    Button backButton = new Button("Back");
    backButton.setOnAction(e -> showOwnerDashboard(primaryStage));
    HBox buttonBox = new HBox(10, updateStatusButton, manageCancelButton);
    buttonBox.setAlignment(Pos.CENTER);
    layout.getChildren().addAll(titleLabel, ordersListView, buttonBox, backButton);
    Scene scene = new Scene(layout, 1000, 700);
    primaryStage.setScene(scene);
}
private void showCancellationRequestDialog(Stage primaryStage, Order order) {
    System.out.println("Showing cancellation request for order: " + order.getItemName());
    Stage dialog = new Stage();
    dialog.setTitle("Cancellation Request");
    VBox layout = new VBox(20);
    layout.setPadding(new Insets(20));
    layout.setStyle("-fx-background-color: #E9F4E9;");
    Label infoLabel = new Label(String.format(
        "User '%s' requested to cancel order:\n%s - %s (Qty: %d)",
        order.getUsername(),
        order.getRestaurantName(),
        order.getItemName(),
        order.getQuantity()
    ));
    infoLabel.setWrapText(true);
    Label messageLabel = new Label("Request Message:");
    TextArea messageArea = new TextArea(order.getCancellationMessage());
    messageArea.setEditable(false);
    messageArea.setWrapText(true);
    Button acceptButton = new Button("Accept Cancellation");
    acceptButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white;");
    acceptButton.setOnAction(e -> {
    order.setStatus("Cancelled");
    order.setCancellationMessage("Cancellation accepted by owner.");
    order.cancellationRequested = false; 
    saveOrdersToFile();
    showAlert("Cancellation accepted. Order marked as cancelled.");
    dialog.close();
    showOwnerOrders(primaryStage);
});
    Button declineButton = new Button("Decline Cancellation");
    declineButton.setStyle("-fx-background-color: #a83232; -fx-text-fill: white;");
    declineButton.setOnAction(e -> {
        order.setCancellationMessage("Cancellation declined by owner.");
        order.cancellationRequested = false; 
        saveOrdersToFile();
        showAlert("Cancellation declined.");
        dialog.close();
        showOwnerOrders(primaryStage);
    });
Button closeButton = new Button("Close");
    closeButton.setOnAction(e -> dialog.close());
    HBox buttons = new HBox(10, acceptButton, declineButton, closeButton);
    buttons.setAlignment(Pos.CENTER);
    layout.getChildren().addAll(infoLabel, messageLabel, messageArea, buttons);
    Scene scene = new Scene(layout, 500, 300);
    dialog.setScene(scene);
    dialog.show();
}
    private void showUpdateOrderStatusDialog(Stage primaryStage, Order order) {
        Stage dialog = new Stage();
        dialog.setTitle("Update Order Status");

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));

        Label orderLabel = new Label(String.format(
            "Order: %s - %s (Qty: %d)", 
            order.getRestaurantName(), 
            order.getItemName(), 
            order.getQuantity()
        ));

        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("Pending", "Preparing", "On the way", "Delivered", "Cancelled");
        statusComboBox.setValue(order.getStatus());

        Button updateButton = new Button("Update");
        updateButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white;");
        updateButton.setOnAction(e -> {
            order.setStatus(statusComboBox.getValue());
            saveOrdersToFile();
            showAlert("Order status updated successfully!");
            dialog.close();
            showOwnerOrders(primaryStage);
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> dialog.close());

        layout.getChildren().addAll(orderLabel, statusComboBox, updateButton, cancelButton);
        Scene scene = new Scene(layout, 700, 500);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showOwnerReviews(Stage primaryStage) {
    VBox layout = new VBox(20);
    layout.setPadding(new Insets(20));
    layout.setStyle("-fx-background-color: #E9F4E9;");
    
    Label titleLabel = new Label("Reviews for " + currentOwner.getRestaurantName());
    titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    
    ScrollPane scrollPane = new ScrollPane();
    VBox reviewsLayout = new VBox(10);

    List<Review> ownerReviews = getReviewsForOwnerRestaurants().stream()
        .filter(review -> review.getRestaurantName().equals(currentOwner.getRestaurantName()))
        .toList();

    if (ownerReviews.isEmpty()) {
        reviewsLayout.getChildren().add(new Label("No reviews yet."));
    } else {
        for (Review review : ownerReviews) {
            VBox reviewBox = new VBox(10);
            reviewBox.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-radius: 5;");

            Label restaurantLabel = new Label("Restaurant: " + review.getRestaurantName());
            Label userLabel = new Label(":User  " + review.getUsername());
            Label ratingLabel = new Label("Rating: " + "★".repeat(review.getRating()) + "☆".repeat(5 - review.getRating()));
            Label commentLabel = new Label(review.getComment());
            commentLabel.setWrapText(true);

            VBox repliesBox = new VBox(5);
            for (Reply reply : review.getReplies()) {
                Label replyLabel = new Label(reply.getRestaurantName() + ": " + reply.getMessage());
                repliesBox.getChildren().add(replyLabel);
            }

            Button replyButton = new Button("Reply");
            replyButton.setOnAction(e -> showReplyDialog(primaryStage, review));

            reviewBox.getChildren().addAll(restaurantLabel, userLabel, ratingLabel, commentLabel, repliesBox, replyButton);
            reviewsLayout.getChildren().add(reviewBox);
        }
    }

    scrollPane.setContent(reviewsLayout);
    scrollPane.setFitToWidth(true);

    Button backButton = new Button("Back");
    backButton.setOnAction(e -> showOwnerDashboard(primaryStage));

    layout.getChildren().addAll(titleLabel, scrollPane, backButton);

    Scene scene = new Scene(layout, 1000, 700);
    primaryStage.setScene(scene);
}

    private void showReplyDialog(Stage primaryStage, Review review) {
    Stage dialog = new Stage();
    dialog.setTitle("Reply to Review");

    VBox layout = new VBox(20);
    layout.setPadding(new Insets(20));

    TextArea replyArea = new TextArea();
    replyArea.setPromptText("Your reply...");
    replyArea.setWrapText(true);
    replyArea.setPrefRowCount(3);

    Button submitButton = new Button("Submit Reply");
    submitButton.setStyle("-fx-background-color: #3F5A43; -fx-text-fill: white;");
    submitButton.setOnAction(e -> {
        String replyMessage = replyArea.getText();
        if (replyMessage.isEmpty()) {
            showAlert("Please enter a reply!");
            return;
        }

        Reply reply = new Reply(currentOwner.getRestaurantName(), replyMessage);
        review.addReply(reply);
        saveReviewsToFile(); 
        showAlert("Reply submitted successfully!");
        dialog.close();
        showOwnerReviews(primaryStage); 
    });

    Button cancelButton = new Button("Cancel");
    cancelButton.setOnAction(e -> dialog.close());

    layout.getChildren().addAll(replyArea, submitButton, cancelButton);

    Scene scene = new Scene(layout, 400, 300);
    dialog.setScene(scene);
    dialog.initOwner(primaryStage);
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.show();
}


    private boolean validateUser (String username, String password) {
    return users.stream().anyMatch(user -> 
        user.getUsername().equals(username) && user.getPassword().equals(password));
}
  
    private boolean validateOwner(String username, String password) {
        return owners.stream().anyMatch(owner -> 
            owner.getUsername().equals(username) && owner.getPassword().equals(password));
    }

    private boolean isUsernameExists(String username) {
        return users.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    private boolean isOwnerUsernameExists(String username) {
        return owners.stream().anyMatch(owner -> owner.getUsername().equals(username));
    }

    private User getUserByUsername(String username) {
        return users.stream()
            .filter(user -> user.getUsername().equals(username))
            .findFirst()
            .orElse(null);
    }
    private User getUserByUsernameAndPhoneNumber(String username, String phoneNumber) {
    return users.stream()
        .filter(user -> user.getUsername().equals(username) && user.getPhoneNumber().equals(phoneNumber))
        .findFirst()
        .orElse(null);
}

    private Owner getOwnerByUsername(String username) {
        return owners.stream()
            .filter(owner -> owner.getUsername().equals(username))
            .findFirst()
            .orElse(null);
    }
    
    private boolean validateOwnerWithCode(String username, String password, String restaurantCode) {
    return owners.stream().anyMatch(owner -> 
        owner.getUsername().equals(username) && 
        owner.getPassword().equals(password) && 
        owner.getRestaurantCode().equals(restaurantCode));
}
    private boolean isRestaurantCodeExists(String restaurantCode) {
    return owners.stream()
        .anyMatch(owner -> owner.getRestaurantCode().equals(restaurantCode));
}

    private Restaurant getRestaurantByName(String name) {
        return restaurants.stream()
            .filter(restaurant -> restaurant.getName().equals(name))
            .findFirst()
            .orElse(null);
    }
    
      private boolean isValidName(String name) {
          return name != null && !name.trim().isEmpty();
      }

      private boolean isValidPrice(String price) {
          try {
              Double.valueOf(price.replaceAll("[^\\d.]", ""));
              return true;
          } catch (NumberFormatException e) {
              return false;
          }
      }
      private String formatPriceWithoutTrailingZeros(double price) {
    if (price == (long) price) {
        return String.format("%d", (long) price);
    } else {
        return String.format("%s", price);
    }
    }

    private List<Review> getReviewsForRestaurant(String restaurantName) {
        return reviews.stream()
            .filter(review -> review.getRestaurantName().equals(restaurantName))
            .toList();
    }

    private List<Order> getOrdersForUser(String username) {
        return orders.stream()
            .filter(order -> order.getUsername().equals(username))
            .toList();
    }

    private List<Order> getOrdersForOwnerRestaurants() {
        return orders.stream()
            .filter(order -> restaurants.stream()
                .anyMatch(restaurant -> restaurant.getName().equals(order.getRestaurantName())))
            .toList();
    }

    private List<Review> getReviewsForOwnerRestaurants() {
        return reviews.stream()
            .filter(review -> restaurants.stream()
                .anyMatch(restaurant -> restaurant.getName().equals(review.getRestaurantName())))
            .toList();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private boolean isValidPhoneNumber(String phoneNumber) {
    return phoneNumber.matches("\\d{11}");
}
    
    private boolean showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
    
    @SuppressWarnings("unchecked")
private void loadUsersFromFile() {
    try {
        if (Files.exists(Paths.get("users.dat"))) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.dat"))) {
                users = (ArrayList<User>) ois.readObject(); // Ensure this is ArrayList<User>
            }
        }
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error loading users: " + e.getMessage());
    }
}

    private void saveUsersToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat"))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    private void loadOwnersFromFile() {
        try {
            if (Files.exists(Paths.get("owners.dat"))) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("owners.dat"))) {
                    owners = (ArrayList<Owner>) ois.readObject();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading owners: " + e.getMessage());
        }
    }

    private void saveOwnersToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("owners.dat"))) {
            oos.writeObject(owners);
        } catch (IOException e) {
            System.out.println("Error saving owners: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadRestaurantsFromFile() {
    try {
        if (Files.exists(Paths.get("restaurants.dat"))) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("restaurants.dat"))) {
                restaurants = (List<Restaurant>) ois.readObject();
            }
        }
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error loading restaurants: " + e.getMessage());
    }
}

    private void saveRestaurantsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("restaurants.dat"))) {
            oos.writeObject(restaurants);
        } catch (IOException e) {
            System.out.println("Error saving restaurants: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    private void loadReviewsFromFile() {
        try {
            if (Files.exists(Paths.get("reviews.dat"))) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("reviews.dat"))) {
                    reviews = (List<Review>) ois.readObject();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading reviews: " + e.getMessage());
        }
    }

    private void saveReviewsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("reviews.dat"))) {
            oos.writeObject(reviews);
        } catch (IOException e) {
            System.out.println("Error saving reviews: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    private void loadOrdersFromFile() {
        try {
            if (Files.exists(Paths.get("orders.dat"))) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("orders.dat"))) {
                    orders = (List<Order>) ois.readObject();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading orders: " + e.getMessage());
        }
    }

    private void saveOrdersToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("orders.dat"))) {
            oos.writeObject(orders);
        } catch (IOException e) {
            System.out.println("Error saving orders: " + e.getMessage());
        }
    }

    private void initializeSampleRestaurants() {
    Restaurant restaurant1;
        restaurant1 = new Restaurant(
                "Eque",
                "file:///C:/Users/USER/Downloads/Eque.jpg", 
                 new ArrayList<>(),// Change this line
                "In 5 Star Math", "01712345678"
        );
        restaurant1.getMenuItems().add(new FoodItem("SPECIAL FRIED WONTON (8 PCS)","250 BDT","", "file:///C:/Users/USER/Downloads/fried-wontons-thumb.jpg"));
        restaurant1.getMenuItems().add(new FoodItem("FRIED CHICKEN WINGS (8 PCS)", "250 BDT","","file:///C:/Users/USER/Downloads/friedchickenwind.jpg"));
        restaurant1.getMenuItems().add(new FoodItem("SPRING ROLLS (8 PCS)", "220 BDT","","file:///C:/Users/USER/Downloads/Veg-Spring-Rolls.jpg"));
        restaurant1.getMenuItems().add(new FoodItem("FRIED RICE", "300 BDT","","file:///C:/Users/USER/Downloads/fried_rice.jpg"));
        restaurant1.getMenuItems().add(new FoodItem("NOODLES", "250 BDT","","file:///C:/Users/USER/Downloads/noodles.jpg"));
        restaurant1.getMenuItems().add(new FoodItem("SWEET AND SOUR CHICKEN", "350 BDT","","file:///C:/Users/USER/Downloads/Sweet_and_Sour_Chicken.jpg"));
        restaurant1.getMenuItems().add(new FoodItem("CHILI CHICKEN", "400 BDT","","file:///C:/Users/USER/Downloads/chilli-chicken.png"));
        restaurant1.getMenuItems().add(new FoodItem("HALF GRILLED CHICKEN", "180 BDT","","file:///C:/Users/USER/Downloads/Half-Chicken.jpg"));
        restaurant1.getMenuItems().add(new FoodItem("FULL GRILLED CHICKEN", "500 BDT","","file:///C:/Users/USER/Downloads/Half-Chicken.jpg"));
        restaurant1.getMenuItems().add(new FoodItem("FRENCH FRY", "250 BDT","","file:///C:/Users/USER/Downloads/Frech_fries.jpg"));
        restaurant1.getMenuItems().add(new FoodItem("FISH FINGER (8 PCS)", "320 BDT","","file:///C:/Users/USER/Downloads/Fish-Fingers.jpg"));
        restaurant1.getMenuItems().add(new FoodItem("SPRING CHICKEN", "300 BDT","","file:///C:/Users/USER/Downloads/spring_chicken.jpg"));
        restaurant1.getMenuItems().add(new FoodItem("CHICKEN BIRYANI", "400 BDT","","file:///C:/Users/USER/Downloads/chicken-biryani.jpg"));
        restaurant1.getMenuItems().add(new FoodItem("VEGETABLE SOUP", "150 BDT","","file:///C:/Users/USER/Downloads/Vegetable-Soup.jpg"));
        restaurant1.getMenuItems().add(new FoodItem ("MANGO STICKY RICE","180 BDT", "","file:///C:/Users/USER/Downloads/Mango-Sticky-Rice.jpg"));    
        
        Restaurant restaurant2 = new Restaurant(
            "Kazifarms kitchen", "file:///C://Users//USER//Downloads//kazifarms.png/",
            new ArrayList<>(),
            "In 6 Star Math", "01787654321"
        );
        restaurant2.getMenuItems().add(new FoodItem("BURGER", "150 BDT","","file:///C:/Users/USER/Downloads/burger.jpg"));
        restaurant2.getMenuItems().add(new FoodItem("DRUMSTICK", "90 BDT","","file:///C:/Users/USER/Downloads/Crispy-Chicken-Drumsticks.jpg")); 
        restaurant2.getMenuItems().add(new FoodItem("SPICY CHICKEN", "80 BDT","","file:///C:/Users/USER/Downloads/spicy_chicken_food.jpg")); 
        restaurant2.getMenuItems().add(new FoodItem("FRENCH FRIES","100 BDT","","file:///C:/Users/USER/Downloads/Frech_fries.jpg")); 
        restaurant2.getMenuItems().add(new FoodItem("NOODLES", "100 BDT","","file:///C:/Users/USER/Downloads/noodles.jpg")); 
        restaurant2.getMenuItems().add(new FoodItem("HOT WINGS", "100 BDT","","file:///C:/Users/USER/Downloads/friedchickenwind.jpg")); 
        restaurant2.getMenuItems().add(new FoodItem("CRISPY CHICKEN", "110 BDT","","file:///C:/Users/USER/Downloads/crispy_burger.jpg")); 
        restaurant2.getMenuItems().add(new FoodItem("NUGGETS", "90 BDT ","","file:///C:/Users/USER/Downloads/nuggets.jpg")); 
        restaurant2.getMenuItems().add(new FoodItem("COLD COFFEE", "130 BDT","","file:///C:/Users/USER/Downloads/Iced-Coffee.jpg")); 
        restaurant2.getMenuItems().add(new FoodItem("MEATBALL SAUSAGE", "30 BDT","","file:///C:/Users/USER/Downloads/meatballs.jpg"));
        restaurant2.getMenuItems().add(new FoodItem("PIZZA", "150 BDT","","file:///C:/Users/USER/Downloads/pizza.jpg"));

        Restaurant restaurant3 = new Restaurant(
            "Pizza Hutt", 
            "file:///C://Users//USER//Downloads//Pizza-Hut-Logo.png/",
            new ArrayList<>(),
            "In 7 Star Math", "01711223344");
        
        restaurant3.getMenuItems().add(new FoodItem("3 PIECE CHICKEN FRY", "240 BDT","","file:///C:/Users/USER/Downloads/crispy_burger.jpg"));
        restaurant3.getMenuItems().add(new FoodItem("CHICKEN PIZZA", "600 BDT","","file:///C:/Users/USER/Downloads/chicken-pizza-.jpg"));
        restaurant3.getMenuItems().add(new FoodItem("CHICKEN CHEESE PIZZA","", "400 BDT","file:///C:/Users/USER/Downloads/cheese-chicken-pizza.jpg"));
        restaurant3.getMenuItems().add(new FoodItem("BEFF & CHICKEN MIXED CHEESE PIZZA","","380 BDT","file:///C:/Users/USER/Downloads/Beef-Chicken-Pizza.jpg"));
        restaurant3.getMenuItems().add(new FoodItem("CHICKEN BURGER MEAL", "180 BDT","","file:///C:/Users/USER/Downloads/burger_meal.jpg"));
        restaurant3.getMenuItems().add(new FoodItem("TWISTER MEAL", "150 BDT","","file:///C:/Users/USER/Downloads/twister_meal.jpg"));
        restaurant3.getMenuItems().add(new FoodItem("VEGATABLE PIZZA", "340 BDT","","file:///C:/Users/USER/Downloads/veggie-pizza.png"));
        restaurant3.getMenuItems().add(new FoodItem("VANILLA MILKSHAKE", "140 BDT","","file:///C:/Users/USER/Downloads/vanila_shake.jpg"));
        restaurant3.getMenuItems().add(new FoodItem("STRAWBERRY MILKSHAKE", "140 BDT","","file:///C:/Users/USER/Downloads/Strawberry-milkshake.jpg"));
        restaurant3.getMenuItems().add(new FoodItem("CHOCOLATE MILKSHAKE", "140 BDT","","file:///C:/Users/USER/Downloads/chocolate_milkshake.jpg"));
        restaurant3.getMenuItems().add(new FoodItem("MANGO MILKSHAKE", "140 BDT","","file:///C:/Users/USER/Downloads/mango_shake.jpg"));
        restaurant3.getMenuItems().add(new FoodItem("OREO MILKSHAKE", "160 BDT","","file:///C:/Users/USER/Downloads/oreo-milkshake.jpg"));
        restaurant3.getMenuItems().add(new FoodItem("KITKAT MILKSHAKE", "160 BDT","","file:///C:/Users/USER/Downloads/kitkat_shake.jpg"));
        restaurant3.getMenuItems().add(new FoodItem("REGULAR HOT COFFEE", "100 BDT","","file:///C:/Users/USER/Downloads/regular_coffee.jpg"));
        restaurant3.getMenuItems().add(new FoodItem("MILK HOT COFFEE", "100 BDT","","file:///C:/Users/USER/Downloads/milk_coffee.jpg"));
        restaurant3.getMenuItems().add(new FoodItem("COLD COFFEE", "120 BDT","","file:///C:/Users/USER/Downloads/Iced-Coffee.jpg"));

        Restaurant restaurant4 = new Restaurant(
            "Waffel Street", 
            "file:///C:/Users/USER/Downloads/2024-09-20.jpg",
             new ArrayList<>(),
            "Airport Road,Saidpur5310", "01313833399");
        
                restaurant4.getMenuItems().add(new FoodItem("NUTELLA CLASSIC", "130 BDT","","file:///C:/Users/USER/Downloads/nutella-waffles.jpg"));
                restaurant4.getMenuItems().add(new FoodItem("NUTTY NUTELLA", "160 BDT","","file:///C:/Users/USER/Downloads/nut_nutella_waffles.jpg"));
                restaurant4.getMenuItems().add(new FoodItem("TIRAMISU TWIST", "160 BDT","","file:///C:/Users/USER/Downloads/tiramisu-waffles.jpg"));
                restaurant4.getMenuItems().add(new FoodItem("WHIPPY NUTELLA", "180 BDT","","file:///C:/Users/USER/Downloads/WafflesWithNutella.jpg"));
                restaurant4.getMenuItems().add(new FoodItem("CHOCOLATE OVERLOADED WITH ICE CREAM", "220 BDT","","file:///C:/Users/USER/Downloads/waffle_ice_cream.jpg"));
                restaurant4.getMenuItems().add(new FoodItem("DIM SUM STEAM (6 pcs)", "170 BDT","","file:///C:/Users/USER/Downloads/momo_chicken.jpg"));
                restaurant4.getMenuItems().add(new FoodItem("DIM SUM FRY (6 pcs)", "190 BDT","","file:///C:/Users/USER/Downloads/Momos-Fried.jpg"));
                restaurant4.getMenuItems().add(new FoodItem("MOMO MANCHURIAN (6 pcs)", "230 BDT","","file:///C:/Users/USER/Downloads/momo_manchurian.png"));
                restaurant4.getMenuItems().add(new FoodItem("WONTON IN CHILI OIL (5 pcs)", "240 BDT","","file:///C:/Users/USER/Downloads/Chili-oil-wontons.jpg"));
                restaurant4.getMenuItems().add(new FoodItem("DUMPLING SOUP (5 pcs)", "240 BDT","","file:///C:/Users/USER/Downloads/Chicken-Dumpling-Soup.jpg"));
                restaurant4.getMenuItems().add(new FoodItem("WEDGES", "90 BDT","","file:///C:/Users/USER/Downloads/Wedges.jpg"));
                restaurant4.getMenuItems().add(new FoodItem("NACHOS", "120 BDT","","file:///C:/Users/USER/Downloads/Chili_Nachos.jpg"));
                restaurant4.getMenuItems().add(new FoodItem("POP CHICKEN (12 pcs)", "180 BDT","","file:///C:/Users/USER/Downloads/pop_chicken.jpg"));
                restaurant4.getMenuItems().add(new FoodItem("THAI FRIED CHICKEN (4 pcs)", "240 BDT","","file:///C:/Users/USER/Downloads/thai-fried-chicken.jpg"));
                restaurant4.getMenuItems().add(new FoodItem("CRISPY FRIED CHICKEN (4 pcs)", "250 BDT","","file:///C:/Users/USER/Downloads/crispy_burger.jpg"));
                restaurant4.getMenuItems().add(new FoodItem("CHICKEN SUB SANDWICH", "150 BDT","","file:///C:/Users/USER/Downloads/sub_sandwich.jpg"));
                restaurant4.getMenuItems().add(new FoodItem("GRILLED CHICKEN SANDWICH (4 pcs)","", "180 BDT","file:///C:/Users/USER/Downloads/Chicken-Grilled-sandwich.jpg"));
                restaurant4.getMenuItems().add(new FoodItem("CHOCOLATE SHAKE", "80 | 120 BDT","","file:///C:/Users/USER/Downloads/chocolate_milkshake.jpg"));
                restaurant4.getMenuItems().add(new FoodItem("OREO SHAKE", "100 | 140 BDT","","file:///C:/Users/USER/Downloads/oreo-milkshake.jpg"));
                restaurant4.getMenuItems().add(new FoodItem("KITKAT SHAKE", "100 | 140 BDT","","file:///C:/Users/USER/Downloads/kitkat_shake.jpg"));
                restaurant4.getMenuItems().add(new FoodItem("STRAWBERRY SHAKE", "90 | 130 BDT","","file:///C:/Users/USER/Downloads/Strawberry-milkshake.jpg"));
                restaurant4.getMenuItems().add(new FoodItem("MANGO SHAKE", "90 | 130 BDT","","file:///C:/Users/USER/Downloads/mango_shake.jpg"));
                restaurant4.getMenuItems().add(new FoodItem("ICE-CREAM", "40 BDT","","file:///C:/Users/USER/Downloads/ice-cream.jpg"));
                
        Restaurant restaurant5;
        restaurant5 = new Restaurant(
                "Waffel N Cream",
                "file:///C://Users//USER//Downloads//waffle_n_cream.jpg/",
                new ArrayList<>(),  
                "Airport Road,Saidpur5310", "017333577752");
        
                restaurant5.getMenuItems().add(new FoodItem("NUTELLA CLASSIC", "130 BDT","","file:///C:/Users/USER/Downloads/nutella-waffles.jpg"));
                restaurant5.getMenuItems().add(new FoodItem("NUTTY NUTELLA", "160 BDT","","file:///C:/Users/USER/Downloads/nut_nutella_waffles.jpg"));
                restaurant5.getMenuItems().add(new FoodItem("TIRAMISU TWIST", "160 BDT","","file:///C:/Users/USER/Downloads/tiramisu-waffles.jpg"));
                restaurant5.getMenuItems().add(new FoodItem("WHIPPY NUTELLA", "180 BDT","","file:///C:/Users/USER/Downloads/WafflesWithNutella.jpg"));
                restaurant5.getMenuItems().add(new FoodItem("CHOCOLATE OVERLOADED WITH ICE CREAM", "220 BDT","","file:///C:/Users/USER/Downloads/waffle_ice_cream.jpg"));
                restaurant5.getMenuItems().add(new FoodItem("DIM SUM STEAM (6 pcs)", "170 BDT","","file:///C:/Users/USER/Downloads/momo_chicken.jpg"));
                restaurant5.getMenuItems().add(new FoodItem("DIM SUM FRY (6 pcs)", "190 BDT","","file:///C:/Users/USER/Downloads/Momos-Fried.jpg"));
                restaurant5.getMenuItems().add(new FoodItem("MOMO MANCHURIAN (6 pcs)", "230 BDT","","file:///C:/Users/USER/Downloads/momo_manchurian.png"));
                restaurant5.getMenuItems().add(new FoodItem("WONTON IN CHILI OIL (5 pcs)", "240 BDT","","file:///C:/Users/USER/Downloads/Chili-oil-wontons.jpg"));
                restaurant5.getMenuItems().add(new FoodItem("DUMPLING SOUP (5 pcs)", "240 BDT","","file:///C:/Users/USER/Downloads/Chicken-Dumpling-Soup.jpg"));
                restaurant5.getMenuItems().add(new FoodItem("WEDGES", "90 BDT","","file:///C:/Users/USER/Downloads/Wedges.jpg"));
                restaurant5.getMenuItems().add(new FoodItem("NACHOS", "120 BDT","","file:///C:/Users/USER/Downloads/Chili_Nachos.jpg"));
                restaurant5.getMenuItems().add(new FoodItem("POP CHICKEN (12 pcs)", "180 BDT","","file:///C:/Users/USER/Downloads/pop_chicken.jpg"));
                restaurant5.getMenuItems().add(new FoodItem("THAI FRIED CHICKEN (4 pcs)", "240 BDT","","file:///C:/Users/USER/Downloads/thai-fried-chicken.jpg"));
                restaurant5.getMenuItems().add(new FoodItem("CRISPY FRIED CHICKEN (4 pcs)", "250 BDT","","file:///C:/Users/USER/Downloads/crispy_burger.jpg"));
                restaurant5.getMenuItems().add(new FoodItem("CHICKEN SUB SANDWICH", "150 BDT","","file:///C:/Users/USER/Downloads/sub_sandwich.jpg"));
                restaurant5.getMenuItems().add(new FoodItem("GRILLED CHICKEN SANDWICH (4 pcs)","", "180 BDT","file:///C:/Users/USER/Downloads/Chicken-Grilled-sandwich.jpg"));
                restaurant5.getMenuItems().add(new FoodItem("CHOCOLATE SHAKE", "80 | 120 BDT","","file:///C:/Users/USER/Downloads/chocolate_milkshake.jpg"));
                restaurant5.getMenuItems().add(new FoodItem("OREO SHAKE", "100 | 140 BDT","","file:///C:/Users/USER/Downloads/oreo-milkshake.jpg"));
                restaurant5.getMenuItems().add(new FoodItem("KITKAT SHAKE", "100 | 140 BDT","","file:///C:/Users/USER/Downloads/kitkat_shake.jpg"));
                restaurant5.getMenuItems().add(new FoodItem("STRAWBERRY SHAKE", "90 | 130 BDT","","file:///C:/Users/USER/Downloads/Strawberry-milkshake.jpg"));
                restaurant5.getMenuItems().add(new FoodItem("MANGO SHAKE", "90 | 130 BDT","","file:///C:/Users/USER/Downloads/mango_shake.jpg"));
                restaurant5.getMenuItems().add(new FoodItem("ICE-CREAM", "40 BDT","","file:///C:/Users/USER/Downloads/ice-cream.jpg"));
              
        restaurants.add(restaurant1);
        restaurants.add(restaurant2);
        restaurants.add(restaurant3);
        restaurants.add(restaurant4);
        restaurants.add(restaurant5);
    }

    public static void main(String[] args) {
        launch(args);
    }

    
}

class User implements Serializable {
    private static final long serialVersionUID =  -6845052377021220484L; 
    private String username;
    private String password;
    private String phoneNumber;

    public User(String username, String password, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
    
    public void setPassword(String password) {
        this.password = password; 
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}


class Owner implements Serializable {
    private static final long serialVersionUID = 1L; 
    private String username;
    private String password;
    private String restaurantName;
    private String restaurantCode; 

    public Owner(String username, String password, String restaurantName, String restaurantCode) {
        this.username = username;
        this.password = password;
        this.restaurantName = restaurantName;
        this.restaurantCode = restaurantCode; 
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRestaurantCode() {
        return restaurantCode; 
    }
}


class Restaurant implements Serializable {
    private static final long serialVersionUID = 1L; 
    private String name;
    private String imagePath;
    private List<FoodItem> menuItems;  
    private String location;
    private String contactNumber;

    public Restaurant(String name, String imagePath, List<FoodItem> menuItems, String location, String contactNumber) {
        this.name = name;
        this.imagePath = imagePath;
        this.menuItems = menuItems != null ? menuItems : new ArrayList<>(); 
        this.location = location;
        this.contactNumber = contactNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public List<FoodItem> getMenuItems() { 
        return menuItems;
    }

    public void addMenuItem(FoodItem item) {
        menuItems.add(item);
    }

    public void removeMenuItem(FoodItem item) {
        menuItems.remove(item);
    }

    public void updateMenuItem(FoodItem oldItem, FoodItem newItem) {
    int index = menuItems.indexOf(oldItem);
    if (index != -1) {
        menuItems.set(index, newItem);
    }
}

}

class Reply implements Serializable {
    private static final long serialVersionUID = 1L;
    private String restaurantName;
    private String message;
    public Reply(String restaurantName, String message) {
        this.restaurantName = restaurantName;
        this.message = message;
    }
    public String getRestaurantName() {
        return restaurantName;
    }
    public String getMessage() {
        return message;
    }
}
class Review implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String restaurantName;
    private int rating;
    private String comment;
    private List<Reply> replies; 

    public Review(String username, String restaurantName, int rating, String comment) {
        this.username = username;
        this.restaurantName = restaurantName;
        this.rating = rating;
        this.comment = comment;
        this.replies = new ArrayList<>(); 
    }

    public String getUsername() {
        return username;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
    
    public List<Reply> getReplies() {
        return replies;
    }

    public void addReply(Reply reply) {
        replies.add(reply);
    }
}

class FoodItem implements Serializable {
    private static final long serialVersionUID = 1L; 
    private String name;
    private String price;
    private String description;
    private String imagePath;

    public FoodItem(String name, String price, String description, String imagePath) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", name, price); 
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    
}
}

class Order implements Serializable {
    private static final long serialVersionUID =-5228505749128343316L;
    private String username;
    private String restaurantName;
    private String itemName;
    private int quantity;
    private String price;
    private String deliveryAddress;
    private String phoneNumber;
    private String status;
    boolean cancellationRequested;
    private String cancellationMessage; 

    public Order(String username, String restaurantName, String itemName, int quantity, 
                 String price, String deliveryAddress, String phoneNumber, String status) {
        this.username = username;
        this.restaurantName = restaurantName;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.deliveryAddress = deliveryAddress;
        this.phoneNumber = phoneNumber;
        this.status = status;
          this.cancellationRequested = false; 
        this.cancellationMessage = ""; 
    }

    public String getUsername() {
        return username;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
  
    public boolean isCancellationRequested() {
        return cancellationRequested;
    }
    public void requestCancellation(String message) {
        this.cancellationRequested = true;
        this.cancellationMessage = message;
    }
    public String getCancellationMessage() {
        return cancellationMessage;
    }
    public void setCancellationMessage(String message) {
        this.cancellationMessage = message;
    }
}