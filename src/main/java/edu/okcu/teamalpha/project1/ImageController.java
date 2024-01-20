package edu.okcu.teamalpha.project1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

public class ImageController {

    public Label lblWelcomeText;
    public ChoiceBox choiceTransformation;
    @FXML
    private ImageView imgPicture;
    private File selectedFile;


    public void initialize() {
        //adds choice options to choiceBox
        choiceTransformation.getItems().addAll("Grayscale", "Sepia", "Reflect");
    }

    /**
     * Method that takes place when the load button is clicked. Will open directory to find image to be
     * transformed. If a file that is not an image is loaded, changes label asking user to reselect image
     * @param actionEvent - actionEvent
     */
    public void onLoadImageClick(ActionEvent actionEvent) {
        //finds image selected and loads it into display
        FileChooser fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(null);

        Image image = new Image(selectedFile.getPath());
        imgPicture.setImage(image);
        imgPicture.setFitHeight(400);
        imgPicture.setFitWidth(400);

        //check if file loaded is not an image, then prompt user if it isn't
        if (image.isError()) {
            lblWelcomeText.setText("Please select a valid image!");
        } else {
            lblWelcomeText.setText("Now select a transformation!");
        }
    }

    public void onButtonApplyClicked(ActionEvent actionEvent) {
        //todo : make this do stuff
    }
}