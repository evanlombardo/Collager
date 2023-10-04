package controller;

import java.util.NoSuchElementException;
import java.util.Scanner;

import model.Collager;
import view.CollagerView;

/**
 * Defines a controller for the collager program that uses {@code Readable}s to take input. Made
 * for use with a text-based collager program.
 */
public class RGBACollageController extends RGBAController {

  private final Readable in;
  private final CollagerView view;

  /**
   * Creates the controller that runs the Collager program.
   *
   * @param in       is the input to read from.
   * @param model is the model that defines how the Collager program is run.
   * @param view     is the view to display the Collager program.
   * @throws IllegalArgumentException if the in, model, or view are null.
   */
  public RGBACollageController(Readable in, Collager model, CollagerView view)
          throws IllegalArgumentException {
    super(model);

    if (in == null) {
      throw new IllegalArgumentException("In cannot be null");
    }

    if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    }

    this.in = in;
    this.view = view;
  }

  @Override
  public void runCollage() throws IllegalStateException {

    Scanner sc = new Scanner(in);

    while (sc.hasNext()) {

      try {

        String filename;
        String layerName;

        switch (sc.next().toLowerCase()) {

          case "new-project":
            int height = 0;
            int width = 0;

            try {
              String widthString = sc.next();
              String heightString = sc.next();
              width = Integer.parseInt(widthString);
              height = Integer.parseInt(heightString);
            } catch (NumberFormatException e) {
              try {
                this.view.renderMessage("Invalid arguments for command \"new-project\". Type " +
                        "\"help\" to see command usage.\n");
              } catch (IllegalStateException e1) {
                throw new IllegalStateException("Unknown IOException: " + e1.getMessage());
              }
            }

            try {
              createProject(height, width);
            } catch (IllegalArgumentException e) {
              renderExceptionMessage(e);
            }

            break;
          case "load-project":
            filename = sc.next();

            try {
              loadProject(filename);
            } catch (IllegalArgumentException e) {
              renderExceptionMessage(e);
            }

            break;
          case "save-project":
            filename = sc.next();

            try {
              try {
                saveProject(filename);
              } catch (IllegalStateException e) {
                renderExceptionMessage(e);
              }
            } catch (IllegalArgumentException e) {
              renderExceptionMessage(e);
            }

            break;
          case "add-layer":
            layerName = sc.next();

            try {
              try {
                addLayer(layerName);
              } catch (IllegalStateException e) {
                renderExceptionMessage(e);
              }
            } catch (IllegalArgumentException e) {
              renderExceptionMessage(e);
            }

            break;
          case "add-image-to-layer":
            layerName = sc.next();
            filename = sc.next();
            int xPos = 0;
            int yPos = 0;

            try {
              String xPosString = sc.next();
              String yPosString = sc.next();
              xPos = Integer.parseInt(xPosString);
              yPos = Integer.parseInt(yPosString);
            } catch (NumberFormatException e) {
              try {
                this.view.renderMessage("Invalid arguments for command \"add-image-to-layer\". " +
                        "Type \"help\" to see command usage.\n");
              } catch (IllegalStateException e1) {
                throw new IllegalStateException("Unknown IOException: " + e1.getMessage());
              }
            }

            try {
              try {
                addImage(layerName, filename, yPos, xPos);
              } catch (IllegalStateException e) {
                renderExceptionMessage(e);
              }
            } catch (IllegalArgumentException e) {
              renderExceptionMessage(e);
            }

            break;
          case "set-filter":
            layerName = sc.next();
            String filterName = sc.next();

            try {
              try {
                setFilter(layerName, filterName);
              } catch (IllegalStateException e) {
                renderExceptionMessage(e);
              }
            } catch (IllegalArgumentException e) {
              renderExceptionMessage(e);
            }

            break;
          case "save-image":
            filename = sc.next();

            try {
              try {
                saveImage(filename);
              } catch (IllegalStateException e) {
                renderExceptionMessage(e);
              }
            } catch (IllegalArgumentException e) {
              renderExceptionMessage(e);
            }

            break;
          case "quit":
            try {
              this.view.renderMessage("Quit!\n");
            } catch (IllegalStateException e1) {
              throw new IllegalStateException("Unknown IOException: " + e1.getMessage());
            }
            return;
          case "help":
            String helpMessage = "new-project CANVAS-HEIGHT CANVAS-WIDTH -- creates a blank " +
                    "project with the provided dimensions\n" +
                    "    CANVAS-HEIGHT and CANVAS-WIDTH must be integers\n" +
                    "    there are no layers by default\n" +
                    "load-project PATH-TO-PROJECT-FILE -- loads a project from a .collage " +
                    "formatted file\n" +
                    "save-project PATH-TO-PROJECT-FILE -- saves the project in the .collage " +
                    "format to the specified file\n" +
                    "add-layer LAYER-NAME -- adds a layer to the project with the specified " +
                    "name\n" +
                    "add-image-to-layer LAYER-NAME PATH-TO-IMAGE-FILE X-POS Y-POS -- adds a PPM " +
                    "formatted image to the specified layer at the specified coordinates\n" +
                    "    X-POS and Y-POS must be integers\n" +
                    "set-filter LAYER-NAME FILTER-NAME -- sets the given layer to have the " +
                    "specified filter\n" +
                    "    available filters are:\n" +
                    "        normal -- no filter applied\n" +
                    "        red_filter -- only displays the red channels of the layer\n" +
                    "        green_filter -- only displays the green channels of the layer\n" +
                    "        blue_filter -- only displays the blue channels of the layer\n" +
                    "        brighten_intensity -- brightens the layer using the average " +
                    "intensity of pixels\n" +
                    "        darken_intensity -- darkens the layer using the average intensity " +
                    "of pixels\n" +
                    "        brighten_luma -- brightens the layer using the weighted luma " +
                    "value\n" +
                    "        darken_luma -- darkens the layer using the weighted luma value\n" +
                    "        brighten_max -- brightens the layer using the maximum intensity of " +
                    "the pixels\n" +
                    "        darken_max -- brightens the layer using the maximum intensity of " +
                    "the pixels\n" +
                    "        darken_multiply -- darkens the layer by changing its L value in " +
                    "its HSL form \n" +
                    "        brighten_screen -- brightens the layer by changing its L value in " +
                    "its HSL form\n" +
                    "        inversion_difference --inverts the colors of the image by using " +
                    "the different of RGB values of the composite image and top image \n" +
                    "save-image PATH-TO-IMAGE-FILE -- save the result of applying all filters " +
                    "on their respective layers, and combining those layers\n" +
                    "quit -- quits the program\n" +
                    "help -- displays this dialog\n";
            try {
              this.view.renderMessage(helpMessage);
            } catch (IllegalStateException e) {
              throw new IllegalStateException("Unknown IOException: " + e.getMessage());
            }

            break;
          default:
            try {
              this.view.renderMessage("Invalid command, type \"help\" for help.\n");
            } catch (IllegalStateException e) {
              throw new IllegalStateException("Unknown IOException: " + e.getMessage());
            }
        }

      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Invalid end of input");
      }
    }
  }

  private void renderExceptionMessage(Exception e) {
    try {
      this.view.renderMessage(e.getMessage() + "\n");
    } catch (IllegalStateException e1) {
      throw new IllegalStateException("Unknown IOException: " + e1.getMessage());
    }
  }
}
