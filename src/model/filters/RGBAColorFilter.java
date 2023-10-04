package model.filters;

import model.Image;
import model.Pixel;
import model.RGBALayerImage;

/**
 * Defines a filter that uses {@code RGBAPixel}s.
 */
public abstract class RGBAColorFilter implements Filter {

  protected final static int MIN = 0;
  protected final static int MAX = 255;

  /**
   * Applies the certain filter to the given {@code Image} and returns a new image with that
   * filter applied to it.
   *
   * @param img is the image to be filtered.
   * @return is the resulting image.
   * @throws IllegalArgumentException if the image given is null.
   */
  public Image apply(Image img) throws IllegalArgumentException {
    if (img == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }

    Pixel[][] pixels = img.getPixels();
    Pixel[][] resultPixels = new Pixel[pixels.length][pixels[0].length];

    for (int i = 0; i < pixels.length; i++) {

      for (int j = 0; j < pixels[i].length; j++) {

        if (pixels[i][j] == null) {
          throw new IllegalArgumentException("One or more pixels in the image do not exist");
        }

        resultPixels[i][j] = createResultPixel(pixels[i][j]);
      }
    }

    return new RGBALayerImage(resultPixels);
  }

  /**
   * Creates the filtered pixel to add to the image given the original pixel.
   * The resulting pixel must have a 255 maximum amount for the values.
   *
   * @param pixel is the pixel to be filtered.
   * @return the {@code Pixel} to add to the image (max 255).
   */
  protected abstract Pixel createResultPixel(Pixel pixel);
}
