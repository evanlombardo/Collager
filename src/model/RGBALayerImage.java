package model;

import java.util.Arrays;

import model.filters.Filter;
import model.filters.TwoFilter;

/**
 * Image that by default uses {@code RGBAPixel}s. Represents the image as a 2D array of pixels.
 */
public class RGBALayerImage implements Image {

  private final int height;
  private final int width;
  private final Pixel[][] pixels;

  /**
   * Creates the image given the height and width that the image should be.
   *
   * @param height is the height of the image.
   * @param width  is the width of the image.
   * @throws IllegalArgumentException if the height or width are invalid.
   */
  public RGBALayerImage(int height, int width) throws IllegalArgumentException {

    if (height < 1) {
      throw new IllegalArgumentException("Height cannot be less than 1 pixel");
    }

    if (width < 1) {
      throw new IllegalArgumentException("Width cannot be less than 1 pixel");
    }

    this.height = height;
    this.width = width;
    this.pixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {

      for (int j = 0; j < width; j++) {

        this.pixels[i][j] = new RGBAPixel(255, 255, 255, 0, 255);
      }
    }
  }

  /**
   * Creates the image given the pixels that the image should be created with.
   *
   * @param pixels are the {@code Pixel}s that make up the image.
   * @throws IllegalArgumentException if the list or any pixels in the list are null, or if the
   *                                  list of pixels is improperly sized (both height and width
   *                                  must be at least 1).
   */
  public RGBALayerImage(Pixel[][] pixels) throws IllegalArgumentException {

    if (pixels == null) {
      throw new IllegalArgumentException("Pixels cannot be null");
    }

    if (pixels.length < 1) {
      throw new IllegalArgumentException("Height cannot be less than 1 pixel");
    }

    if (pixels[0].length < 1) {
      throw new IllegalArgumentException("Width cannot be less than 1 pixel");
    }

    for (Pixel[] row : pixels) {

      for (Pixel p : row) {

        if (p == null) {
          throw new IllegalArgumentException("No pixels can be null");
        }
      }
    }

    this.height = pixels.length;
    this.width = pixels[0].length;
    this.pixels = pixels;
  }


  @Override
  public Pixel[][] getPixels() {
    Pixel[][] returnArray = new Pixel[this.height][this.width];

    for (int i = 0; i < height; i++) {
      returnArray[i] = Arrays.copyOf(this.pixels[i], width);
    }

    return returnArray;
  }

  @Override
  public Image combine(Image that, int startY, int startX) throws IllegalArgumentException {

    if (that == null) {
      throw new IllegalArgumentException("Image that cannot be null");
    }

    Pixel[][] thatPixels = that.getPixels();
    Pixel[][] resultPixels = new Pixel[height][width];

    if (startY < 0 || startY + thatPixels.length > height) {
      throw new IllegalArgumentException("Invalid starting Y position or image height, image " +
              "must be fully contained");
    }

    if (startX < 0 || startX + thatPixels[0].length > width) {
      throw new IllegalArgumentException("Invalid starting X position or image width, image " +
              "must be fully contained");
    }

    for (int i = 0; i < thatPixels.length; i++) {

      for (int j = 0; j < thatPixels[i].length; j++) {

        resultPixels[i + startY][j + startX] = this.pixels[i + startY][j + startX]
                .combine(thatPixels[i][j]);
      }
    }

    for (int i = 0; i < this.height; i++) {

      for (int j = 0; j < this.width; j++) {

        if (resultPixels[i][j] == null) {
          resultPixels[i][j] = this.pixels[i][j];
        }
      }
    }

    return new RGBALayerImage(resultPixels);
  }

  @Override
  public Image applyFilter(Filter filter) {
    if (filter == null) {
      throw new IllegalArgumentException("Filter cannot be null");
    }

    return filter.apply(this);
  }

  @Override
  public Image applyTwoFilter(TwoFilter filter, Image other) {
    if (filter == null) {
      throw new IllegalArgumentException("Filter cannot be null");
    }

    if (other == null) {
      throw new IllegalArgumentException("Other image cannot be null");
    }

    return filter.apply(this, other);
  }

  @Override
  public int getHeight() {

    return this.getPixels().length;

  }

  @Override
  public int getWidth() {

    return this.getPixels()[0].length;

  }

}
