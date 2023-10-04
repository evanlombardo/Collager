package model;

/**
 * Represents a pixel in an image. The pixel can be converted to RGB and RGBA, as well as be
 * combined with other pixels.
 */
public interface Pixel {

  /**
   * Represents a {@code Pixel} as an array of length 4, where the structure is {R, G, B, A}
   * where R is the red value of the pixel, G is the green value of the pixel, B is the blue
   * value of the pixel, and A is the alpha level of the pixel.
   * The maximum amount that the red, blue, green, and alpha values can be is 255.
   *
   * @return the RGBA array representation of the pixel (max 255).
   */
  int[] asRGBA();

  /**
   * Represents a {@code Pixel} as an array of length 3, where the structure is {R, G, B}
   * where R is the red value of the pixel, G is the green value of the pixel, and B is
   * the blue value of the pixel.
   * The maximum amount that the red, blue, green, and alpha values can be is 255.
   *
   * @return the RGB array representation of the pixel (max 255).
   */
  int[] asRGB();

  /**
   * Combines this pixel with another and returns the resulting pixel.
   *
   * @param that is the pixel to combine with.
   * @return the resulting pixel.
   * @throws IllegalArgumentException if the provided {@code Pixel} is null.
   */
  Pixel combine(Pixel that) throws IllegalArgumentException;

  /**
   * Gets the maximum amount that a value of the pixel can be.
   *
   * @return the maximum amount that a value of the pixel can be.
   */
  int getMax();
}
