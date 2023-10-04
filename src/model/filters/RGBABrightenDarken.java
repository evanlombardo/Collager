package model.filters;

import model.Pixel;
import model.RGBAPixel;

/**
 * Defines a type of filter that either brightens or darkens an image by adding a value to it.
 * This type of filter uses {@code RGBAPixel}s.
 */
public abstract class RGBABrightenDarken extends RGBAColorFilter {

  @Override
  protected Pixel createResultPixel(Pixel pixel) {
    int[] pixelData = pixel.asRGBA();

    int difference = createDifference(pixelData[0], pixelData[1], pixelData[2]);

    int newRed = pixelData[0] + difference;
    int newGreen = pixelData[1] + difference;
    int newBlue = pixelData[2] + difference;

    if (newRed > MAX) {
      newRed = MAX;
    } else if (newRed < MIN) {
      newRed = MIN;
    }

    if (newGreen > MAX) {
      newGreen = MAX;
    } else if (newGreen < MIN) {
      newGreen = MIN;
    }

    if (newBlue > MAX) {
      newBlue = MAX;
    } else if (newBlue < MIN) {
      newBlue = MIN;
    }

    return new RGBAPixel(newRed, newGreen, newBlue, pixelData[3], pixel.getMax());
  }

  /**
   * Creates the difference, which is the number to add to the original pixels to
   * brighten or darken them.
   * If the difference is positive, then the image brightens.
   * If the difference is negative, the image darkens.
   * The value is adjusted to ensure that it is between the minimum and maximum.
   *
   * @param red   is the initial red value of the pixel.
   * @param green is the initial green value of the pixel.
   * @param blue  is the initial blue value of the pixel.
   * @return is the difference to brighten or darken the image.
   */
  protected abstract int createDifference(int red, int green, int blue);
}
