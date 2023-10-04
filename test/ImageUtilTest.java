import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import controller.utils.ImageUtil;
import model.Collager;
import model.Image;
import model.Layer;
import model.Pixel;
import model.RGBACollager;
import model.RGBALayer;
import model.RGBALayerImage;
import model.RGBAPixel;

import static model.filters.FilterName.NORMAL;
import static model.filters.FilterName.RED_FILTER;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Defines tests for the behavior of {@code ImageUtil}.
 */
public class ImageUtilTest {

  Collager collage1;
  Collager collage2;
  Collager collage3;

  Image image1;
  Image image2;

  Pixel[][] pixels;

  @Before
  public void init() {
    collage1 = new RGBACollager();
    collage2 = new RGBACollager(100, 100);
    collage3 = new RGBACollager(200, 200);
  }

  @Test
  public void testValidReadPPM() {

    image1 = new RGBALayerImage(3, 3);
    pixels = new RGBAPixel[][]{{new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(134, 122, 3, 5, 255)},
        {new RGBAPixel(0, 0, 0, 0, 255),
            new RGBAPixel(10, 220, 40, 65, 255)}};
    image2 = new RGBALayerImage(pixels);

    ImageUtil.saveImage("testLoadResult.ppm", image1);
    ImageUtil.readPPM("testLoadResult.ppm");
    ImageUtil.saveImage("testLoadResult2.ppm", image1);

    try {
      String fileContents = Files.readString(Path.of("testLoadResult2.ppm"));
      assertEquals("P3\n3 3\n255\n0 0 0 0 0 0 " +
              "0 0 0\n0 0 0 0 0 0 0 0 0\n" +
              "0 0 0 0 0 0 " +
              "0 0 0\n", fileContents);
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }

    ImageUtil.saveImage("testLoadResult.ppm", image2);
    ImageUtil.readPPM("testLoadResult.ppm");
    ImageUtil.saveImage("testLoadResult2.ppm", image1);

    try {
      String fileContents = Files.readString(Path.of("testLoadResult.ppm"));
      assertEquals("P3\n2 2\n255\n255 255 255 3 2 0\n0 0 0 3 56 10\n", fileContents);
      Files.delete(Path.of("testLoadResult.ppm"));
      Files.delete(Path.of("testLoadResult2.ppm"));
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }


  }

