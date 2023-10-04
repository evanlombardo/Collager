package model.filters;

import model.Pixel;
import model.RGBAPixel;

/**
 * Defines how the inversion difference filter creates new pixels. Subtracts the RGB values of the
 * top pixel by the RGBA values of the bottom pixel, using the alpha value of the bottom pixel to
 * create the new {@code Pixel}.
 */
public class RGBADifference extends RGBAColorTwoFilter {

  @Override
  protected Pixel createResultPixel(Pixel pixel1, Pixel pixel2) {
    int[] rgba1 = pixel1.asRGB();
    int[] rgba2 = pixel2.asRGBA();

    int newRed = Math.abs(rgba1[0] - rgba2[0]);
    int newGreen = Math.abs(rgba1[1] - rgba2[1]);
    int newBlue = Math.abs(rgba1[2] - rgba2[2]);

    return new RGBAPixel(newRed, newGreen, newBlue, rgba2[3], 255);
  }

}
