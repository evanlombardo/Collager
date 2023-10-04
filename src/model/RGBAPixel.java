package model;

/**
 * Represents a pixel in an image using the red values, green values, blue values, and the alpha
 * level of the pixel. The max amount of the pixel's values is 255 and the min amount of pixel's
 * values is 0. When creating the pixel, it will convert the values to 0-255 given the max (min is
 * assumed to be 0)
 */
public class RGBAPixel implements Pixel {

  private static final int MIN = 0;
  private static final int MAX = 255;

  private final int red;
  private final int green;
  private final int blue;
  private final int alpha;

  /**
   * Creates an {@code RGBAPixel} given the red value, green value, blue value, alpha level, and
   * max amount for the pixel.
   * The values for red, green, blue, and alpha must all be in between 0 (MIN) and max.
   * The values are converted to have a max of 255.
   *
   * @param red   is the red value of the pixel.
   * @param green is the green value of the pixel.
   * @param blue  is the blue value of the pixel.
   * @param alpha is the alpha value of the pixel.
   * @param max is the maximum amount that the values of the pixel can be.
   * @throws IllegalArgumentException if any parameter given is greater than the max or less
   *                                  than 0 (MIN).
   */
  public RGBAPixel(int red, int green, int blue, int alpha, int max)
          throws IllegalArgumentException {

    if (max < 1) {
      throw new IllegalArgumentException("Maximum value must be greater than 0 (min)");
    }

    if (red > max || red < MIN) {
      throw new IllegalArgumentException("Red value is invalid, must be between " + MIN +
              " and " + max);
    }

    if (blue > max || blue < MIN) {
      throw new IllegalArgumentException("Blue value is invalid, must be between " + MIN +
              " and " + max);
    }
    if (green > max || green < MIN) {
      throw new IllegalArgumentException("Green value is invalid, must be between " + MIN +
              " and " + max);
    }

    if (alpha > max || alpha < MIN) {
      throw new IllegalArgumentException("Alpha value is invalid, must be between " + MIN +
              " and " + max);
    }

    this.red = (int) Math.round((1 - Math.pow(0, red)) * ((MAX + 1) * ((red + 1) /
            ((double) max + 1)) - 1));
    this.green = (int) Math.round((1 - Math.pow(0, green)) * ((MAX + 1) * ((green + 1) /
            ((double) max + 1)) - 1));
    this.blue = (int) Math.round((1 - Math.pow(0, blue)) * ((MAX + 1) * ((blue + 1) /
            ((double) max + 1)) - 1));
    this.alpha = (int) Math.round((1 - Math.pow(0, alpha)) * ((MAX + 1) * ((alpha + 1) /
            ((double) max + 1)) - 1));
  }

  /**
   * Creates an {@code RGBAPixel} given the red value, green value, blue value, and max amount
   * for the pixel.
   * The values for red, green, and blue must all be in between 0 (MIN) and 255 (MAX).
   * Alpha level defaults to the maximum opacity, 255 (MAX).
   * The values are converted to have a max of 255.
   *
   * @param red   is the red value of the pixel.
   * @param green is the green value of the pixel.
   * @param blue  is the blue value of the pixel.
   * @param max is the maximum amount that the values of the pixel can be.
   * @throws IllegalArgumentException if any parameter given is greater than 255 (MAX) or less
   *                                  than 0 (MIN).
   */
  public RGBAPixel(int red, int green, int blue, int max) throws IllegalArgumentException {

    if (red > max || red < MIN) {
      throw new IllegalArgumentException("Red value is invalid, must be between " + MIN +
              " and " + max);
    }


    if (green > max || green < MIN) {
      throw new IllegalArgumentException("Green value is invalid, must be between " + MIN +
              " and " + max);
    }

    if (blue > max || blue < MIN) {
      throw new IllegalArgumentException("Blue value is invalid, must be between " + MIN +
              " and " + max);
    }

    this.red = (int) ((red * ((double) max)) / 255);
    this.green = (int) ((green * ((double) max)) / 255);
    this.blue = (int) ((blue * ((double) max)) / 255);
    this.alpha = MAX;
  }

  @Override
  public int[] asRGBA() {
    return new int[]{this.red, this.green, this.blue, this.alpha};
  }

  /**
   * Represents a {@code Pixel} as an array of length 3, where the structure is {R, G, B}
   * where R is the red value of the pixel, G is the green value of the pixel, and B is
   * the blue value of the pixel.
   * Changes the colors based on the opacity.
   *
   * @return the RGB array representation of the pixel.
   */
  @Override
  public int[] asRGB() {
    double calcAlpha = this.alpha / 255.0;

    int newRed = (int) Math.round(this.red * calcAlpha);
    int newGreen = (int) Math.round(this.green * calcAlpha);
    int newBlue = (int) Math.round(this.blue * calcAlpha);

    return new int[]{newRed, newGreen, newBlue};
  }

  @Override
  public Pixel combine(Pixel that) throws IllegalArgumentException {

    if (that == null) {
      throw new IllegalArgumentException("Pixel tried to combine with a null pixel");
    }
    int[] topPixel = that.asRGBA();

    double calcAlpha = (topPixel[3] / 255.0) + (this.alpha / 255.0) * (1 - (topPixel[3] / 255.0));
    int newAlpha = (int) Math.round(calcAlpha * 255);

    int newRed = (int) Math.round((((topPixel[3] / 255.0) * topPixel[0]) +
            (this.red * (this.alpha / 255.0)) * (1 - (topPixel[3] / 255.0))) * (1 / calcAlpha));
    int newGreen = (int) Math.round((((topPixel[3] / 255.0) * topPixel[1]) +
            (this.green * (this.alpha / 255.0)) * (1 - (topPixel[3] / 255.0))) * (1 / calcAlpha));
    int newBlue = (int) Math.round((((topPixel[3] / 255.0) * topPixel[2]) +
            (this.blue * (this.alpha / 255.0)) * (1 - (topPixel[3] / 255.0))) * (1 / calcAlpha));

    return new RGBAPixel(newRed, newGreen, newBlue, newAlpha, 255);
  }

  @Override
  public int getMax() {
    return MAX;
  }
}
