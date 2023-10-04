package model.filters;

/**
 * Defines a filter that uses {@code RGBAPixel}s to brighten an image by
 * increasing the RGB values by its max value.
 */
public class RGBABrightenMax extends RGBABrightenDarken {

  @Override
  protected int createDifference(int red, int green, int blue) {
    return Math.max(Math.max(red, green), blue);
  }
}
