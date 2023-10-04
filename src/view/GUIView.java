package view;

import controller.CollageController;

/**
 * Defines a view for the collager program that displays as a GUI. Requires a controller to work.
 */
public interface GUIView extends CollagerView {

  /**
   * Adds a controller to the view to display the GUI.
   *
   * @param controller is the controller to add.
   */
  void addController(CollageController controller);
}
