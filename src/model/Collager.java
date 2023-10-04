package model;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Defines basic operations for a model of the collager program.
 */
public interface Collager {

  /**
   * Creates a new project given the height and width of the canvas.
   *
   * @param height is the height of the canvas.
   * @param width  is the width of the canvas.
   * @throws IllegalArgumentException if the height or width are invalid.
   */
  void createProject(int height, int width) throws IllegalArgumentException;

  /**
   * Adds a layer to the project with the specified name. The layer starts with an image by default.
   *
   * @param name is the name of the new layer.
   * @throws IllegalArgumentException if the name is null or already exists.
   * @throws IllegalStateException    if the user attempts to add a layer without a project.
   */
  void addLayer(String name) throws IllegalArgumentException, IllegalStateException;

  /**
   * Loads a collager project given a String that represents the contents of a collager file.
   *
   * @param collagerContents is the contents of the collager file.
   * @throws IllegalArgumentException if the format is not of a collager file or if the contents
   *                                  are null.
   */
  void loadProject(String collagerContents) throws IllegalArgumentException;

  /**
   * Sets the filter of a given layer. Only works for filters that are supported by the model.
   *
   * @param layerName  is the layer name to set the filter of.
   * @param filterName is the filter to set the layer to.
   * @throws IllegalArgumentException if the layerName or filterName is null, if the
   *                                  layer does not exist, or if the filter does not exist in
   *                                  this model.
   * @throws IllegalStateException    if the user attempts to change the filter without a project.
   */
  void setFilter(String layerName, String filterName) throws IllegalArgumentException,
          IllegalStateException;

  /**
   * Adds an image to the current layer given the layer name, the file contents of the image,
   * and the starting coordinates to add the image at. File contents should be in PPM format.
   *
   * @param layerName is the name of the layer to add the image to.
   * @param fileContents  is the file's content that contains the image.
   * @param startY    is the starting Y position to add the image at.
   * @param startX    is the starting X position to add the image at.
   * @throws IllegalArgumentException if the layerName or file contents are null, if the layer does
   *                                  not exist, if the start position is invalid, or if the file
   *                                  contents are invalid.
   * @throws IllegalStateException    if the user attempts to add an image without a project.
   */
  void addImage(String layerName, String fileContents, int startY, int startX)
          throws IllegalArgumentException, IllegalStateException;

  /**
   * Adds an image to the current layer given the layer name, the image to add,
   * and the starting coordinates to add the image at.
   *
   * @param layerName is the name of the layer to add the image to.
   * @param img       is the image to add.
   * @param startY    is the starting Y position to add the image at.
   * @param startX    is the starting X position to add the image at.
   * @throws IllegalArgumentException if the layerName or image is null, if the layer
   *                                  does not exist, or if the start position is invalid.
   * @throws IllegalStateException    if the user attempts to add an image without a project.
   */
  void addImage(String layerName, Image img, int startY, int startX)
          throws IllegalArgumentException, IllegalStateException;

  /**
   * Adds an image to the current layer given the layer name, the image to add,
   * and the starting coordinates to add the image at. This method uses {@code BufferedImage}s and
   * converts them to the model's representation of an image.
   *
   * @param layerName is the name of the layer to add the image to.
   * @param img       is the image to add as a {@code BufferedImage}.
   * @param startY    is the starting Y position to add the image at.
   * @param startX    is the starting X position to add the image at.
   * @throws IllegalArgumentException if the layerName or image is null, if the layer
   *                                  does not exist, or if the start position is invalid.
   * @throws IllegalStateException    if the user attempts to add an image without a project.
   */
  void addImage(String layerName, BufferedImage img, int startY, int startX)
          throws IllegalArgumentException, IllegalStateException;

  /**
   * Gets a list of the layer names in the collager.
   *
   * @return the list of layer names
   * @throws IllegalStateException if the user tries to get layer names without a project or layer.
   */
  List<String> getLayerNames() throws IllegalStateException;

  /**
   * Gets the final image, the combination of all the filters and layers.
   *
   * @return the final image.
   * @throws IllegalStateException if there are no layers or no project.
   */
  Image getFinalImage() throws IllegalStateException;

  /**
   * Gets the image at the specified layer, the combination of all the filters and layers up to
   * that point.
   *
   * @param layername is the name of the layer to get at.
   * @return the image.
   * @throws IllegalStateException if there are no layers or no project.
   * @throws IllegalArgumentException if the layer does not exist.
   */
  Image getImageAtLayer(String layername) throws IllegalStateException, IllegalArgumentException;

  /**
   * Gets the height of the project.
   *
   * @return the height of the project.
   * @throws IllegalStateException if the project does not exist.
   */
  int getHeight() throws IllegalStateException;

  /**
   * Gets the width of the project.
   *
   * @return the width of the project.
   * @throws IllegalStateException if the project does not exist.
   */
  int getWidth() throws IllegalStateException;

  /**
   * Gets the list of layers in the collager.
   *
   * @return the list of layers in the collager program.
   * @throws IllegalStateException if the project does not exist.
   */
  List<Layer> getLayers() throws IllegalStateException;

  /**
   * Converts an image of this implementation to a Java {@code BufferedImage} type.
   *
   * @param img is the image to convert.
   * @param bufferedImageType is the type of {@code BufferedImage} to create. See
   *                          {@code BufferedImage} imageType fields to see options.
   * @return is the resulting Java Image type object.
   */
  BufferedImage createJavaImage(Image img, int bufferedImageType);

  /**
   * Gets the filter names available in this {@code Collager} as an array.
   *
   * @return the filter names as an array.
   */
  String[] getFilterNames();
}
