package courseman2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import courseman2.controller.EnrolmentManager;
import courseman2.controller.ModuleManager;
import courseman2.controller.StudentManager;

/**
 * @author dmle
 * @overview Represents the main class of the CourseMan program.
 * @attributes initial       String
 * sman          StudentManager
 * mman          ModuleManager
 * emain         EnrolmentManager
 * lhelper       CourseManDemo.LogoHelper
 * mainGUI       JFrame
 * @abstract_properties <pre>
 *   optional(initial) = false /\
 *   optional(sman) = false /\
 *   optional(mman) = false /\
 *   optional(eman) = false /\
 *   optional(lhelper) = false /\
 *   optional(mainGUI) = false /\
 * </pre>
 */
public class CourseManDemo implements ActionListener {
    /**
     * the initial used as logo
     */
    @DomainConstraint(type = DomainConstraint.Type.String, optional = false)
    private String initial;

    /**
     * the student manager
     */
    @DomainConstraint(type = DomainConstraint.Type.UserDefined, optional = false)
    private StudentManager sman;

    /**
     * the module manager
     */
    @DomainConstraint(type = DomainConstraint.Type.UserDefined, optional = false)
    private ModuleManager mman;

    /**
     * the enrolment manager
     */
    @DomainConstraint(type = DomainConstraint.Type.UserDefined, optional = false)
    private EnrolmentManager eman;

    /**
     * the logo helper used for creating a visual effect on the logo
     */
    @DomainConstraint(type = DomainConstraint.Type.UserDefined, optional = false)
    private LogoHelper lhelper;

    /**
     * the main GUI window
     */
    @DomainConstraint(type = DomainConstraint.Type.Object, optional = false)
    private JFrame mainGUI;

    // constructor method

    /**
     * @effects initialise this with initial
     * {@link #createGUI()}: create mainGUI
     * initialise sman, mman, eman such that their (x,y) locations are each 50 pixels higher
     * those of mainGUI
     * initialise lhelper
     */
    public CourseManDemo(String initial) {
        this.initial = initial;
        createGUI();
        this.sman = new StudentManager("Student Manager", "Enter student infor", 500, 300, 100, 200);
        this.mman = new ModuleManager("Module Manager", "Enter module infor", 500, 300, 100, 200);
        this.eman = new EnrolmentManager("Enrolment Manager", "Enter enrolemnt infor", 500, 300, 100, 200, sman, mman);

    }

    /**
     * @modifies this
     * @effects create mainGUI that has a menu bar with:
     * <p>
     * File menu has two items: Save and Exit
     * Tools has three menu items for the three data management functions
     * Reports: has the two reporting functions
     * {@link #createLogoPanel(JMenuBar, String)}: a logo panel containing a
     * logo label at the far end of the menu bar
     * <p>
     * <p>
     * The action listener of the menu items is this.
     */
    protected void createGUI() {
        mainGUI = new JFrame();
        mainGUI.setTitle("CourseMan2");
        mainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGUI.setPreferredSize(new Dimension(400, 64));
        mainGUI.setLayout(new GridBagLayout());
        // init menu bar
        JMenuBar jmb = new JMenuBar();

        // create 3 menu for menubar
        JMenu file = new JMenu("File");
        JMenu tools = new JMenu("Tools");
        JMenu reports = new JMenu("Reports");
        // add them to the menubar
        jmb.add(file);
        jmb.add(tools);
        jmb.add(reports);

        // create option for file menu
        JMenuItem save = new JMenuItem("Save");
        save.setActionCommand("Save");
        save.addActionListener(this);
        JMenuItem exit = new JMenuItem("Exit");
        exit.setActionCommand("Exit");
        exit.addActionListener(this);
        // add file menuItem
        file.add(save);
        file.addSeparator();
        file.add(exit);

        // create option for tools and setActionCommand
        JMenuItem manageModules = new JMenuItem("Manage Modules");
        manageModules.setActionCommand("manMo");
        manageModules.addActionListener(this);
        JMenuItem manageStudents = new JMenuItem("Manage Students");
        manageStudents.setActionCommand("manStu");
        manageStudents.addActionListener(this);
        JMenuItem manageEnrolments = new JMenuItem("Manage Enrolments");
        manageEnrolments.setActionCommand("manEn");
        manageEnrolments.addActionListener(this);
        // add tools menuItem
        tools.add(manageModules);
        tools.add(manageStudents);
        tools.add(manageEnrolments);
        tools.addSeparator();
        // create option for reports
        JMenuItem initReport = new JMenuItem("Initial enrolment report");
        initReport.setActionCommand("initReport");
        initReport.addActionListener(this);
        JMenuItem assesReport = new JMenuItem("Assessment report");
        assesReport.setActionCommand("assReport");
        assesReport.addActionListener(this);
        // add reports menuItem
        reports.add(initReport);
        reports.add(assesReport);

        // create logo
        createLogoPanel(jmb, initial);
        // set JMenuBar
        mainGUI.setJMenuBar(jmb);
        mainGUI.pack();
        // init sman, mman, eman
        sman = new StudentManager("Student Manager", "Enter Student Details",
                400, 400, 50, 50);
        mman = new ModuleManager("Module Manager", "Enter Module Details",
                400, 400, 100, 100);
        eman = new EnrolmentManager("Enrolment Manager", "Enter Enrolment Details",
                400, 400, 150, 150, sman, mman);

    }

