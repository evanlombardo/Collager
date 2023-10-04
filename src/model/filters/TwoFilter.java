package model.filters;

import model.Image;

/**
 * Represents a filter that acts on two images. Operates on two {@code Image}s and returns the
 * resulting {@code Image}. The first image is assumed to be the top and the second image is
 * assumed to be the bottom.
 */
public interface TwoFilter extends Filter {

  /**
   * Applies the filter given the images to use in the filter.
   *
   * @param img1 is the first image to use in the filter.
   * @param img2 is the second image to use in the filter.
   * @return is the resulting image.
   * @throws IllegalArgumentException if either image given is null, any pixels in the images are
   *                                  null, or if the second image is not the same size as the
   *                                  first.
   */
  Image apply(Image img1, Image img2) throws IllegalArgumentException;
}
