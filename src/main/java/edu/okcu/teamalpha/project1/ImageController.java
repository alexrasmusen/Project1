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

    //ChoiceBox for getting the user's decision for which transformation to take place
    public ChoiceBox choiceTransformation;
    public VBox vboxBackground;
    public ImageView imgNewPicture;

    @FXML

    private ImageView imgPicture;
    private File selectedFile;



    public void initialize() {
        //set background color
        vboxBackground.setStyle("-fx-background-color: #E1E1E1");

        //adds choice options to choiceBox
        choiceTransformation.getItems().addAll("Grayscale", "Sepia", "Reflect", "Mirror", "Upside Down");
    }

    /**
     * Method that takes place when the load button is clicked. Will open directory to find image to be
     * transformed. If a file that is not an image is loaded, changes label asking user to reselect image
     * @param actionEvent - actionEvent
     */
    public void onLoadImageClick(ActionEvent actionEvent) {
        try {
            //finds image selected and loads it into display
            FileChooser fileChooser = new FileChooser();
            selectedFile = fileChooser.showOpenDialog(null);

            Image image = new Image(selectedFile.toURI().toString());
            //display the selected image
            displayImage(image, imgPicture);

            lblWelcomeText.setText("Now select a transformation!");
        } catch (NullPointerException exception) {
            //if they close the window that opens, bug them about loading an image again.
            lblWelcomeText.setText("Please load an image!");
        }

    }


    /**
     * Alright, this is a big one cuz it does way too much. First, gets the transformation selected from the ChoiceBox.
     * Converts the selected image into a BufferedImage. Then has a switch to call the appropriate method depending on the
     * transformation. Finally, calls the displayImage() method to display the newly transformed image.
     * @param actionEvent - actionEvent
     * @throws IOException - I guess it has to throw this because it is using ImageIO.read(). Let's hope it doesn't get this exception.
     */
    public void onButtonApplyClicked(ActionEvent actionEvent) throws IOException {
        try {
            String transformationSelection = choiceTransformation.getValue().toString();
            BufferedImage img = ImageIO.read(selectedFile);


            switch (transformationSelection) {     //Switch to handle what choice is made from the ChoiceBox and call the respective methods.
                case "Grayscale" -> changeGrayScale(img);
                case "Sepia" -> changeSepia(img);
                case "Reflect" -> changeReflect(img);
                case "Mirror" -> changeMirror(img);
                case "Upside Down" -> changeUpsideDown(img);
            }
            displayImage(convertToFxImage(img), imgNewPicture);  //Converts BufferedImage to Image using stolen method. Then displays it
        } catch (IllegalArgumentException exception) {
            //if the user tries to apply a transformation without selecting an image, prompts them.
            lblWelcomeText.setText("Please load an image first!");
        } catch (NullPointerException exception) {
            //if the user has an image BUT NOT a transformation selected, kill them. Just kidding tell them to select a transformation
            lblWelcomeText.setText("Please select a transformation first!");
        }

    }

    /**
     * Method for upside down transformation. Selects a pixel from the top and the bottom, then flip their colors.
     * @param img - selected image to be flipped upside down.
     */
    private void changeUpsideDown(BufferedImage img) {
            for (int x = 0; x < img.getWidth(); x++) {
                int yEnd = img.getHeight()-1;
                int yStart = 0;
                while (yStart<=yEnd) {
                    yEnd--;
                    yStart++;
                    int pixel = img.getRGB(x, yStart);
                    int endPixel = img.getRGB(x, yEnd);
                    Color color = new Color(pixel);
                    Color color2 = new Color(endPixel);
                    img.setRGB(x, yEnd, color.getRGB());
                    img.setRGB(x, yStart, color2.getRGB());
                }
            }
        }

    /**
     *  Method for taking the left side of the image and mirroring it over the right. It creates a funny effect when using pictures of faces.
     * @param img - the image to be mirrored
     */
    private void changeMirror(BufferedImage img) {
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
        }

    /**
     * Method that turns the image into grayscale
     * @param img - BufferedImage to be turned grayscale
     */
    private void changeGrayScale(BufferedImage img) {
        //loop through every pixel
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {

                int pixel = img.getRGB(x, y);
                Color color = new Color(pixel);

                //get the color values for each color
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                //do some slight maths nd stuff
                int grayScale = (red + blue + green)/3;
                red = grayScale;
                green = grayScale;
                blue = grayScale;

                //reassign the NEW color values for the pixel.
                Color newPixel = new Color(red, green, blue);
                img.setRGB(x, y, newPixel.getRGB());
            }
        }
    }

    /**
     * Method to reflect an image horizontally.
     * @param img - The BufferedImage to be reflected
     */
    private void changeReflect(BufferedImage img) {
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
        }

    /**
     * Method that turns an image into sepia. Does this by getting the color values for each pixel then doing some calculations.
     * @param img - Image to be turned sepia.
     */
    private void changeSepia(BufferedImage img) {
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int pixel = img.getRGB(x, y);
                Color color = new Color(pixel);

                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                double testRed = (red * 0.393) +(green * 0.769) + (blue * 0.189);
                double testGreen = (red * 0.349) +(green * 0.686) + (blue * 0.168);
                double testBlue =(red * 0.272) +(green * 0.534) + (blue * 0.131);

                int sepiaRed = (int)Math.round(testRed);
                int sepiaBlue = (int)Math.round(testBlue);
                int sepiaGreen = (int)Math.round(testGreen);
                //If the color value is over 255, sets it to 255. I don't think it's possible for blue to be over 255, at least I hope it's not.
                if(sepiaRed >= 255){
                    sepiaRed = 255;
                }
                if(sepiaGreen >= 255){
                    sepiaGreen = 255;
                }
                red = sepiaRed;
                green = sepiaGreen;
                blue = sepiaBlue;

                Color newPixel = new Color(red, green, blue);
                img.setRGB(x, y, newPixel.getRGB());
            }
        }
    }

    /**
     * Displays the edited image to the second ImageView
     * @param image - Newly transformed image to be displayed
     */
    public void displayImage(Image image, ImageView imageView) {
        //set the image and then the resoluuuuution as well!!!
        imageView.setImage(image);
        imageView.setFitHeight(300);
        imageView.setFitWidth(300);
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