    /**
     * @effects create a label panel containing a decorated JLabel whose text is
     * initial. The decoration must use the following settings:
     * <p>
     * background colour: orange
     * foreground colour: blue
     * font: Serif, bold, 18 points
     * size: height=20, wide enough to fit the text
     * alignment: center
     * focusable: false
     * <p>
     * <p>
     * add the label panel to the menu bar mb so that it appears at the far end.
     * <p>
     * The logo text must have the "appearing" effect.
     */
    private void createLogoPanel(JMenuBar mb, String initial) {
        lhelper = new LogoHelper(initial);
        // add logo to the right of the menu bar
        mb.add(Box.createHorizontalGlue());
        mb.add(lhelper);
    }

    /**
     * @effects save data objects managed by sman, mman and eman to files
     */
    public void save() {
        sman.save();
        mman.save();
        eman.save();
    }

    /**
     * @effects start up sman, mman, eman
     * start lhelper
     */
    public void startUp() {
        sman.startUp();
        mman.startUp();
        eman.startUp();
    }

    /**
     * @effects shut down sman, mman, eman
     * dispose mainGUI and end the program.
     */
    public void shutDown() {
        sman.shutDown();
        mman.shutDown();
        eman.shutDown();
        mainGUI.dispatchEvent(new WindowEvent(mainGUI, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * @effects show mainGUI
     */
    public void display() {
        mainGUI.setVisible(true);
    }

    /**
     * @effects handles user actions on the menu items
     * <pre>
     *          if menu item is Tools/Manage students
     *            {@link #sman}.display()}
     *          else if menu item is Tools/Manage modules
     *            {@link #mman}.display()
     *          else if menu item is Tools/Manage enrolments
     *            {@link #eman}.display()
     *          else if menu item is Reports/Initial enrolment report
     *            {@link #eman}.report()
     *          else if menu item is Reports/Assessment report
     *            {@link #eman}.reportAssessment()
     *          else if menu item is File/Save
     *            {@link #save()}
     *          else if menu item is File/Exit
     *            {@link #shutDown()}
     *          </pre>
     */
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("manStu")) {
            sman.display();
        }
        if (cmd.equals("manMo")) {
            mman.display();
        }
        if (cmd.equals("manEn")) {
            eman.display();
        }
        if (cmd.equals("Save")) {
            save();
        }
        if (cmd.equals("Exit")) {
            shutDown();
        }
        if (cmd.equals("initReport")) {
            eman.report();
        }
        if (cmd.equals("assReport")) {
            eman.assessmentReport();
        }
    }

    /**
     * The run method
     *
     * @effects initialise an initial
     * create an instance of CourseManDemo from the initial
     * {@link #startUp()}: start up the CourseManDemo instance
     * {@link #display()}: display the main gui of CourseManDemo instance
     */
    public static void main(String[] args) {
        final String initial = "NMT-LPS";
        CourseManDemo app = new CourseManDemo(initial);

        app.startUp();
        app.display();
    }
}
