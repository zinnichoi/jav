package courseman2.controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

import courseman2.DomainConstraint;
import courseman2.NotPossibleException;

/**
 * @author dmle
 * @overview Represents the abstract super-type of all the GUI-based controllers.
 * It is sub-type of <tt>ActionListener</tt>, which requires that the
 * sub-classes handle the user actions on the GUI.
 * @attributes gui       JFrame
 * title     String
 * titleText String
 * dim       Dimension
 * location  Point
 * @abstract_properties <pre>
 *  optional(title)=false /\ optional(titleText) = false /\ optional(dim) = false /\
 *  optional(location) = false /\
 *  option(gui) = false /\
 *  gui.title = title /\ gui.size = dim /\ gui.location = location /\
 *  (A):
 *  gui.menuBar.components = [ menu<"File"> ] /\
 *  gui.contentPane.components =
 *    [ top:panel<>, middle:panel<>, bottom:panel<> ] /\
 *  top.components= [ label<titleText> ]
 *  size(middle.components) > 0 /\
 *  bottom.components = [ ok:button<"OK">, cancel:button<"Cancel"> ] /\
 *  ok.actionListener = this /\ cancel.actionListener = this
 *  </pre>
 * <p>
 * <p>i.e.
 * <br>(A):
 * <br><tt>gui</tt>'s menu bar contains one <tt>"File"</tt> menu /\
 * <br><tt>gui</tt> has three panel components named <tt>top</tt>, <tt>middle</tt>, and <tt>bottom</tt> /\
 * <br><tt>top</tt> panel has a label whose content is <tt>titleText</tt> /\
 * <br><tt>middle</tt> panel has at least one component /\
 * <br><tt>bottom<tt> panel has two buttons "OK" (named <tt>ok</tt>) and "Cancel" (named <tt>cancel</tt>) /\
 * <br>action listener of buttons <tt>ok</tt> and <tt>cancel</tt> are <tt>this</tt>
 */
public abstract class AppController implements ActionListener {
    /**
     * the data management GUI window
     */
    @DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
    protected JFrame gui;

    @DomainConstraint(type = DomainConstraint.Type.String, optional = false, mutable = false)
    private String title;

    @DomainConstraint(type = DomainConstraint.Type.String, optional = false, mutable = false)
    private String titleText;

    @DomainConstraint(type = DomainConstraint.Type.Object, optional = false, mutable = false)
    private Dimension dim;

    @DomainConstraint(type = DomainConstraint.Type.Object, optional = false, mutable = false)
    private Point location;

    // constructor

    /**
     * @effects initialise this with a <tt>gui</tt> using the specified settings
     */
    public AppController(String title, String titleText, int width, int height, int x, int y) {
        this.title = title;
        this.titleText = titleText;
        dim = new Dimension(width, height);
        location = new Point(x, y);
        createGUI();
    }

    /**
     * @modifies this
     * @effects {@link #initGUI()}: create <tt>gui</tt> to be a JFrame-typed window that has:
     * <p>
     * <ol>
     * <li>{@link #createFileMenu()}: a File menu with a single Exit menu item.
     * <li>{@link #createTopPanel()}: a top panel for displaying title text,
     * <li>{@link #createMiddlePanel()}: a (middle) data panel for displaying GUI-related components specific
     * for the current application function,
     * <li>{@link #createBottomPanel()}: a (bottom) button panel for displaying two command buttons: OK
     * and Cancel, and
     * </ol>
     */
    private void createGUI() {
        // initialise gui
        initGUI();

        // a File menu with a single Exit menu item.
        createFileMenu();

        // top panel
        createTopPanel();

        // middle panel
        createMiddlePanel();

        // bottom panel
        createBottomPanel();
    }

    /**
     * @requires gui != null
     * @modifies this
     * @effects initialise <tt>gui</tt> to be a JFrame-typed window whose title is <tt>title</tt>,
     * size is <tt>dim</tt> and location is <tt>location</tt>.
     */
    private void initGUI() {
        // initial gui
        gui = new JFrame(title);
        //set size
        gui.setSize(dim);
        // set location
        gui.setLocation(location);
        // set layout
        gui.setLayout(new BorderLayout());
    }

    /**
     * @requires gui != null
     * @modifies this
     * @effects create a File menu with a single Exit item, which hides <tt>gui</tt>.
     * <p>
     * <p>The action listener of this item is <tt>this</tt>.
     */
    private void createFileMenu() {
        // create menu bar
        JMenuBar menuBar = new JMenuBar();
        // create File menu
        JMenu fileMenu = new JMenu("File");
        // create Exit item
        JMenuItem exitItem = new JMenuItem("Exit");
        // add actionListiner
        exitItem.addActionListener(this);
        // add item to menu and add menu to menubar
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        //set menuber for gui
        gui.setJMenuBar(menuBar);
    }

