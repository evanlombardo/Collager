package model.filters;

import model.Pixel;
import model.RGBAPixel;

/**
 * Defines a filter that only displays the red hue of the original image.
 */
public class RGBARedFilter extends RGBAColorFilter {

  @Override
  protected Pixel createResultPixel(Pixel pixel) {
    int[] pixelData = pixel.asRGBA();

    return new RGBAPixel(pixelData[0], 0, 0, pixelData[3], pixel.getMax());
  }
}
