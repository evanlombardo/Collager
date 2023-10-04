package model.filters;

import model.Image;

/**
 * Defines a filter to operate on an image. Operates on and returns the {@code Image} type defined
 * in this model.
 */
public interface Filter {

  /**
   * Places the filter on the given image and returns the resulting image.
   *
   * @param img is the resulting image.
   * @throws IllegalArgumentException if the image given is null or any
   *                                  pixel in the image is null.
   */
  Image apply(Image img) throws IllegalArgumentException;
}
