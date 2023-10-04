package model;

import model.filters.FilterName;

/**
 * Represents a Layer based on RGBA value pixels and images in a collage. This type of layer uses
 * the filters in {@code FilterName}. Each layer has a name that is specified upon creation of the
 * layer, and a filter from the {@code FilterName} enum specified upon creation.
 */
public class RGBALayer implements Layer {

  private final String layerName;
  private final Image image;
  private final int height;
  private final int width;
  private FilterName filterName;

  /**
   * Creates a blank layer using the given name, filter, height, and width of the layer.
   *
   * @param layerName  the name for the layer.
   * @param filterName the filter for the layer.
   * @param height     the height of the layer.
   * @param width      the width of the layer.
   * @throws IllegalArgumentException if the layerName is null, or if the height/width
   *                                  are invalid.
   */
  public RGBALayer(String layerName, FilterName filterName, int height, int width)
          throws IllegalArgumentException {

    if (layerName == null) {
      throw new IllegalArgumentException("No name for layer was given");
    } else if (filterName == null) {
      throw new IllegalArgumentException("No filtername was given");
    } else if (height < 0 || width < 0) {
      throw new IllegalArgumentException("Invalid height or width was given");
    }

    this.layerName = layerName;
    this.filterName = filterName;
    this.height = height;
    this.width = width;
    this.image = new RGBALayerImage(this.height, this.width);

  }

  /**
   * Creates a layer given the layer's name, the filter, the image on the layer, and the
   * height/width of the layer.
   * This constructor is private to force users to start with a blank layer.
   * Therefore, layers with images on them must be created using the combine methods.
   *
   * @param layerName  the name of the layer.
   * @param filterName the name of the filter.
   * @param image      the {@code Image} to display on the layer.
   * @param height     an integer representing the height of the layer in terms of pixels.
   * @param width      an integer representing the height of the layer in terms of pixels.
   */
  private RGBALayer(String layerName, FilterName filterName, Image image, int height, int width) {

    this.layerName = layerName;
    this.filterName = filterName;
    this.height = height;
    this.width = width;
    this.image = image;
  }

  @Override
  public Layer combine(Image that, int startY, int startX) throws IllegalArgumentException {
    if (that == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }

    if (startY < 0 || startX < 0) {
      throw new IllegalArgumentException("Invalid coordinates were given");
    }

    return new RGBALayer(this.layerName, this.filterName,
            this.image.combine(that, startY, startX), this.height, this.width);
  }

  @Override
  public Layer combine(Layer that, int startY, int startX) throws IllegalArgumentException {
    if (that == null) {
      throw new IllegalArgumentException("Layer cannot be null");
    }

    if (startY < 0 || startX < 0) {
      throw new IllegalArgumentException("Invalid coordinates were given");
    }

    return combine(that.getImage(), startY, startX);
  }

  /**
   * Updates the filter for the model given a String that matches a filter in the
   * {@code FilterName} enum, which the filter is updated to.
   *
   * @param filterName is the new filter.
   * @throws IllegalArgumentException if the {@code FilterName} is null.
   */
  @Override
  public void updateFilter(String filterName) throws IllegalArgumentException {
    if (filterName == null) {
      throw new IllegalArgumentException("FilterName cannot be null");
    }

    FilterName appliedFilter = null;

    for (FilterName filter : FilterName.values()) {

      if (filter.getName().equals(filterName)) {
        appliedFilter = filter;
        break;
      }
    }

    if (appliedFilter == null) {
      throw new IllegalArgumentException("No filter with this name exists");
    }

    this.filterName = appliedFilter;
  }

  @Override
  public Layer applyFilter() {
    return new RGBALayer(this.layerName, this.filterName,
            this.image.applyFilter(this.filterName.getFilter()), this.height,
            this.width);
  }

  @Override
  public Layer applyTwoFilter(Image that) throws IllegalStateException {
    if (that == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }

    return new RGBALayer(this.layerName, this.filterName,
            this.filterName.getTwoFilter().apply(this.image, that), this.height,
            this.width);
  }

  @Override
  public Layer applyTwoFilter(Layer that) throws IllegalStateException {
    if (that == null) {
      throw new IllegalArgumentException("Layer cannot be null");
    }

    return new RGBALayer(this.layerName, this.filterName,
            this.filterName.getTwoFilter().apply(this.image, that.getImage()), this.height,
            this.width);
  }

  @Override
  public String getLayerName() {
    return this.layerName;
  }

  @Override
  public String getFilterName() {
    return this.filterName.getName();
  }

  @Override
  public Image getImage() {
    return new RGBALayerImage(this.image.getPixels());
  }

}