  @Test
  public void testInvalidReadPPM() {

    try {
      ImageUtil.readPPM(null);
      fail("Collage should not save if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      ImageUtil.readPPM("wedewbfweubf");
      fail("Collage should not save if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      ImageUtil.readPPM("testLoadSource.collage");
      fail("Collage should not save if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

  }

  @Test
  public void testValidReadCollage() {

    List<Layer> layers = new ArrayList<>();
    layers.add(new RGBALayer("name1", NORMAL, 3, 3));

    ImageUtil.saveCollage("testLoadResult.collage", 3, 3, layers);
    ImageUtil.readCollage("testLoadResult.collage", collage1);
    ImageUtil.saveCollage("testOther.collage", 3, 3, layers);

    try {
      String fileContents = Files.readString(Path.of("testOther.collage"));
      assertEquals("C1\n3 3\n255\nname1 normal\n255 255 255 0\n255 255 255 0\n" +
              "255 255 255 0\n255 255 255 0\n255 255 255 0\n255 255 255 0\n" +
              "255 255 255 0\n255 255 255 0\n" +
              "255 255 255 0\n", fileContents);
      Files.delete(Path.of("testOther.collage"));
      Files.delete(Path.of("testLoadResult.collage"));
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }

  }

  @Test
  public void testInvalidReadCollage() {

    try {
      ImageUtil.readCollage(null, collage1);
      fail("Collage should not save if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      ImageUtil.readCollage("wedewbfweubf", collage1);
      fail("Collage should not save if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      ImageUtil.readCollage("testLoadSource.ppm", collage1);
      fail("Collage should not save if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      ImageUtil.readCollage("testLoadSource.collage", null);
      fail("Collage should not save if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }
  }


  @Test
  public void testValidSaveCollage() {

    List<Layer> layers = new ArrayList<>();
    layers.add(new RGBALayer("name1", NORMAL, 3, 3));

    ImageUtil.saveCollage("testLoadResult.collage", 3, 3, layers);

    try {
      String fileContents = Files.readString(Path.of("testLoadResult.collage"));
      assertEquals("C1\n3 3\n255\nname1 normal\n255 255 255 0\n255 255 255 0\n" +
              "255 255 255 0\n255 255 255 0\n255 255 255 0\n255 255 255 0\n" +
              "255 255 255 0\n255 255 255 0\n" +
              "255 255 255 0\n", fileContents);
      Files.delete(Path.of("testLoadResult.collage"));
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }

  }

  @Test
  public void testInvalidSaveCollage() {

    collage1.createProject(2, 3);
    collage1.addLayer("test");

    List<Layer> layers = new ArrayList<>();
    layers.add(new RGBALayer("name1", NORMAL, 3, 3));
    layers.add(new RGBALayer("name2", RED_FILTER, 3, 3));

    try {
      ImageUtil.saveCollage("testLoadResult.collage", 3, 3, null);
      fail("Collage should not save if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }


    try {
      ImageUtil.saveCollage(null, 3, 3, layers);
      fail("Collage should not save if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      ImageUtil.saveCollage("testLoadResult.collage", 0, 3, layers);
      fail("Collage should not save if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      ImageUtil.saveCollage("testLoadResult.collage", -1, 3, layers);
      fail("Collage should not save if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      ImageUtil.saveCollage("testLoadResult.collage", 3, 0, layers);
      fail("Collage should not save if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      ImageUtil.saveCollage("testLoadResult.collage", 3, -1, layers);
      fail("Collage should not save if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      ImageUtil.saveCollage("testLoadResult.collage", -1, -1, layers);
      fail("Collage should not save if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      ImageUtil.saveCollage("webfrijwefbweourfbwerf", -1, -1, layers);
      fail("Collage should not save if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

  }

  @Test
  public void testValidSaveImage() {

    image1 = new RGBALayerImage(3, 3);
    pixels = new RGBAPixel[][]{{new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(134, 122, 3, 5, 255)},
        {new RGBAPixel(0, 0, 0, 0, 255),
            new RGBAPixel(10, 220, 40, 65, 255)}};
    image2 = new RGBALayerImage(pixels);

    ImageUtil.saveImage("testLoadResult.ppm", image1);

    try {
      String fileContents = Files.readString(Path.of("testLoadResult.ppm"));
      assertEquals("P3\n3 3\n255\n0 0 0 0 0 0 " +
              "0 0 0\n0 0 0 0 0 0 0 0 0\n" +
              "0 0 0 0 0 0 " +
              "0 0 0\n", fileContents);
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }

    ImageUtil.saveImage("testLoadResult.ppm", image2);

    try {
      String fileContents = Files.readString(Path.of("testLoadResult.ppm"));
      assertEquals("P3\n2 2\n255\n255 255 255 3 2 0\n0 0 0 3 56 10\n", fileContents);
      Files.delete(Path.of("testLoadResult.ppm"));
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }

    ImageUtil.saveImage("testLoadResult.png",
            collage2.createJavaImage(image1, BufferedImage.TYPE_INT_ARGB));


    BufferedImage loaded = ImageUtil.readJPEGPNG("testLoadResult.png");

    for (int i = 0; i < loaded.getHeight(); i++) {

      for (int j = 0; j < loaded.getWidth(); j++) {

        int rgba = loaded.getRGB(j, i);

        int[] actualRGBA = {rgba >> 16 & 0xff, rgba >> 8 & 0xff, rgba & 0xff, rgba >> 24 & 0xff};

        assertArrayEquals(new int[]{255, 255, 255, 0}, actualRGBA);
      }
    }

    ImageUtil.saveImage("testLoadResult.png", collage2.createJavaImage(image2,
            BufferedImage.TYPE_INT_ARGB));

    try {
      loaded = ImageUtil.readJPEGPNG("testLoadResult.png");
      Pixel[][] pixels2 = image2.getPixels();

      for (int i = 0; i < loaded.getHeight(); i++) {

        for (int j = 0; j < loaded.getWidth(); j++) {

          int rgba = loaded.getRGB(j, i);

          int[] actualRGBA = {rgba >> 16 & 0xff, rgba >> 8 & 0xff, rgba & 0xff, rgba >> 24 & 0xff};

          assertArrayEquals(pixels2[i][j].asRGBA(), actualRGBA);
        }
      }

      Files.delete(Path.of("testLoadResult.png"));
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }

    ImageUtil.saveImage("testLoadResult.jpg", collage2.createJavaImage(image1,
            BufferedImage.TYPE_INT_RGB));

    try {
      ImageIO.write(collage2.createJavaImage(image1, BufferedImage.TYPE_INT_RGB), "jpg",
              new File("testLoadResult.jpg"));
    } catch (IOException e) {
      throw new IllegalStateException("coc");
    }

    loaded = ImageUtil.readJPEGPNG("testLoadResult.jpg");

    for (int i = 0; i < loaded.getHeight(); i++) {

      for (int j = 0; j < loaded.getWidth(); j++) {

        int rgba = loaded.getRGB(j, i);

        int[] actualRGBA = {rgba >> 16 & 0xff, rgba >> 8 & 0xff, rgba & 0xff, rgba >> 24 & 0xff};

        assertArrayEquals(new int[]{0, 0, 0, 255}, actualRGBA);
      }
    }

    ImageUtil.saveImage("testLoadResult.jpg", collage2.createJavaImage(image2,
            BufferedImage.TYPE_INT_RGB));

    try {
      loaded = ImageUtil.readJPEGPNG("testLoadResult.jpg");
      Pixel[][] pixels2 = new RGBAPixel[][]{{new RGBAPixel(243, 255, 244, 255),
              new RGBAPixel(2, 19, 3, 255, 255)},
          {new RGBAPixel(0, 14, 0, 255, 255),
              new RGBAPixel(14, 31, 15, 255, 255)}};

      for (int i = 0; i < loaded.getHeight(); i++) {

        for (int j = 0; j < loaded.getWidth(); j++) {

          int rgba = loaded.getRGB(j, i);

          int[] actualRGBA = {rgba >> 16 & 0xff, rgba >> 8 & 0xff, rgba & 0xff, rgba >> 24 & 0xff};

          assertArrayEquals(pixels2[i][j].asRGBA(), actualRGBA);
        }
      }

      Files.delete(Path.of("testLoadResult.jpg"));
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }

    ImageUtil.saveImage("testLoadResult.jpeg", collage2.createJavaImage(image1,
            BufferedImage.TYPE_INT_RGB));


    loaded = ImageUtil.readJPEGPNG("testLoadResult.jpeg");

    for (int i = 0; i < loaded.getHeight(); i++) {

      for (int j = 0; j < loaded.getWidth(); j++) {

        int rgba = loaded.getRGB(j, i);

        int[] actualRGBA = {rgba >> 16 & 0xff, rgba >> 8 & 0xff, rgba & 0xff, rgba >> 24 & 0xff};

        assertArrayEquals(new int[]{0, 0, 0, 255}, actualRGBA);
      }
    }

    ImageUtil.saveImage("testLoadResult.jpeg", collage2.createJavaImage(image2,
            BufferedImage.TYPE_INT_RGB));

    try {
      loaded = ImageUtil.readJPEGPNG("testLoadResult.jpeg");
      Pixel[][] pixels2 = new RGBAPixel[][]{{new RGBAPixel(243, 255, 244, 255),
              new RGBAPixel(2, 19, 3, 255, 255)},
          {new RGBAPixel(0, 14, 0, 255, 255),
              new RGBAPixel(14, 31, 15, 255, 255)}};

      for (int i = 0; i < loaded.getHeight(); i++) {

        for (int j = 0; j < loaded.getWidth(); j++) {

          int rgba = loaded.getRGB(j, i);

          int[] actualRGBA = {rgba >> 16 & 0xff, rgba >> 8 & 0xff, rgba & 0xff, rgba >> 24 & 0xff};

          assertArrayEquals(pixels2[i][j].asRGBA(), actualRGBA);
        }
      }

      Files.delete(Path.of("testLoadResult.jpeg"));
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }
  }

  @Test
  public void testInvalidSaveImage() {

    image1 = new RGBALayerImage(3, 3);
    pixels = new RGBAPixel[][]{{new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(134, 122, 3, 5, 255)},
        {new RGBAPixel(0, 0, 0, 0, 255),
            new RGBAPixel(10, 220, 40, 65, 255)}};
    image2 = new RGBALayerImage(pixels);

    try {
      ImageUtil.saveImage("test.ppm", (Image) null);
      fail("Collage should not save if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      ImageUtil.saveImage("test.ppm", (BufferedImage) null);
      fail("Collage should not save if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      ImageUtil.saveImage(null, image1);
      fail("Collage should not save if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      ImageUtil.saveImage(null, image2);
      fail("Collage should not save if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      ImageUtil.saveImage(null, new BufferedImage(1, 1, 1));
      fail("Collage should not save if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }
  }

  @Test
  public void testInvalidReadJPEGPNG() {
    try {
      ImageUtil.readJPEGPNG("file.ppm");
      fail("Should not read file if not jpg, jpeg, or png");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      ImageUtil.readJPEGPNG(null);
      fail("Should not read file if filepath null");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      ImageUtil.readJPEGPNG("file.jpeg");
      fail("Should not read file if does not exist");
    } catch (IllegalStateException expected) {
      // Do nothing, test passed
    }

    try {
      ImageUtil.readJPEGPNG("file.png");
      fail("Should not read file if does not exist");
    } catch (IllegalStateException expected) {
      // Do nothing, test passed
    }

    try {
      ImageUtil.readJPEGPNG("file.jpg");
      fail("Should not read file if does not exist");
    } catch (IllegalStateException expected) {
      // Do nothing, test passed
    }
  }

  @Test
  public void testValidReadJPEGPNG() {
    image1 = new RGBALayerImage(3, 3);
    pixels = new RGBAPixel[][]{{new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(134, 122, 3, 5, 255)},
        {new RGBAPixel(0, 0, 0, 0, 255),
            new RGBAPixel(10, 220, 40, 65, 255)}};
    image2 = new RGBALayerImage(pixels);

    ImageUtil.saveImage("testLoadResult.png",
            collage2.createJavaImage(image1, BufferedImage.TYPE_INT_ARGB));


    BufferedImage loaded = ImageUtil.readJPEGPNG("testLoadResult.png");

    for (int i = 0; i < loaded.getHeight(); i++) {

      for (int j = 0; j < loaded.getWidth(); j++) {

        int rgba = loaded.getRGB(j, i);

        int[] actualRGBA = {rgba >> 16 & 0xff, rgba >> 8 & 0xff, rgba & 0xff, rgba >> 24 & 0xff};

        assertArrayEquals(new int[]{255, 255, 255, 0}, actualRGBA);
      }
    }

    ImageUtil.saveImage("testLoadResult.png", collage2.createJavaImage(image2,
            BufferedImage.TYPE_INT_ARGB));

    try {
      loaded = ImageUtil.readJPEGPNG("testLoadResult.png");
      Pixel[][] pixels2 = image2.getPixels();

      for (int i = 0; i < loaded.getHeight(); i++) {

        for (int j = 0; j < loaded.getWidth(); j++) {

          int rgba = loaded.getRGB(j, i);

          int[] actualRGBA = {rgba >> 16 & 0xff, rgba >> 8 & 0xff, rgba & 0xff, rgba >> 24 & 0xff};

          assertArrayEquals(pixels2[i][j].asRGBA(), actualRGBA);
        }
      }

      Files.delete(Path.of("testLoadResult.png"));
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }

    ImageUtil.saveImage("testLoadResult.jpg", collage2.createJavaImage(image1,
            BufferedImage.TYPE_INT_RGB));

    try {
      ImageIO.write(collage2.createJavaImage(image1, BufferedImage.TYPE_INT_RGB), "jpg",
              new File("testLoadResult.jpg"));
    } catch (IOException e) {
      throw new IllegalStateException("coc");
    }

    loaded = ImageUtil.readJPEGPNG("testLoadResult.jpg");

    for (int i = 0; i < loaded.getHeight(); i++) {

      for (int j = 0; j < loaded.getWidth(); j++) {

        int rgba = loaded.getRGB(j, i);

        int[] actualRGBA = {rgba >> 16 & 0xff, rgba >> 8 & 0xff, rgba & 0xff, rgba >> 24 & 0xff};

        assertArrayEquals(new int[]{0, 0, 0, 255}, actualRGBA);
      }
    }

    ImageUtil.saveImage("testLoadResult.jpg", collage2.createJavaImage(image2,
            BufferedImage.TYPE_INT_RGB));

    try {
      loaded = ImageUtil.readJPEGPNG("testLoadResult.jpg");
      Pixel[][] pixels2 = new RGBAPixel[][]{{new RGBAPixel(243, 255, 244, 255),
              new RGBAPixel(2, 19, 3, 255, 255)},
          {new RGBAPixel(0, 14, 0, 255, 255),
              new RGBAPixel(14, 31, 15, 255, 255)}};

      for (int i = 0; i < loaded.getHeight(); i++) {

        for (int j = 0; j < loaded.getWidth(); j++) {

          int rgba = loaded.getRGB(j, i);

          int[] actualRGBA = {rgba >> 16 & 0xff, rgba >> 8 & 0xff, rgba & 0xff, rgba >> 24 & 0xff};

          assertArrayEquals(pixels2[i][j].asRGBA(), actualRGBA);
        }
      }

      Files.delete(Path.of("testLoadResult.jpg"));
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }

    ImageUtil.saveImage("testLoadResult.jpeg", collage2.createJavaImage(image1,
            BufferedImage.TYPE_INT_RGB));


    loaded = ImageUtil.readJPEGPNG("testLoadResult.jpeg");

    for (int i = 0; i < loaded.getHeight(); i++) {

      for (int j = 0; j < loaded.getWidth(); j++) {

        int rgba = loaded.getRGB(j, i);

        int[] actualRGBA = {rgba >> 16 & 0xff, rgba >> 8 & 0xff, rgba & 0xff, rgba >> 24 & 0xff};

        assertArrayEquals(new int[]{0, 0, 0, 255}, actualRGBA);
      }
    }

    ImageUtil.saveImage("testLoadResult.jpeg", collage2.createJavaImage(image2,
            BufferedImage.TYPE_INT_RGB));

    try {
      loaded = ImageUtil.readJPEGPNG("testLoadResult.jpeg");
      Pixel[][] pixels2 = new RGBAPixel[][]{{new RGBAPixel(243, 255, 244, 255),
              new RGBAPixel(2, 19, 3, 255, 255)},
          {new RGBAPixel(0, 14, 0, 255, 255),
              new RGBAPixel(14, 31, 15, 255, 255)}};

      for (int i = 0; i < loaded.getHeight(); i++) {

        for (int j = 0; j < loaded.getWidth(); j++) {

          int rgba = loaded.getRGB(j, i);

          int[] actualRGBA = {rgba >> 16 & 0xff, rgba >> 8 & 0xff, rgba & 0xff, rgba >> 24 & 0xff};

          assertArrayEquals(pixels2[i][j].asRGBA(), actualRGBA);
        }
      }

      Files.delete(Path.of("testLoadResult.jpeg"));
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }
  }

}







