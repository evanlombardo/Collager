import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

import controller.CollageController;
import controller.RGBACollageController;
import controller.RGBAGUICollageController;
import model.Collager;
import model.RGBACollager;
import view.CollagerGUIView;
import view.CollagerTextView;

/**
 * Implements the collager program by bringing together the controller, model, and view. Allows for
 * different modes of the collager program: text (where the user can input commands via console),
 * script (where a script file can be run), and GUI (where the user can interact with a GUI to use
 * the collager program).
 */
public final class CollageCreator {

  /**
   * Runs the program given some or no arguments.
   *
   * @param args are the command arguments given from the user when starting the program.
   *             Must be "-file PATH-TO-FILE-SCRIPT" to load a script file, "-text" to run
   *             the program using text commands in the console, and no arguments to run
   *             the program using the GUI.
   */
  public static void main(String[] args) {

    Collager collager = new RGBACollager();
    CollageController controller = null;

    if (args.length < 1) {
      controller = new RGBAGUICollageController(collager, new CollagerGUIView());

    } else if (args[0].equals("-text")) {
      controller = new RGBACollageController(new InputStreamReader(System.in), collager,
              new CollagerTextView());

    } else if (args.length > 1 && args[0].equals("-file")) {
      Readable in = null;

      try {
        in = new FileReader(args[1]);
      } catch (FileNotFoundException e) {
        System.err.println("Cannot find script to read");
        System.exit(1);
      }

      controller = new RGBACollageController(in, collager, new CollagerTextView());

    } else if (args[0].equals("--help")) {
      System.out.println("Use: \"-file PATH-OF-FILE-SCRIPT\" to run a script file\n" +
              "Use: \"-text\" to run the Collager in text mode\n" +
              "Use with no commands to run the Collager in GUI mode\n" +
              "Use: \"--help\" to pull up this menu");
      System.exit(0);

    } else {
      System.err.println("Invalid command, use \"--help\" to see options");
      System.exit(1);

    }

    controller.runCollage();

  }
}