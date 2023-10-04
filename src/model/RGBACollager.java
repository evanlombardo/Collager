package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.filters.FilterName;

/**
 * Defines a {@code Collager} that uses an {@code RGBALayer} to represent its layers. The layers
 * are stored in a list.
 */
public class RGBACollager implements Collager {

  private final List<Layer> layers;
  private int height;
  private int width;

  /**
   * Creates the {@code RGBACollager} object with no project by default (height and width 0) and
   * with no layers by default (since there is no project).
   */
  public RGBACollager() {

    this.height = 0;
    this.width = 0;
    this.layers = new ArrayList<>();

  }

  /**
   * Creates the {@code RGBACollager} object and a project with the given height and width. No
   * layers are created by default.
   *
   * @throws IllegalArgumentException when the height or width are invalid.
   */
  public RGBACollager(int height, int width) throws IllegalArgumentException {

    this.layers = new ArrayList<>();
    createProject(height, width);

  }

  @Override
  public void createProject(int height, int width) throws IllegalArgumentException {

    if (height <= 0 || width <= 0) {
      throw new IllegalArgumentException("Invalid height or width was given");
    }

    this.height = height;
    this.width = width;
    this.layers.clear();
  }

  @Override
  public void addLayer(String name) throws IllegalArgumentException, IllegalStateException {

    if (this.height == 0 || this.width == 0) {
      throw new IllegalStateException("Cannot add layer to a project that does not exist");
    }

    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }

    for (Layer layer : this.layers) {

      if (layer.getLayerName().equals(name)) {
        throw new IllegalArgumentException("Name already exists");
      }
    }

