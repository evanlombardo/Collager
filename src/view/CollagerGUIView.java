package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.CollageController;

/**
 * Defines a view for the collager program that uses a GUI. This view will not display until a
 * controller is given to it. This view processes GUI information by being a {@code JFrame},
 * {@code ActionListener}, and {@code ListSelectionListener}. When these changes need to happen,
 * they are forwarded to the model through the controller.
 */
public class CollagerGUIView extends JFrame implements GUIView, ActionListener,
        ListSelectionListener {

  private final JLabel image;
  private CollageController controller;
  private final JList<String> listOfStrings;
  private final DefaultListModel<String> dataForListOfStrings;

  /**
   * Creates the Java Swing elements that make up the GUI. Also creates the view that the
   * controller can use to output messages to the user.
   */
  public CollagerGUIView() {

    this.setMinimumSize(new Dimension(300, 300));
    this.setMaximumSize(new Dimension(1200, 800));
    this.setSize(new Dimension(600, 400));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Collager");

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    this.add(mainScrollPane);

    this.image = new JLabel();
    mainPanel.add(this.image);

    JPanel selectionContainPanel = new JPanel();
    selectionContainPanel.setLayout(new GridLayout(2, 1));
    selectionContainPanel.add(new JLabel("Layers"));

    JPanel selectionListPanel = new JPanel();
    selectionListPanel.setLayout(new BoxLayout(selectionListPanel, BoxLayout.X_AXIS));
    selectionContainPanel.add(selectionListPanel);
    mainPanel.add(selectionContainPanel);

    dataForListOfStrings = new DefaultListModel<>();
    listOfStrings = new JList<>(dataForListOfStrings);
    listOfStrings.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listOfStrings.addListSelectionListener(this);
    selectionListPanel.add(listOfStrings);

    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(dialogBoxesPanel);

    JPanel createProjectPanel = new JPanel();
    createProjectPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(createProjectPanel);
    JButton createProjectButton = new JButton("Create a project");
    createProjectButton.setActionCommand("Create project");
    createProjectButton.addActionListener(this);
    createProjectPanel.add(createProjectButton);

    JPanel addLayerPanel = new JPanel();
    addLayerPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(addLayerPanel);
    JButton addLayerButton = new JButton("Add a layer");
    addLayerButton.setActionCommand("Add layer");
    addLayerButton.addActionListener(this);
    addLayerPanel.add(addLayerButton);

    JPanel setFilterPanel = new JPanel();
    setFilterPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(setFilterPanel);
    JButton setFilterButton = new JButton("Set the filter of a layer");
    setFilterButton.setActionCommand("Set filter");
    setFilterButton.addActionListener(this);
    setFilterPanel.add(setFilterButton);

    JPanel fileOpenProjectPanel = new JPanel();
    fileOpenProjectPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileOpenProjectPanel);
    JButton fileOpenProjectButton = new JButton("Open a project");
    fileOpenProjectButton.setActionCommand("Open project");
    fileOpenProjectButton.addActionListener(this);
    fileOpenProjectPanel.add(fileOpenProjectButton);

    JPanel fileSaveProjectPanel = new JPanel();
    fileSaveProjectPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileSaveProjectPanel);
    JButton fileSaveProjectButton = new JButton("Save a project");
    fileSaveProjectButton.setActionCommand("Save project");
    fileSaveProjectButton.addActionListener(this);
    fileSaveProjectPanel.add(fileSaveProjectButton);

    JPanel fileOpenImagePanel = new JPanel();
    fileOpenImagePanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileOpenImagePanel);
    JButton fileOpenImageButton = new JButton("Open an image");
    fileOpenImageButton.setActionCommand("Open image");
    fileOpenImageButton.addActionListener(this);
    fileOpenImagePanel.add(fileOpenImageButton);

    JPanel fileSaveImagePanel = new JPanel();
    fileSaveImagePanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileSaveImagePanel);
    JButton fileSaveImageButton = new JButton("Save an image");
    fileSaveImageButton.setActionCommand("Save image");
    fileSaveImageButton.addActionListener(this);
    fileSaveImagePanel.add(fileSaveImageButton);

    this.pack();
  }

  @Override
  public void renderMessage(String message) throws IllegalArgumentException {
    if (message == null) {
      throw new IllegalArgumentException("Message cannot be null");
    }

    JOptionPane.showMessageDialog(CollagerGUIView.this, message,
            "Message", JOptionPane.PLAIN_MESSAGE);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    switch (e.getActionCommand()) {
      case "Create project": {
        int width;
        int height;

        String widthString = JOptionPane.showInputDialog("Please enter the  width of the " +
                "project's images (in pixels)");
        if (widthString == null) {
          return;
        }

        String heightString = JOptionPane.showInputDialog("Please enter the height of the " +
                "project's images (in pixels)");
        if (heightString == null) {
          return;
        }

        try {
          width = Integer.parseInt(widthString);
          height = Integer.parseInt(heightString);
        } catch (NumberFormatException e1) {
          renderMessage("Width/height must be integers");
          return;
        }

        try {
          controller.createProject(height, width);
          dataForListOfStrings.clear();
          listOfStrings.clearSelection();
        } catch (IllegalArgumentException e1) {
          renderMessage(e1.getMessage());
          return;
        }
      }
      break;
      case "Add layer": {
        String name = JOptionPane.showInputDialog("Please enter the name of the layer to add");

        if (name == null) {
          return;
        }

        try {
          try {
            controller.addLayer(name);
            dataForListOfStrings.addElement(name);
            listOfStrings.setSelectedIndex(dataForListOfStrings.size() - 1);
          } catch (IllegalStateException e1) {
            renderMessage(e1.getMessage());
            return;
          }
        } catch (IllegalArgumentException e1) {
          renderMessage(e1.getMessage());
          return;
        }
      }
      break;
      case "Set filter": {
        java.util.List<String> layerNames;

        try {
          layerNames = controller.getLayerNames();
        } catch (IllegalStateException e1) {
          renderMessage(e1.getMessage());
          return;
        }

        String[] options = new String[layerNames.size()];
        layerNames.toArray(options);
        String retvalue = (String) JOptionPane.showInputDialog(this, "Please choose layer " +
                        "to change filter at", "Layers", JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        if (retvalue == null) {
          return;
        }

        String selectedLayer = retvalue;

        String[] filterNames = controller.getFilterNames();

        retvalue = (String) JOptionPane.showInputDialog(this, "Please choose filter " +
                        "to change to", "Filters", JOptionPane.INFORMATION_MESSAGE,
                null, filterNames, filterNames[0]);

        if (retvalue == null) {
          return;
        }

        String selectedFilter = retvalue;

        try {
          controller.setFilter(selectedLayer, selectedFilter);
        } catch (IllegalStateException e1) {
          renderMessage(e1.getMessage());
          return;
        }
      }
      break;
      case "Open project": {
        final JFileChooser fchooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Collager Project Files", "collage");
        fchooser.setFileFilter(filter);
        int retvalue = fchooser.showOpenDialog(this);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          try {
            this.controller.loadProject(f.getAbsolutePath());

            List<String> layerNames = new ArrayList<>();

            try {
              layerNames.addAll(controller.getLayerNames());
            } catch (IllegalStateException allowed) {
              // Do nothing
            }

            dataForListOfStrings.clear();
            dataForListOfStrings.addAll(layerNames);
            listOfStrings.setSelectedIndex(layerNames.size() - 1);
          } catch (IllegalArgumentException e1) {
            renderMessage(e1.getMessage());
            return;
          }
        } else {
          return;
        }
      }
      break;
      case "Save project": {
        final JFileChooser fchooser = new JFileChooser(".");
        int retvalue = fchooser.showSaveDialog(this);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          try {
            this.controller.saveProject(f.getAbsolutePath());
          } catch (IllegalStateException e1) {
            renderMessage(e1.getMessage());
            return;
          }
        } else {
          return;
        }
      }
      break;
      case "Open image": {
        List<String> layerNames;

        try {
          layerNames = controller.getLayerNames();
        } catch (IllegalStateException e1) {
          renderMessage(e1.getMessage());
          return;
        }

        final JFileChooser fchooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "PPM, PNG, and JPEG Image Files", "ppm", "jpg", "jpeg", "png");
        fchooser.setFileFilter(filter);
        int retvalue = fchooser.showOpenDialog(this);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();

          String[] options = new String[layerNames.size()];
          layerNames.toArray(options);
          String retValue = (String) JOptionPane.showInputDialog(this, "Please choose layer " +
                          "to add image to", "Layers", JOptionPane.INFORMATION_MESSAGE,
                  null, options, options[0]);

          if (retValue == null) {
            return;
          }

          int xPos;
          int yPos;

          String xPosString = JOptionPane.showInputDialog("Please enter the x-coordinate to " +
                  "place the image at (starting at 0)");
          if (xPosString == null) {
            return;
          }

          String yPosString = JOptionPane.showInputDialog("Please enter the y-coordinate to " +
                  "place the image at (starting at 0)");
          if (yPosString == null) {
            return;
          }

          try {
            xPos = Integer.parseInt(xPosString);
            yPos = Integer.parseInt(yPosString);
          } catch (NumberFormatException e1) {
            renderMessage("Coordinates must be integers");
            return;
          }

          try {
            try {
              controller.addImage(retValue, f.getAbsolutePath(), yPos, xPos);
            } catch (IllegalStateException e1) {
              renderMessage(e1.getMessage());
              return;
            }
          } catch (IllegalArgumentException e1) {
            renderMessage(e1.getMessage());
            return;
          }
        } else {
          return;
        }
      }
      break;
      case "Save image": {
        final JFileChooser fchooser = new JFileChooser(".");
        int retvalue = fchooser.showSaveDialog(this);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          try {
            this.controller.saveImage(f.getAbsolutePath(), listOfStrings.getSelectedValue());
          } catch (IllegalStateException e1) {
            this.renderMessage(e1.getMessage());
            return;
          }
        } else {
          return;
        }
      }
      break;
      default: {
        // Do nothing
      }
    }

    try {
      String currentLayer = listOfStrings.getSelectedValue();

      if (currentLayer == null) {
        image.setIcon(new ImageIcon(controller.getFinalImage()));
      } else {
        image.setIcon(new ImageIcon(controller.getImageAt(currentLayer)));
      }
      this.pack();
    } catch (IllegalStateException e1) {
      if (!e1.getMessage().equals("No image to save")) {
        throw e1;
      }

      image.setIcon(new ImageIcon());
    }
  }

  @Override
  public void addController(CollageController controller) {
    if (controller == null) {
      throw new IllegalArgumentException("Controller cannot be null");
    }

    if (this.controller == null) {
      this.controller = controller;
    }

    this.setVisible(true);
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {

    if (dataForListOfStrings.isEmpty()) {
      image.setIcon(new ImageIcon());
    } else if (listOfStrings.getSelectedValue() == null) {
      image.setIcon(new ImageIcon(controller.getFinalImage()));
    } else {
      image.setIcon(new ImageIcon(controller.getImageAt(listOfStrings.getSelectedValue())));
    }

  }
}
