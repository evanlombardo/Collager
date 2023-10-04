package controller;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Defines the controller to run the collage program. The getLayerNames, setFilter, addLayer
 * createProject, getFinalImage, getImageAt, and getFilterNames methods should use the respective
 * methods in the model for their implementation. The controller should not define these behaviors
 * on its own.
 */
public interface CollageController {

  /**
   * Runs the collager program.
   *
   * @throws IllegalStateException if reading/writing input fails.
   */
  void runCollage() throws IllegalStateException;

  /**
   * Saves the current project given the filepath to save it at.
   *
   * @throws IllegalArgumentException if the filepath is null or if the project fails to save.
   * @throws IllegalStateException    if the user attempts to save without a project or layer.
   */
  void saveProject(String filepath) throws IllegalArgumentException, IllegalStateException;

  /**
   * Saves the image at the specified filepath.
   *
   * @param filename is the filepath to save the file at.
   * @throws IllegalArgumentException if the filename is null or if the image fails to save.
   * @throws IllegalStateException    if the user tries to save the image without a project.
   */
  void saveImage(String filename) throws IllegalArgumentException, IllegalStateException;

  /**
   * Saves the image from the given layer at the specified filepath.
   *
   * @param filename is the name of the file to save to.
   * @param layername is the layer name to save from.
   * @throws IllegalArgumentException if the layername or filename is null, the layer does
   *                                  not exist, or the image fails to save.
   * @throws IllegalStateException if the use tries to save without a project.
   */
  void saveImage(String filename, String layername)
          throws IllegalArgumentException, IllegalStateException;

  /**
   * Adds an image to the current layer given the layer name, the filepath of the image,
   * and the starting coordinates to add the image at. If the image is not a PNG or JPEG image,
   * it is assumed to be a PPM image.
   *
   * @param layerName is the name of the layer to add the image to.
   * @param filepath  is the filepath of the image.
   * @param startY    is the starting Y position to add the image at.
   * @param startX    is the starting X position to add the image at.
   * @throws IllegalArgumentException if the layerName or filepath is null, if the layer or file
   *                                  does not exist, or if the start position is invalid.
   * @throws IllegalStateException    if the user attempts to add an image without a project.
   */
  void addImage(String layerName, String filepath, int startY, int startX)
          throws IllegalArgumentException, IllegalStateException;

  /**
   * Loads a project from the given filepath.
   *
   * @param filepath is the filepath to load the project from.
   * @throws IllegalArgumentException if the String is null, if the file does not exist, or
   *                                  if the file is not a valid Collager file.
   */
  void loadProject(String filepath) throws IllegalArgumentException;

  /**
   * Gets the layer names according to the model.
   *
   * @return a list containing the names of the layers.
   * @throws IllegalStateException if the user tries to get layer names without a project or layer.
   */
  List<String> getLayerNames() throws IllegalStateException;

  /**
   * Sets the layer's filter according to the model.
   *
   * @param layername  is the name of the layer to set the filter of.
   * @param filtername is the name of the filter to set.
   * @throws IllegalArgumentException if the layerName or filterName is null, or if the
   *                                  layer or filter does not exist.
   * @throws IllegalStateException    if the user attempts to change the filter without a project.
   */
  void setFilter(String layername, String filtername)
          throws IllegalArgumentException, IllegalStateException;

  /**
   * Adds the layer according to the model.
   *
   * @param name is the name of the layer to add.
   * @throws IllegalArgumentException if the name is null or already exists.
   * @throws IllegalStateException    if the user attempts to add a layer without a project.
   */
  void addLayer(String name) throws IllegalArgumentException, IllegalStateException;

  /**
   * Creates a project according to the model.
   *
   * @param height is the height of the project.
   * @param width  is the width of the project.
   * @throws IllegalArgumentException if the height or width are invalid.
   */
  void createProject(int height, int width) throws IllegalArgumentException;

  /**
   * Gets the final image (as default Java {@code BufferedImage} type) according to the model.
   *
   * @return the final image.
   * @throws IllegalStateException if there are no layers or no project.
   */
  BufferedImage getFinalImage() throws IllegalStateException;

  /**
   * Gets the image as a {@code BufferedImage} at the given layer according to the model.
   *
   * @param layername is the name of the layer.
   * @return the image at the layer.
   * @throws IllegalStateException if there are no layers or no project.
   * @throws IllegalArgumentException if the layer does not exist.
   */
  BufferedImage getImageAt(String layername) throws IllegalStateException, IllegalArgumentException;

  /**
   * Gets the names of the filters in the model as an array of Strings.
   *
   * @return the array of filter names.
   */
  String[] getFilterNames();
}
