package edu.okcu.teamalpha.project1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageController {

    public Label lblWelcomeText;
    public ChoiceBox choiceTransformation;
    public VBox vboxBackground;

    @FXML

    private ImageView imgPicture;
    private File selectedFile;



    public void initialize() {
        //set background color
        vboxBackground.setStyle("-fx-background-color: #E1E1E1");

        //adds choice options to choiceBox
        choiceTransformation.getItems().addAll("Grayscale", "Sepia", "Reflect", "50% Opacity");
    }

    /**
     * Method that takes place when the load button is clicked. Will open directory to find image to be
     * transformed. If a file that is not an image is loaded, changes label asking user to reselect image
     * @param actionEvent - actionEvent
     */
    public void onLoadImageClick(ActionEvent actionEvent) throws IOException {
        //finds image selected and loads it into display
        FileChooser fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(null);

        Image image = new Image(selectedFile.getPath());

        imgPicture.setImage(image);
        imgPicture.setFitHeight(400);
        imgPicture.setFitWidth(400);

    }


    private BufferedImage changeGrayScale(BufferedImage img) {
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int pixel = img.getRGB(x, y);
                Color color = new Color(pixel);

                int alpha = color.getAlpha();
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                red = red / 3;
                blue = blue /3;
                green = green/3;

                Color newPixel = new Color(alpha, red, green, blue);
                img.setRGB(x, y, newPixel.getRGB());
            }
        }
        return img;
    }

    public void onButtonApplyClicked(ActionEvent actionEvent) throws IOException {
        //todo : make this do stuff
        String transformationSelection = choiceTransformation.getValue().toString();
        BufferedImage img = ImageIO.read(selectedFile);


        //todo: add other options for other effects
        switch (transformationSelection) {
            case "Grayscale":
                changeGrayScale(img);

                break;
            case "Sepia":

                break;
        }
        displayImage(convertToFxImage(img));

    }

    public void displayImage(Image image) {
        imgPicture.setImage(image);
    }

    /**
     * Stolen method
     * @param image
     * @return
     */
    private static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }
}