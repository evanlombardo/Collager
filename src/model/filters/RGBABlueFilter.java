package model.filters;

import model.Pixel;
import model.RGBAPixel;

/**
 * Defines a filter that only displays the blue hue of the original image. This filter uses
 * {@code RGBAPixel}s.
 */
public class RGBABlueFilter extends RGBAColorFilter {

  @Override
  protected Pixel createResultPixel(Pixel pixel) {
    int[] pixelData = pixel.asRGBA();

    return new RGBAPixel(0, 0, pixelData[2], pixelData[3], pixel.getMax());
  }
}
