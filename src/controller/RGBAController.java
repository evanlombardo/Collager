package controller;

import java.awt.image.BufferedImage;
import java.util.List;

import controller.utils.ImageUtil;
import model.Collager;
import model.Image;

/**
 * Defines an RGBAController that uses the {@code ImageUtil} class to do image and project file I/O.
 */
public abstract class RGBAController implements CollageController {

  protected final Collager model;

  /**
   * Creates a RGBAController given the model.
   *
   * @param model is the model to use.
   * @throws IllegalArgumentException if the model is null.
   */
  protected RGBAController(Collager model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("The model cannot be null");
    }

    this.model = model;
  }

  @Override
  public void saveProject(String filepath) throws IllegalArgumentException, IllegalStateException {

    try {
      this.model.getHeight();
      this.model.getWidth();
    } catch (IllegalStateException e) {
      throw new IllegalStateException("Cannot save a project that does not exist");
    }

    if (filepath == null) {
      throw new IllegalArgumentException("Filepath cannot be null");
    }

    ImageUtil.saveCollage(filepath, this.model.getHeight(), this.model.getWidth(),
            this.model.getLayers());
  }

  /**
   * Saves the image at the specified filepath. If the filepath ends with ".png" it is saved as a
   * PNG file, if the filepath ends with ".jpeg" or ".jpg" it is saved as a JPEG file, and
   * otherwise it is saved as a PPM file (case-sensitive).
   *
   * @param filename is the filepath to save the file at.
   * @throws IllegalArgumentException if the filename is null.
   * @throws IllegalStateException    if the user tries to save the image without a project.
   */
  @Override
  public void saveImage(String filename) throws IllegalArgumentException, IllegalStateException {

    Image finalImage = model.getFinalImage();

    if (filename == null) {
      throw new IllegalArgumentException("The file name cannot be null");
    }

    if (filename.endsWith(".png") || filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
      int type;

      if (filename.endsWith(".png")) {
        type = BufferedImage.TYPE_INT_ARGB;
      } else {
        type = BufferedImage.TYPE_INT_RGB;
      }

      ImageUtil.saveImage(filename, model.createJavaImage(finalImage, type));
      return;
    }

    ImageUtil.saveImage(filename, finalImage);
  }

  /**
   * Saves the image from the given layer at the specified filepath. If the filepath ends with
   * ".png" it is saved as a PNG file, if the filepath ends with ".jpeg" or ".jpg" it is saved as
   * a JPEG file, and otherwise it is saved as a PPM file.
   *
   * @param filename is the name of the file to save to.
   * @param layername is the layer name to save from.
   * @throws IllegalArgumentException if the layername or filename is null, or if the layer does
   *                                  not exist.
   * @throws IllegalStateException if the use tries to save without a project.
   */
  @Override
  public void saveImage(String filename, String layername)
          throws IllegalArgumentException, IllegalStateException {

    if (layername == null) {
      throw new IllegalArgumentException("Must select a layer to save");
    }

    Image finalImage = model.getImageAtLayer(layername);

    if (filename == null) {
      throw new IllegalArgumentException("The file name cannot be null");
    }

    if (filename.endsWith(".png") || filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
      int type;

      if (filename.endsWith(".png")) {
        type = BufferedImage.TYPE_INT_ARGB;
      } else {
        type = BufferedImage.TYPE_INT_RGB;
      }

      ImageUtil.saveImage(filename, model.createJavaImage(finalImage, type));
      return;
    }

    ImageUtil.saveImage(filename, finalImage);
  }

  @Override
  public void loadProject(String filepath) throws IllegalArgumentException {

    if (filepath == null) {
      throw new IllegalArgumentException("Filepath cannot be null");
    }

    ImageUtil.readCollage(filepath, model);
  }

  @Override
  public void addImage(String layername, String filename, int yPos, int xPos)
          throws IllegalArgumentException, IllegalStateException {
    if (filename == null) {
      throw new IllegalArgumentException("Filename cannot be null");
    }

    if (filename.endsWith(".png") || filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
      model.addImage(layername, ImageUtil.readJPEGPNG(filename), yPos, xPos);
      return;
    }

    model.addImage(layername, ImageUtil.readPPM(filename), yPos, xPos);
  }

  @Override
  public List<String> getLayerNames() throws IllegalArgumentException {
    return model.getLayerNames();
  }

  @Override
  public void setFilter(String layername, String filtername)
          throws IllegalArgumentException, IllegalStateException {
    model.setFilter(layername, filtername);
  }

  @Override
  public void addLayer(String name) throws IllegalArgumentException, IllegalStateException {
    model.addLayer(name);
  }

  @Override
  public void createProject(int height, int width) throws IllegalArgumentException {
    model.createProject(height, width);
  }

  @Override
  public BufferedImage getFinalImage() throws IllegalStateException {
    return model.createJavaImage(model.getFinalImage(), BufferedImage.TYPE_INT_ARGB);
  }

  @Override
  public BufferedImage getImageAt(String layername)
          throws IllegalStateException, IllegalArgumentException {
    return model.createJavaImage(model.getImageAtLayer(layername), BufferedImage.TYPE_INT_ARGB);
  }

  @Override
  public String[] getFilterNames() {
    return model.getFilterNames();
  }
}