    this.layers.add(new RGBALayer(name, FilterName.valueOf("NORMAL"), this.height, this.width));
  }

  @Override
  public void loadProject(String collagerContents) throws IllegalArgumentException {

    if (collagerContents == null) {
      throw new IllegalArgumentException("Collager file contents cannot be null");
    }

    String token;

    Scanner sc = new Scanner(collagerContents);

    token = sc.next();
    if (!token.equals("C1")) {
      throw new IllegalArgumentException("Invalid Collage file: plain RAW file should begin with" +
              " C1");
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    List<Image> layers = new ArrayList<>();
    List<String> layerNames = new ArrayList<>();
    List<String> filterNames = new ArrayList<>();

    while (sc.hasNext()) {

      Pixel[][] pixels = new Pixel[height][width];

      layerNames.add(sc.next());
      filterNames.add(sc.next());

      for (int i = 0; i < height; i++) {

        for (int j = 0; j < width; j++) {

          int r = sc.nextInt();
          int g = sc.nextInt();
          int b = sc.nextInt();
          int a = sc.nextInt();
          pixels[i][j] = new RGBAPixel(r, g, b, a, maxValue);
        }
      }

      layers.add(new RGBALayerImage(pixels));
    }

    createProject(height, width);

    for (int i = 0; i < layers.size(); i++) {
      addLayer(layerNames.get(i));
      setFilter(layerNames.get(i), filterNames.get(i));
      addImage(layerNames.get(i), layers.get(i), 0, 0);
    }

    sc.close();
  }

  @Override
  public void setFilter(String layerName, String filterName)
          throws IllegalArgumentException, IllegalStateException {
    if (this.height == 0 || this.width == 0) {
      throw new IllegalStateException("Cannot set a filter to a project that does not exist");
    }

    if (layerName == null) {
      throw new IllegalArgumentException("Layer name cannot be null");
    }

    if (filterName == null) {
      throw new IllegalArgumentException("Filter name cannot be null");
    }

    boolean updated = false;

    for (int i = 0; i < this.layers.size(); i++) {

      if (this.layers.get(i).getLayerName().equals(layerName)) {

        this.layers.get(i).updateFilter(filterName);
        updated = true;
        break;
      }
    }

    if (!updated) {
      throw new IllegalArgumentException("No layer with this name exists");
    }
  }

  @Override
  public void addImage(String layerName, String fileContents, int startY, int startX)
          throws IllegalArgumentException, IllegalStateException {

    if (this.height == 0 || this.width == 0) {
      throw new IllegalStateException("Cannot add an image to a project that does not exist");
    }

    if (fileContents == null) {
      throw new IllegalArgumentException("Filepath cannot be null");
    }

    if (layerName == null) {
      throw new IllegalArgumentException("Layer name cannot be null");
    }

    if (startY < 0 || startX < 0) {
      throw new IllegalArgumentException("Invalid coordinates were given");
    }

    Scanner sc = new Scanner(fileContents);

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    Pixel[][] pixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {

      for (int j = 0; j < width; j++) {

        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        pixels[i][j] = new RGBAPixel(r, g, b, maxValue);
      }
    }

    sc.close();

    addImage(layerName, new RGBALayerImage(pixels), startY, startX);
  }

  @Override
  public void addImage(String layerName, Image img, int startY, int startX)
          throws IllegalArgumentException, IllegalStateException {
    if (this.height == 0 || this.width == 0) {
      throw new IllegalStateException("Cannot add an image to a project that does not exist");
    }

    if (layerName == null) {
      throw new IllegalArgumentException("The layer name cannot be null");
    }

    if (img == null) {
      throw new IllegalArgumentException("The image cannot be null");
    }

    if (startY < 0 || startX < 0) {
      throw new IllegalArgumentException("Invalid coordinates were given");
    }

    boolean updated = false;

    for (int i = 0; i < this.layers.size(); i++) {

      if (this.layers.get(i).getLayerName().equals(layerName)) {
        this.layers.set(i, this.layers.get(i).combine(img, startY, startX));
        updated = true;
        break;
      }
    }

    if (!updated) {
      throw new IllegalArgumentException("No layer with this name exists");
    }
  }

  @Override
  public void addImage(String layerName, BufferedImage img, int startY, int startX)
          throws IllegalArgumentException, IllegalStateException {

    if (img == null) {
      throw new IllegalArgumentException("The image cannot be null");
    }

    Pixel[][] convertedPixels = new Pixel[img.getHeight()][img.getWidth()];

    for (int i = 0; i < img.getHeight(); i++) {

      for (int j = 0; j < img.getWidth(); j++) {

        int rgba = img.getRGB(j, i);

        int a = rgba >> 24 & 0xff;
        int r = rgba >> 16 & 0xff;
        int g = rgba >> 8 & 0xff;
        int b = rgba & 0xff;

        convertedPixels[i][j] = new RGBAPixel(r, g, b, a);
      }
    }

    addImage(layerName, new RGBALayerImage(convertedPixels), startY, startX);
  }

  @Override
  public List<String> getLayerNames() throws IllegalStateException {
    if (this.height == 0 || this.width == 0) {
      throw new IllegalStateException("There is no project");
    }

    if (layers.size() == 0) {
      throw new IllegalStateException("There are no layers");
    }

    List<String> layerNames = new ArrayList<>();

    for (Layer layer : this.layers) {

      layerNames.add(layer.getLayerName());
    }

    return layerNames;
  }

  @Override
  public Image getFinalImage() throws IllegalStateException {
    return makeImageAt(this.layers.size() - 1);
  }

  @Override
  public Image getImageAtLayer(String layername) throws IllegalStateException,
          IllegalArgumentException {

    if (height == 0 || width == 0) {
      throw new IllegalStateException("Cannot get an image when there is no project");
    }

    if (layers.size() == 0) {
      throw new IllegalStateException("Cannot get an image when there are no layers");
    }

    if (layername == null) {
      throw new IllegalArgumentException("layer name cannot be null");
    }

    int index = -1;

    for (int i = 0; i < this.layers.size(); i++) {

      if (this.layers.get(i).getLayerName().equals(layername)) {
        index = i;
        break;
      }
    }

    if (index == -1) {
      throw new IllegalArgumentException("Layer does not exist");
    }

    return makeImageAt(index);
  }

  @Override
  public int getHeight() throws IllegalStateException {
    if (height == 0) {
      throw new IllegalStateException("No project exists");
    }

    return this.height;
  }

  @Override
  public int getWidth() throws IllegalStateException {
    if (width == 0) {
      throw new IllegalStateException("No project exists");
    }

    return this.width;
  }

  @Override
  public List<Layer> getLayers() throws IllegalStateException {
    if (height == 0 | width == 0) {
      throw new IllegalStateException("No project exists");
    }

    return List.copyOf(this.layers);
  }

  private Image makeImageAt(int layerIndex) {
    if (this.height == 0 || this.width == 0) {
      throw new IllegalStateException("Cannot save an image to a project that does not exist");
    }

    if (this.layers.size() == 0) {
      throw new IllegalStateException("No image to save");
    }

    Layer result = layers.get(0).applyFilter();

    for (int i = 1; i < layerIndex + 1; i++) {

      try {
        result = this.layers.get(i).applyTwoFilter(result);
      } catch (IllegalStateException e) {
        // Do nothing, filter is not a TwoFilter
      }

      result = result.combine(this.layers.get(i).applyFilter(), 0, 0);
    }

    return result.getImage();
  }

  /**
   * Converts an image of this implementation to a Java {@code BufferedImage} type.
   *
   * @param img is the image to convert.
   * @param bufferedImageType is the type of {@code BufferedImage} to create. Only works with
   *                          {@code BufferedImage.TYPE_INT_ARGB} and
   *                          {@code BufferedImage.TYPE_INT_RGB}.
   * @return is the resulting Java Image type object.
   * @throws IllegalArgumentException if bufferedImageType is not
   *                                  {@code BufferedImage.TYPE_INT_ARGB} or
   *                                  {@code BufferedImage.TYPE_INT_RGB}.
   */
  @Override
  public BufferedImage createJavaImage(Image img, int bufferedImageType) {
    if (bufferedImageType != BufferedImage.TYPE_INT_ARGB &&
            bufferedImageType != BufferedImage.TYPE_INT_RGB) {
      throw new IllegalArgumentException("Only supports BufferedImage.TYPE_INT_ARGB and " +
              "BufferedImage.TYPE_INT_RGB image types.");
    }

    if (img == null) {
      throw new IllegalArgumentException("image cannot be null");
    }

    BufferedImage image = new BufferedImage(img.getWidth(), img.getHeight(),
            bufferedImageType);

    Pixel[][] imgPixels = img.getPixels();

    for (int x = 0; x < image.getWidth(); x++) {

      for (int y = 0; y < image.getHeight(); y++) {

        int[] rgba;

        if (bufferedImageType == BufferedImage.TYPE_INT_ARGB) {
          rgba = imgPixels[y][x].asRGBA();
        } else {
          rgba = imgPixels[y][x].asRGB();
        }

        int r = rgba[0];
        int g = rgba[1];
        int b = rgba[2];

        int argb;

        if (bufferedImageType == BufferedImage.TYPE_INT_ARGB) {
          int a = rgba[3];
          argb = a << 24;
          argb |= r << 16;
        } else {
          argb = r << 16;
        }

        argb |= g << 8;
        argb |= b;
        image.setRGB(x, y, argb);
      }
    }

    return image;
  }

  @Override
  public String[] getFilterNames() {
    FilterName[] filters = FilterName.values();
    String[] filterNames = new String[filters.length];

    for (int i = 0; i < filters.length; i++) {

      filterNames[i] = filters[i].getName();
    }

    return filterNames;
  }
}
