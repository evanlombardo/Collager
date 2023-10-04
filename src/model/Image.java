package model;

import model.filters.Filter;
import model.filters.TwoFilter;

/**
 * Represents an image as a collection of {@code Pixel}s. No pixel in an {@code Image} is null.
 */
public interface Image {

  /**
   * Represents the image as a 2-D array of its pixels.
   * It should be noted that the 2-D array should not be an alias of the data used for the image.
   *
   * @return the 2-D array of pixels.
   */
  Pixel[][] getPixels();

  /**
   * Combines two images and returns the resulting image, given an image and the starting
   * coordinates to place the image.
   *
   * @param that   is the image to combine with.
   * @param startY the Y coordinate of the top left corner of {@code that} image.
   * @param startX the X coordinate of the top left corner of {@code that} image.
   * @return a new {@code Image} object that is the combination of the two images.
   */
  Image combine(Image that, int startY, int startX);

  /**
   * Applies the provided filter to this image.
   *
   * @param filter is the {@code Filter} to apply.
   * @return a new {@code Image} with the applied filter.
   */
  Image applyFilter(Filter filter);

  /**
   * Applies the provided filter to this image.
   *
   * @param filter is the {@code TwoFilter} to apply.
   * @param other is the other image to use in the application of the filter.
   * @return a new {@code Image} with the applied filter.
   */
  Image applyTwoFilter(TwoFilter filter, Image other);

  /**
   * Provides the height of an image.
   *
   * @return a new integer representing the height of the image.
   */
  int getHeight();

  /**
   * Provides the width of an image.
   *
   * @return a new integer representing the width of the image.
   */
  int getWidth();
}
