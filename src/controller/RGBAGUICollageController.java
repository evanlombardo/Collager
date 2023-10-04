package controller;

import model.Collager;
import view.GUIView;

/**
 * Defines a controller for the collager program for a GUI. This controller uses a {@code GUIView}
 * and adds the controller (this object) to the view upon running the program.
 */
public class RGBAGUICollageController extends RGBAController implements CollageController {

  private final GUIView view;

  /**
   * Creates a controller object given the model and view.
   *
   * @param model is the model to use for the Collager program.
   * @param view  is the view to use to render the Collager program.
   * @throws IllegalArgumentException if the model or view is null.
   */
  public RGBAGUICollageController(Collager model, GUIView view)
          throws IllegalArgumentException {
    super(model);

    if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    }

    this.view = view;
  }

  @Override
  public void runCollage() throws IllegalStateException {
    view.addController(this);
  }

}