    /**
     * @requires gui != null
     * @modifies this
     * @effects a top panel of <tt>gui</tt> for displaying <tt>titleText</tt>
     */
    private void createTopPanel() {
        // create top panel
        JPanel topPanel = new JPanel();
        //set flowLayout in center
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        // create label with title
        JLabel label = new JLabel(titleText);
        // add label to panel
        topPanel.add(label);
        //set back ground color
        topPanel.setForeground(Color.BLUE);
        // add panel to the north gui
        this.gui.add(topPanel, BorderLayout.NORTH);
    }

    /**
     * @requires gui != null
     * @modifies this
     * @effects create a middle panel of <tt>gui</tt> for displaying GUI-related components specific
     * for the current application function
     */
    protected abstract void createMiddlePanel();

    /**
     * @requires gui != null
     * @modifies this
     * @effects create a (bottom) button panel of <tt>gui</tt> for displaying two command buttons: OK
     * and Cancel.
     * <p>
     * <p>The OK button performs the application function using the data entered on
     * <tt>gui</tt>. The Cancel button reset the data panel by clearing the GUI components.
     * <p>
     * <p>The action listeners of these buttons are <tt>this</tt>.
     */
    private void createBottomPanel() {
        // create panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 2));
        // create ok button
        JButton okButton = new JButton("OK");
        okButton.addActionListener(this);
        // create cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        // add two button to bottom panel
        bottomPanel.add(okButton);
        bottomPanel.add(cancelButton);
        // add panel to gui
        gui.add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * @requires <tt>gui != null</tt>
     * @effects show <tt>gui</tt>
     */
    public void display() {
        gui.setVisible(true);
    }

    /**
     * @effects return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @requires <tt>gui != null</tt>
     * @effects display on <tt>gui</tt> an informational message dialog whose
     * title is <tt>title</tt>,
     * and whose message is <tt>mesg</tt>
     */
    protected void displayMessage(String mesg, String title) {
        JOptionPane.showMessageDialog(gui, mesg, title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * @requires <tt>gui != null</tt>
     * @effects display on <tt>gui</tt> an error message dialog whose title is
     * <tt>title</tt>,
     * and whose message is <tt>mesg</tt>
     */
    protected void displayErrorMessage(String mesg, String title) {
        JOptionPane.showMessageDialog(gui, mesg, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * @effects handles user action performed on <tt>gui</tt>:
     * <p>
     * <pre>
     *      if e.commandAction is File/Exit
     *        {@link #exit()}
     *      else if e.commandAction is OK
     *        {@link #doTask()}
     *        if exception is thrown
     *          {@link #displayErrorMessage} : display the exception message on the GUI
     *
     *      else if e.commandAction is Cancel
     *        {@link #clearGUI()}</pre>
     */
    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();

        if (cmd.equals("Exit")) {
            exit();
        } else if (cmd.equals("OK")) {
            try {
                doTask();
            } catch (NotPossibleException s) {
                displayErrorMessage(s.getMessage(), this.getTitle());
            }
        } else if (cmd.equals("Cancel")) {
            // clear the GUI to enter again
            clearGUI();
        }// add others here...
    }

    /**
     * @effects perform the current application function using the data in the data panel of
     * <tt>gui</tt> as input
     * <p>
     * <p>Throws NotPossibleException if failed to perform the function.
     */
    public abstract void doTask() throws NotPossibleException;

    /**
     * @effects clear the GUI components on <tt>gui</tt>
     */
    public abstract void clearGUI();

    /**
     * A helper method for {@link #clearGUI()}.
     *
     * @effects recursively clear the enabled text fields in <tt>panel</tt> and in all of its sub-panels.
     */
    protected void clearGUI(JPanel panel) {
        Component[] comps = panel.getComponents();
        Component comp;
        JTextField tf;

        for (int i = 0; i < comps.length; i++) {
            comp = comps[i];
            if (comp instanceof JTextField) {
                // found a text field
                tf = (JTextField) comp;

                // only clear the enabled text fields
                if (tf.isEnabled())
                    tf.setText("");
            } else if (comp instanceof JPanel) {
                clearGUI((JPanel) comp);
            }
        }
    }

    /**
     * @modifies this
     * @effects initialise any application resources associated to the attributes of <tt>this</tt>.
     * <p>
     * <pre>if succeeds
     *            display a console message
     *          else
     *            display a console error message</pre>
     */
    public abstract void startUp();

    /**
     * @requires <tt>gui != null</tt>
     * @effects {@link #clearGUI()}: reset <tt>gui</tt>
     * hide <tt>gui</tt>
     */
    public void exit() {
        clearGUI();
        gui.setVisible(false);
    }

    /**
     * @requires <tt>gui != null</tt>
     * @modifies this
     * @effects dispose <tt>gui</tt> and clear any resources associated to attributes of <tt>this</tt>
     */
    public void shutDown() {
        gui.dispose();
    }

}
