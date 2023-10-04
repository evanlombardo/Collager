package helpers;


import java.awt.image.BufferedImage;
import java.util.List;

import model.Collager;
import model.Image;
import model.Layer;

/**
 * This mock class represents a Collager that finds all the transmissions via a log.
 */
public class ConfirmInputs implements Collager {

  private StringBuilder log;

  /**
   * This contructor creates the mock with a given log.
   *
   * @param log a string builder object that helps store the transmissions.
   */
  public ConfirmInputs(StringBuilder log) {

    this.log = log;

  }

  @Override
  public void createProject(int height, int width) throws IllegalArgumentException {


    this.log.append("A new project creation was attempted with "
            + ", height: " + height
            + ", and width: " + width + ".\n");
  }

  @Override
  public void addLayer(String name) throws IllegalArgumentException, IllegalStateException {
    this.log.append("A new layer addition was attempted. The layer's name was "
            + name + "\n");
  }

  @Override
  public void loadProject(String collagerContents) throws IllegalArgumentException {
    this.log.append("collagerContents:" + collagerContents);
  }

  @Override
  public void setFilter(String layerName, String filterName)
          throws IllegalArgumentException, IllegalStateException {
    this.log.append("A new filter setting was attempted. The layer's name was "
            + layerName + " and the filter name was: " + filterName + "\n");
  }

  @Override
  public void addImage(String layerName, String filepath, int startY, int startX)
          throws IllegalArgumentException, IllegalStateException {

    this.log.append("An image addition was attempted at " + startX + ", " +
            startY + ". The layer's name was " +
            layerName + ", and the file  path was: " + filepath + "\n");
  }

  @Override
  public void addImage(String layerName, Image img, int startY, int startX)
          throws IllegalArgumentException, IllegalStateException {
    this.log.append("An image addition was attempted at " + startX + ", " +
            startY + ". The layer's name was " +
            layerName + "\n");
  }

  @Override
  public void addImage(String layerName, BufferedImage img, int startY, int startX)
          throws IllegalArgumentException, IllegalStateException {
    log.append("layerName:" + layerName + " img:" + img + " startY:" + startY + " startX:" +
            startX);
  }

  @Override
  public List<String> getLayerNames() throws IllegalStateException {
    return null;
  }

  @Override
  public Image getFinalImage() throws IllegalStateException {
    return null;
  }

  @Override
  public Image getImageAtLayer(String layername)
          throws IllegalStateException, IllegalArgumentException {
    log.append("layername:" + layername);

    return null;
  }

  @Override
  public int getHeight() throws IllegalStateException {
    return 0;
  }

  @Override
  public int getWidth() throws IllegalStateException {
    return 0;
  }

  @Override
  public List<Layer> getLayers() throws IllegalStateException {
    return null;
  }

  @Override
  public BufferedImage createJavaImage(Image img, int bufferedImageType) {
    log.append("img:" + img + " bufferedImageType:" + bufferedImageType);

    return null;
  }

  @Override
  public String[] getFilterNames() {
    return new String[0];
  }

}
