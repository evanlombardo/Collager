package controller.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.Collager;
import model.Image;
import model.Layer;
import model.Pixel;

/**
 * This class contains utility methods to read and write images, and read and write project files.
 * Can read and write from PPM, JPEG, and PNG.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and return its contents as a string.
   *
   * @param filename the path of the file.
   * @return the image file read as a String.
   * @throws IllegalArgumentException if the filename is null or the file does not exist.
   */
  public static String readPPM(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Filename cannot be null");
    }

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filename + " not found");
    }
    StringBuilder builder = new StringBuilder();
    // Read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    return builder.toString();
  }

  /**
   * Reads a collager file given the filename of the collager file and the resulting
   * {@code Collager} to open the project with.
   *
   * @param filename is the filename to open the collager project from.
   * @param collager is the {@code Collager} to open the project with.
   * @throws IllegalArgumentException if the filename or collager is null, the file cannot
   *                                  be found, or if the file is not a valid collager file.
   */
  public static void readCollage(String filename, Collager collager)
          throws IllegalArgumentException {

    if (filename == null) {
      throw new IllegalArgumentException("Filename cannot be null");
    }

    if (collager == null) {
      throw new IllegalArgumentException("Collager cannot be null");
    }

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filename + " not found");
    }

    StringBuilder builder = new StringBuilder();
    // Read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    collager.loadProject(builder.toString());
  }

  /**
   * Saves a collager project to the given filepath, given the height and width of the images,
   * and given the list of layers in the collager project.
   *
   * @param filepath is the filepath to save the project file to.
   * @param height   is the height of the images in the layers.
   * @param width    is the width of the images in the layers.
   * @param layers   is the list of layers that are in the project.
   * @throws IllegalArgumentException if the filepath is null, the height and width are less than
   *                                  1, the list of layers are null, any of the layers in the list
   *                                  are null, or if the project fails to save to the file.
   */
  public static void saveCollage(String filepath, int height, int width, List<Layer> layers)
          throws IllegalArgumentException {

    if (filepath == null) {
      throw new IllegalArgumentException("Filepath cannot be null");
    }

    if (height < 1 || width < 1) {
      throw new IllegalArgumentException("Width and height must be at least 1");
    }

    if (layers == null) {
      throw new IllegalArgumentException("Layers cannot be null");
    }

    StringBuilder contents = new StringBuilder();

    contents.append("C1\n");
    contents.append(width + " " + height + "\n");
    contents.append("255\n");

    for (Layer layer : layers) {

      if (layer == null) {
        throw new IllegalArgumentException("None of the layers can be null");
      }

      contents.append(layer.getLayerName() + " ");
      contents.append(layer.getFilterName());
      contents.append("\n");

      for (Pixel[] row : layer.getImage().getPixels()) {

        for (Pixel pixel : row) {

          int[] rgba = pixel.asRGBA();
          contents.append(rgba[0] + " " + rgba[1] + " " + rgba[2] + " " + rgba[3] + "\n");
        }
      }
    }

    try {
      File file = new File(filepath);
      file.createNewFile();
      FileWriter writer = new FileWriter(file);
      writer.write(contents.toString());
      writer.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("Error creating/writing to file: " + e.getMessage());
    }
  }

  /**
   * Saves the given image to the given file path in PPM format.
   *
   * @param filepath is the file path to save the image to.
   * @param img      is the image to save to file as a PPM.
   * @throws IllegalArgumentException if the filepath or image are null, or if the image fails
   *                                  to save to the specified file.
   */
  public static void saveImage(String filepath, Image img) throws IllegalArgumentException {

    if (filepath == null) {
      throw new IllegalArgumentException("Filepath cannot be null");
    }

    if (img == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }

    StringBuilder contents = new StringBuilder();

    contents.append("P3\n");
    contents.append(img.getWidth() + " " + img.getHeight() + "\n");
    contents.append("255\n");

    Pixel[][] pixels = img.getPixels();

    for (int i = 0; i < img.getHeight(); i++) {

      for (int j = 0; j < img.getWidth(); j++) {

        int[] rgb = pixels[i][j].asRGB();
        contents.append(rgb[0] + " " + rgb[1] + " " + rgb[2]);

        if (j < img.getWidth() - 1) {
          contents.append(" ");
        } else {
          contents.append("\n");
        }
      }
    }

    try {
      File file = new File(filepath);
      file.createNewFile();
      FileWriter writer = new FileWriter(file);
      writer.write(contents.toString());
      writer.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("Error creating/writing to file: " + e.getMessage());
    }
  }

  /**
   * Saves the given image to the given file path. Only works for PNG and JPEG filepaths.
   *
   * @param filepath is the file path to save the image to in PNG and JPEG.
   * @param img      is the image to save to file.
   * @throws IllegalArgumentException if the filepath or image are null, the file is not PNG or
   *                                  JPEG, or if the image fails to save to the specified file.
   */
  public static void saveImage(String filepath, BufferedImage img) throws IllegalArgumentException {
    if (filepath == null) {
      throw new IllegalArgumentException("Filepath cannot be null");
    }

    if (img == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }

    String[] splitFile = filepath.split("\\.");
    if (!splitFile[splitFile.length - 1].equals("png")
            && !splitFile[splitFile.length - 1].equals("jpg")
            && !splitFile[splitFile.length - 1].equals("jpeg")) {
      throw new IllegalArgumentException("Filepath must be in JPEG or PNG form");
    }

    try {
      File file = new File(filepath);
      file.createNewFile();
      ImageIO.write(img, splitFile[splitFile.length - 1], file);
    } catch (IOException e) {
      throw new IllegalArgumentException("Error writing to file");
    }
  }

  /**
   * Reads a JPEG or PNG file to a {@code BufferedImage}.
   *
   * @param filename is the filename to read from.
   * @return the {@code BufferedImage} read from file.
   * @throws IllegalArgumentException if the filename is null or of the wrong filetype.
   * @throws IllegalStateException if the file fails to read.
   */
  public static BufferedImage readJPEGPNG(String filename)
          throws IllegalArgumentException, IllegalStateException {
    if (filename == null) {
      throw new IllegalArgumentException("Filename cannot be null");
    }

    if (!filename.endsWith(".png") && !filename.endsWith(".jpg") && !filename.endsWith(".jpeg")) {
      throw new IllegalArgumentException("File is not PNG or JPEG");
    }

    try {
      return ImageIO.read(new File(filename));
    } catch (IOException e) {
      throw new IllegalStateException("Error reading file");
    }
  }
}

