package model;

/**
 * Defines the behavior of one of the layers in a collager model.
 */
public interface Layer {

  /**
   * Combines the image with the layer and returns the resulting layer, given an image and the
   * starting coordinates to place the image.
   *
   * @param that   is the image to combine with.
   * @param startY the Y coordinate from the top left corner of the image.
   * @param startX the X coordinate from the top left corner of the image.
   * @return a new {@code Layer} object that is the combination of the layer and the image.
   * @throws IllegalArgumentException if the image is null.
   */
  Layer combine(Image that, int startY, int startX) throws IllegalArgumentException;

  /**
   * Combines the layers and returns the resulting layer, given a layer and the starting
   * coordinates to place the image.
   *
   * @param that   is the layer to combine with.
   * @param startY the Y coordinate of the top left corner of the image.
   * @param startX the X coordinate of the top left corner of the image.
   * @return a new {@code Layer} object that is the combination of the layer and the image.
   * @throws IllegalArgumentException if the layer is null.
   */
  Layer combine(Layer that, int startY, int startX) throws IllegalArgumentException;

  /**
   * Updates the filter for the layer given a String that matches a filter in the
   * model. If the filter exists, the layer's filter is updated to that filter.
   *
   * @param filterName is the name of the filter to update to.
   * @throws IllegalArgumentException if the filterName is null.
   */
  void updateFilter(String filterName) throws IllegalArgumentException;

  /**
   * Applies the filter to the layer.
   *
   * @return a new {@code Layer} with the applied filter.
   */
  Layer applyFilter();

  /**
   * Applies the {@code TwoFilter} to the layer with the image. The provided image is assumed to
   * be the image beneath.
   *
   * @param that is the lower image to apply with.
   * @return a new {@code Layer} with the applied filter.
   * @throws IllegalStateException if the current filter is not a {@code TwoFilter}.
   */
  Layer applyTwoFilter(Image that) throws IllegalStateException;

  /**
   * Applies the {@code TwoFilter} to the layer with the layer. The provided image is assumed to
   * be the image beneath.
   *
   * @param that is the lower layer to apply with.
   * @return a new {@code Layer} with the applied filter.
   * @throws IllegalStateException if the current filter is not a {@code TwoFilter}.
   */
  Layer applyTwoFilter(Layer that) throws IllegalStateException;

  /**
   * Gets the name of the layer.
   *
   * @return the name of the layer.
   */
  String getLayerName();

  /**
   * Gets the name of the filter.
   *
   * @return the name of the filter.
   */
  String getFilterName();

  /**
   * Gets a copy of the image stored in the layer.
   *
   * @return the copy of the image in the layer.
   */
  Image getImage();
}
