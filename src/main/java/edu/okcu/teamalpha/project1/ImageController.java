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
import java.lang.reflect.InvocationTargetException;

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
        choiceTransformation.getItems().addAll("Grayscale", "Sepia", "Reflect");
    }

    /**
     * Method that takes place when the load button is clicked. Will open directory to find image to be
     * transformed. If a file that is not an image is loaded, changes label asking user to reselect image
     * @param actionEvent - actionEvent
     */
    public void onLoadImageClick(ActionEvent actionEvent) throws IOException {
        try {
            //finds image selected and loads it into display
            FileChooser fileChooser = new FileChooser();
            selectedFile = fileChooser.showOpenDialog(null);

            Image image = new Image(selectedFile.toURI().toString());

            imgPicture.setImage(image);
            imgPicture.setFitHeight(400);
            imgPicture.setFitWidth(400);

            lblWelcomeText.setText("Now select a transformation!");
        } catch (NullPointerException exception) {
            //if they close the window that opens, bug them about loading an image again.
            lblWelcomeText.setText("Please load an image!");
        }

    }


    public void onButtonApplyClicked(ActionEvent actionEvent) throws IOException {
        try {
            String transformationSelection = choiceTransformation.getValue().toString();
            BufferedImage img = ImageIO.read(selectedFile);


            switch (transformationSelection) {     //Switch to handle what choice is made from the ChoiceBox and call the respective methods.
                case "Grayscale":
                    changeGrayScale(img);
                    break;
                case "Sepia":
                    changeSepia(img);
                    break;
                case "Reflect":
                    changeReflect(img);
                    break;
            }
            displayImage(convertToFxImage(img));  //Converts BufferedImage to Image using stolen method.
        } catch (IllegalArgumentException exception) {
            //if the user tries to apply a transformation without selecting an image, reprompts them.
            lblWelcomeText.setText("Please load an image first!");
        }

    }

    private BufferedImage changeGrayScale(BufferedImage img) {
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                //todo: fix this so it doesnt turn it red lmao
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

    //This method reflects an image but only once
    private BufferedImage changeReflect(BufferedImage img) {
        for (int y = 0; y < img.getHeight(); y++) {
            int xEnd = img.getWidth()-1;
            int xStart = 0;
            while (xStart<=xEnd) {
                xEnd--;
                xStart++;
                int pixel = img.getRGB(xStart, y);
                int endPixel = img.getRGB(xEnd, y);
                Color color = new Color(pixel);
                Color color2 = new Color(endPixel);
                img.setRGB(xEnd, y, color.getRGB());
                img.setRGB(xStart, y, color2.getRGB());
                }
            }
            return img;
        }

        //This method mirrors an image
    private BufferedImage changeMirror(BufferedImage img) {
        for (int y = 0; y < img.getHeight(); y++) {
            int xEnd = img.getWidth()-1;
            int xStart = 0;
            while (xStart<=xEnd) {
                xEnd--;
                xStart++;
                int pixel = img.getRGB(xStart, y);
                Color color = new Color(pixel);
                img.setRGB(xEnd, y, color.getRGB());
            }
        }
        return img;
    }

    private BufferedImage changeSepia(BufferedImage img) {
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                //todo: fill in this to do what is intended

            }
        }

        return img;
    }

    public void displayImage(Image image) {
        imgPicture.setImage(image);
    }

    /**
     * Stolen method from StackOverflow
     * @param image - BufferedImage to be converted into Image
     * @return Image
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