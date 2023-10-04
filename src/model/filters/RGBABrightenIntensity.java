package model.filters;

/**
 * Defines a filter that uses {@code RGBAPixel}s to brighten an image by
 * increasing the RGB values by its average.
 */
public class RGBABrightenIntensity extends RGBABrightenDarken {

  @Override
  protected int createDifference(int red, int green, int blue) {
    return (int) Math.round((red + green + blue) / 3.0);
  }
}