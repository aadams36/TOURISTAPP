package com.example.project3150;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TouristApp extends Application {

    // Map to store the destination details
    private Map<String, Destination> destinations;

    @Override
    public void start(Stage primaryStage) {
        // Initialize the destinations
        initializeDestinations();
        readAllDataAtOnce("locfor.csv", destinations);
        /** HERE 222*/

        // Create the main layout
        BorderPane mainLayout = new BorderPane();

        // Create the list view to display the destinations
        ListView<String> destinationListView = new ListView<>();
        destinationListView.getItems().addAll(destinations.keySet());

        // Create the details area to display destination information
        FlowPane detailsFlowPane = new FlowPane();
        detailsFlowPane.setAlignment(Pos.TOP_LEFT);
        detailsFlowPane.setPadding(new Insets(10));

        // Add event listener to update details when a destination is selected
        destinationListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateDetailsView(newValue, detailsFlowPane);
            }
        });

        // Create the scroll pane for the details area
        ScrollPane detailsScrollPane = new ScrollPane(detailsFlowPane);
        detailsScrollPane.setFitToWidth(true);

        // Create the main split pane
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(destinationListView, detailsScrollPane);
        splitPane.setDividerPositions(0.2);

        // Set the main layout
        mainLayout.setCenter(splitPane);

        // Create the scene
        Scene scene = new Scene(mainLayout, 900, 700);

        // Set up the stage
        primaryStage.setTitle("Georgia Tourist App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateDetailsView(String destinationName, FlowPane detailsFlowPane) {
        // Clear the previous details
        detailsFlowPane.getChildren().clear();

        // Get the destination details
        Destination destination = destinations.get(destinationName);

        // Create the title label
        Label titleLabel = new Label(destinationName);
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Create the description label
        Label descriptionLabel = new Label(destination.getDescription());
        descriptionLabel.setWrapText(true); // Enable wrapping of text to prevent it from stretching the window

        // Create the forecast label
        Label forecastLabel = new Label("Forecast: " + destination.getForecast());
        forecastLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        // Create the temperature label
        Label temperatureLabel = new Label("Temperature: " + destination.getTemperature() + "°C");
        temperatureLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        // Add the labels to the details view
        detailsFlowPane.getChildren().addAll(titleLabel, descriptionLabel, forecastLabel, temperatureLabel);

        // Create the flow pane for the pictures
        FlowPane picturesFlowPane = new FlowPane(10, 10);
        picturesFlowPane.setAlignment(Pos.BOTTOM_LEFT);

        // Adjust the size of the flow pane to fit the content
        picturesFlowPane.setPrefWrapLength(500); // Change this value to fit your desired width

        for (String picturePath : destination.getPictures()) {
            try {
                Image image = new Image(picturePath);
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(400);
                imageView.setPreserveRatio(true);

                // Adjust the size of the image view to fit the images horizontally
                imageView.setFitWidth(400); // Change this value to fit your desired width

                picturesFlowPane.getChildren().add(imageView);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(forecastLabel, temperatureLabel, picturesFlowPane);

        // Add the VBox to the details view
        detailsFlowPane.getChildren().add(vbox);


        // Add the pictures flow pane to the details view

    }

    /** HEREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE*/

public static void readAllDataAtOnce(String file, Map<String, Destination> destinations) {
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            String destinationName = data[0];
            String temperature = data[1];
            String forecast = data[2];

            Destination destination = destinations.get(destinationName);
            if (destination != null) {
                destination.setTemperature(Double.parseDouble(temperature));
                destination.setForecast(forecast);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}






    // Add the components to the details view
    private void initializeDestinations() {
        destinations = new HashMap<>();

        // Destination 1: Savannah
        Destination savannah = new Destination("Savannah");
        savannah.setDescription("Savannah, Georgia, is a charming and historic city that offers a unique blend of Southern hospitality, captivating architecture, and a vibrant cultural scene. With its cobblestone streets, moss-draped oak trees, and stately antebellum homes, Savannah is a must-visit destination for tourists seeking a glimpse into the Old South. Here's a brief summary of the must-see attractions and why a tourist would visit Savannah.\n\n" +
                "1. Forsyth Park: One of Savannah's most iconic landmarks, Forsyth Park is a sprawling green space adorned with beautiful fountains, statues, and a fragrant garden. Visitors can relax on the lush lawns, take a leisurely stroll, or enjoy a picnic while immersing themselves in the park's serene ambiance.\n\n" +
                "2. River Street: Located along the Savannah River, River Street is a bustling waterfront district filled with shops, restaurants, and historic buildings. This vibrant area offers stunning views of the river, charming cobblestone streets, and a lively atmosphere. Visitors can indulge in delicious seafood, shop for unique souvenirs, or simply soak in the lively atmosphere of this historic district.\n\n" +
                "3. Cathedral of St. John the Baptist: This magnificent cathedral is a masterpiece of Gothic architecture and is considered one of Savannah's most beautiful landmarks. Its towering spires, intricate stained glass windows, and awe-inspiring interior make it a must-visit for architecture enthusiasts and those seeking a moment of tranquility.\n\n" +
                "4. Historic District: Savannah's Historic District is a treasure trove of history and charm. With its meticulously preserved 18th and 19th-century architecture, picturesque squares, and elegant homes, it's a delight to explore on foot. Visitors can take guided tours, visit historic museums, and admire the distinct beauty of this well-preserved district.\n\n" +
                "5. Bonaventure Cemetery: This hauntingly beautiful cemetery is located just outside of Savannah and is known for its moss-covered oak trees and stunning monuments. It has been featured in books and movies, capturing the imagination of many. Visitors can take a leisurely stroll through the cemetery and marvel at the artistry and serenity of this unique place.\n\n" +
                "6. Telfair Museums: Art enthusiasts will appreciate the Telfair Museums, which consist of three separate museums displaying a diverse collection of art and artifacts. The Telfair Academy, the Owens-Thomas House, and the Jepson Center offer visitors a chance to explore various artistic styles and historical exhibits.\n\n" +
                "7. Savannah College of Art and Design (SCAD): As a prominent art and design institution, SCAD has had a significant impact on the city's cultural scene. Visitors can wander through SCAD's various buildings and galleries, showcasing innovative and thought-provoking artwork from students and renowned artists.\n\n" +
                "Tourists are drawn to Savannah for its rich history, architectural splendor, and Southern charm. Whether it's exploring the city's historic landmarks, strolling along the picturesque River Street, or immersing oneself in the artistic and cultural offerings, Savannah offers a truly enchanting experience. The city's hospitality, captivating beauty, and a sense of nostalgia make it an unforgettable destination for all who visit.");

        savannah.addPicture("sav1.jpg");
        savannah.addPicture("sav2.jpg");
        savannah.addPicture("sav3.jpg");
        savannah.addPicture("sav4.jpg");
        savannah.addPicture("sav5.jpg");
        destinations.put("Savannah", savannah);

        Destination goldenIsles = new Destination("Golden Isles");
        goldenIsles.setDescription("The Golden Isles of Georgia is a stunning coastal region consisting of a cluster of four barrier islands: St. Simons Island, Sea Island, Jekyll Island, and Little St. Simons Island. This picturesque area attracts tourists from far and wide with its pristine beaches, natural beauty, rich history, and a variety of outdoor activities. Here's a brief summary of the must-see attractions and why a tourist would visit the Golden Isles.\n" +
                "\n" +
                "1. St. Simons Island: Known for its charm and natural beauty, St. Simons Island offers visitors a perfect blend of relaxation and exploration. The island is home to iconic attractions like the St. Simons Lighthouse, which provides panoramic views of the coastline, and the historic Christ Church, one of the oldest churches in Georgia. Visitors can also enjoy miles of sandy beaches, bike along scenic trails, play golf on world-class courses, or explore the island's vibrant art scene.\n" +
                "\n" +
                "2. Sea Island: Renowned for its luxurious resorts and pristine beaches, Sea Island is a haven for those seeking a tranquil retreat. The island is famous for its world-class accommodations, upscale dining options, and exceptional golfing facilities. Visitors can indulge in spa treatments, relax by the pool, enjoy water sports, or take a nature walk along the picturesque coastline.\n" +
                "\n" +
                "3. Jekyll Island: With its rich history and unspoiled natural landscapes, Jekyll Island offers a unique experience for tourists. The island's historic district features the Jekyll Island Club, a historic hotel that was once a private retreat for America's wealthiest families. Visitors can explore the island's maritime forest, lounge on secluded beaches, visit the Georgia Sea Turtle Center, or take a bike ride on the extensive network of trails.\n" +
                "\n" +
                "4. Little St. Simons Island: As a privately owned island, Little St. Simons Island provides an exclusive and pristine getaway. This secluded paradise is a nature lover's dream, offering pristine beaches, tidal creeks, and expansive marshlands. Visitors can partake in birdwatching, kayak through serene waterways, take guided nature tours, or simply relax in the tranquility of the island's unspoiled surroundings.\n" +
                "\n" +
                "5. Outdoor Activities: The Golden Isles provide abundant opportunities for outdoor enthusiasts. Fishing enthusiasts can enjoy both deep-sea and inshore fishing, while boating enthusiasts can explore the waterways and embark on scenic cruises. Nature lovers can take guided eco-tours, go birdwatching, or observe sea turtles and other wildlife in their natural habitats.\n" +
                "\n" +
                "The Golden Isles of Georgia offer a diverse range of attractions and activities that cater to a variety of interests. Whether you seek relaxation, outdoor adventures, historical exploration, or luxury accommodations, the Golden Isles provide an idyllic coastal destination that promises a memorable experience for every tourist.");
        goldenIsles.addPicture("gol1.jpg");
        goldenIsles.addPicture("gol2.jpg");
        goldenIsles.addPicture("gol3.jpg");
        goldenIsles.addPicture("gol4.jpg");
        goldenIsles.addPicture("gol5.jpg");
        destinations.put("Golden Isles", goldenIsles);

        Destination dahlonega = new Destination("Dahlonega");
        dahlonega.setDescription("Dahlonega, Georgia, is a charming town nestled in the foothills of the North Georgia Mountains. Known for its rich history, natural beauty, and vibrant arts scene, Dahlonega attracts tourists seeking a unique and memorable experience. Here's a brief summary of the must-see attractions and why a tourist would visit Dahlonega.\n" +
                "\n" +
                "1. Dahlonega Square: The heart of the town, Dahlonega Square, is a picturesque historic district lined with unique shops, art galleries, and restaurants. Visitors can explore the local boutiques, sample delicious food, and soak in the small-town ambiance. The square is also home to the Dahlonega Gold Museum, where visitors can learn about the town's gold rush history and even try their hand at panning for gold.\n" +
                "\n" +
                "2. North Georgia Wine Country: Dahlonega is the gateway to North Georgia's burgeoning wine country. The region is known for its vineyards and wineries, offering wine enthusiasts the opportunity to sample award-winning wines while enjoying breathtaking views of the vineyards and surrounding mountains. Visitors can take vineyard tours, participate in tastings, and attend wine festivals throughout the year.\n" +
                "\n" +
                "3. Amicalola Falls State Park: Located just a short drive from Dahlonega, Amicalola Falls State Park is home to the tallest waterfall in Georgia. The cascading waterfall drops over 700 feet and offers stunning views and picturesque hiking trails. Visitors can hike to the top of the falls, stay overnight in the park's lodge, or explore the park's nature trails and wildlife.\n" +
                "\n" +
                "4. Outdoor Adventures: Dahlonega is a paradise for outdoor enthusiasts. The area boasts numerous opportunities for hiking, mountain biking, horseback riding, and fishing. The nearby Chattahoochee National Forest offers miles of scenic trails and camping spots for nature lovers to explore and enjoy the great outdoors.\n" +
                "\n" +
                "5. Festivals and Events: Dahlonega hosts various festivals and events throughout the year, showcasing the town's vibrant arts and cultural scene. The Bear on the Square Mountain Festival celebrates Appalachian music and art, while the Dahlonega Arts & Wine Festival highlights local artists and wineries. Visitors can enjoy live music, arts and crafts, delicious food, and a lively atmosphere during these festive occasions.\n" +
                "\n" +
                "6. Dahlonega Gold Mines: As the site of the first major gold rush in the United States, Dahlonega is steeped in gold mining history. Tourists can visit historic gold mines and learn about the town's fascinating past. The Consolidated Gold Mine offers underground tours where visitors can explore the tunnels and see demonstrations of gold panning.\n" +
                "\n" +
                "Tourists are drawn to Dahlonega for its small-town charm, outdoor recreational opportunities, rich history, and thriving arts community. Whether it's exploring the historic square, indulging in wine tastings, hiking to scenic waterfalls, or immersing oneself in the town's cultural events, Dahlonega offers a delightful blend of natural beauty, cultural experiences, and a warm welcoming atmosphere that makes it an appealing destination for all.");
        dahlonega.addPicture("dah1.jpg");
        dahlonega.addPicture("dah2.jpg");
        dahlonega.addPicture("dah3.jpg");
        dahlonega.addPicture("dah4.jpg");
        dahlonega.addPicture("dah5.jpg");
        destinations.put("Dahlonega", dahlonega);



    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Destination {
    private String name;
    private String description;
    private List<String> pictures;

    private double temperature;
    private String forecast;

    public Destination(String name) {
        this.name = name;
        pictures = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void addPicture(String picturePath) {
        pictures.add(picturePath);
    }


    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }


}

