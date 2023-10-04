package model.filters;

import model.Image;
import model.Pixel;
import model.RGBALayerImage;
import model.RGBAPixel;

/**
 * Defines a general {@code TwoFilter} that can be applied with two images. This uses
 * {@code RGBAPixel}s.
 */
public abstract class RGBAColorTwoFilter extends RGBAColorFilter implements TwoFilter {

  /**
   * Applies the filter given the images to use in the filter.
   *
   * @param img1 is the first image to use in the filter.
   * @param img2 is the second image to use in the filter.
   *             This is defined as the lower layer.
   * @return is the resulting image.
   * @throws IllegalArgumentException if either image given is null, any pixels in the images are
   *                                  null, or if the second image is not the same size as the
   *                                  first.
   */
  @Override
  public Image apply(Image img1, Image img2) throws IllegalArgumentException {
    if (img1 == null || img2 == null) {
      throw new IllegalArgumentException("Images cannot be null");
    }

    if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
      throw new IllegalArgumentException("Images must be same size");
    }

    Pixel[][] pixels1 = img1.getPixels();
    Pixel[][] pixels2 = img2.getPixels();
    Pixel[][] resultPixels = new Pixel[img1.getHeight()][img1.getWidth()];

    for (int i = 0; i < img1.getHeight(); i++) {

      for (int j = 0; j < img1.getWidth(); j++) {

        if (pixels1[i][j] == null || pixels2[i][j] == null) {
          throw new IllegalArgumentException("Pixels in images cannot be null");
        }

        resultPixels[i][j] = createResultPixel(pixels1[i][j], pixels2[i][j]);
      }
    }

    return new RGBALayerImage(resultPixels);
  }

  @Override
  protected Pixel createResultPixel(Pixel pixel) {
    return createResultPixel(pixel, new RGBAPixel(255, 255, 255, 0, 255));
  }

  protected abstract Pixel createResultPixel(Pixel pixel1, Pixel pixel2);
}
