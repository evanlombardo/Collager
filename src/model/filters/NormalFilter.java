package model.filters;

import model.Image;

/**
 * Defines a filter that does nothing. This filter just returns the image that it was given.
 */
public class NormalFilter implements Filter {

  @Override
  public Image apply(Image img) throws IllegalArgumentException {
    if (img == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }

    return img;
  }
}
