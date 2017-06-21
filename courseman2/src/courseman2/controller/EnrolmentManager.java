package courseman2.controller;

import courseman2.DomainConstraint;
import courseman2.NotPossibleException;
import courseman2.model.Enrolment;
import courseman2.model.Module;
import courseman2.model.Student;
import courseman2.view.EasyTable;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Vector;

/**
 * Created by nguyen manh tien 3c14 on 20/04/2017.
 */
public class EnrolmentManager extends Manager {
    @DomainConstraint(type = DomainConstraint.Type.Object, mutable = false, optional = false)
    private StudentManager studentManager;

    @DomainConstraint(type = DomainConstraint.Type.Object, mutable = false, optional = false)
    private ModuleManager moduleManager;

    @DomainConstraint(type = DomainConstraint.Type.Object, mutable = false, optional = false)
    private JPanel westPanel, centrePanel;

    @DomainConstraint(type = DomainConstraint.Type.Object, mutable = false, optional = false)
    private JTextField jTextFieldStudent, jTextFieldModule, jTextFieldInternalMark, jTextFieldExaminationMark;

    public EnrolmentManager(String title, String titleText, int width, int height, int x, int y, StudentManager studentManager, ModuleManager moduleManager) {
        super(title, titleText, width, height, x, y);
        this.studentManager = studentManager;
        this.moduleManager = moduleManager;
    }


    @Override
    protected void createMiddlePanel() {
        westPanel = new JPanel();
        centrePanel = new JPanel();

        westPanel.setLayout(new GridLayout(4, 1));
        westPanel.add(new JLabel("Student : "));
        westPanel.add(new JLabel("Module : "));
        westPanel.add(new JLabel("InternalMark : "));
        westPanel.add(new JLabel("ExaminationMark : "));

        centrePanel.setLayout(new GridLayout(4, 1));
        jTextFieldStudent = new JTextField(30);
        centrePanel.add(jTextFieldStudent);
        jTextFieldModule = new JTextField(30);
        centrePanel.add(jTextFieldModule);
        jTextFieldInternalMark = new JTextField(30);
        centrePanel.add(jTextFieldInternalMark);
        jTextFieldExaminationMark = new JTextField(30);
        centrePanel.add(jTextFieldExaminationMark);

        gui.add(westPanel, BorderLayout.WEST);
        gui.add(centrePanel, BorderLayout.CENTER);
    }

    @Override
    public void clearGUI() {
        clearGUI(centrePanel);
    }

    @Override
    public Object createObject() throws NotPossibleException {
        Student student = null;
        Module module = null;
        String studentId;
        String moduleCode;
        double internalMark = 0;
        double examinationMark = 0;

        studentId = jTextFieldStudent.getText();
        if (studentId.length() == 0) {
            displayErrorMessage("Invalid student id", this.getTitle());
            return null;
        }

        moduleCode = jTextFieldModule.getText();
        if (moduleCode.length() == 0) {
            displayErrorMessage("Invalid module code", this.getTitle());
            return null;
        }

        try {
            internalMark = Double.parseDouble(jTextFieldInternalMark.getText());
        } catch (Exception e) {
            displayErrorMessage("Invalid internal mark", this.getTitle());
            return null;
        }

        try {
            examinationMark = Double.parseDouble(jTextFieldExaminationMark.getText());
        } catch (Exception e) {
            displayErrorMessage("Invalid examination mark", this.getTitle());
            return null;
        }
        if (studentManager.getStudent(studentId) != null) {
            student = studentManager.getStudent(studentId);
        } else {
            displayErrorMessage("Student with id : '" + studentId + "' not exist", this.getTitle());
            return null;
        }

        if (moduleManager.getModule(moduleCode) != null) {
            module = moduleManager.getModule(moduleCode);
        } else {
            displayErrorMessage("Module with code : '" + moduleCode + "' not exist", this.getTitle());
            return null;
        }

        if (isExistEnrolment(studentId, moduleCode)) {
            displayErrorMessage("Enrolemnt existed!", this.getTitle());
            return null;
        } else {
            Enrolment enrolment = new Enrolment(student, module, internalMark, examinationMark);
            this.objects.add(enrolment);
            displayMessage("Create :" + enrolment.toString(), this.getTitle());
            System.out.println(enrolment.toString());
        }
        return null;
    }

    private boolean isExistEnrolment(String studentId, String moduleCode) {
        for (int i = 0; i < this.objects.size(); i++) {
            Enrolment enrolment = (Enrolment) this.objects.get(i);
            if (studentId.equals(enrolment.getStudent().getId()) && moduleCode.equals(enrolment.getModule().getCode())) {
                return true;
            }
        }
        return false;
    }

    public void report() {
        // init frame with title
        JFrame frame = new JFrame("List of the initial enrolments");
        // set default close operation
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        // set dimension
        frame.setPreferredSize(new Dimension(800, 600));
        // init header
        String[] headers = {
                "No", "Student ID", "Student name", "Module code", "Module name"
        };
        // init ez table with headers
        EasyTable easyTable = new EasyTable(headers);
        // add row with values
        for (int i = 0; i < objects.size(); i++) {
            Enrolment enrolment = (Enrolment) objects.get(i);
            easyTable.addRow();
            easyTable.setValueAt(i + 1, i, 0);
            easyTable.setValueAt(enrolment.getStudent().getId(), i, 1);
            easyTable.setValueAt(enrolment.getStudent().getName(), i, 2);
            easyTable.setValueAt(enrolment.getModule().getCode(), i, 3);
            easyTable.setValueAt(enrolment.getModule().getName(), i, 4);
        }
        // add scroll pane
        frame.add(new JScrollPane(easyTable));
        // pack components
        frame.pack();
        // make all components visible
        frame.setVisible(true);
    }

    public void assessmentReport() {
        // create new frame with title
        JFrame frame = new JFrame("List of the assessed enrolments");
        // set close operation
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        // set dimension
        frame.setPreferredSize(new Dimension(800, 600));
        // init  headers
        String[] headers = {
                "No", "Student ID", "Module code", "Internal Mark", "Exam Mark", "Final Grade"
        };
        // init ez table with headers
        EasyTable easyTable = new EasyTable(headers);
        // add row with values
        for (int i = 0; i < objects.size(); i++) {
            easyTable.addRow();
            Enrolment e = (Enrolment) objects.get(i);
            easyTable.setValueAt(i + 1, i, 0);
            easyTable.setValueAt(e.getStudent().getId(), i, 1);
            easyTable.setValueAt(e.getModule().getCode(), i, 2);
            easyTable.setValueAt(e.getInternalMark(), i, 3);
            easyTable.setValueAt(e.getExaminationMark(), i, 4);
            easyTable.setValueAt(e.getFinalGrade(), i, 5);
        }
        // add scroll pane
        frame.add(new JScrollPane(easyTable));

        frame.pack();
        // make components visible
        frame.setVisible(true);


    }

    @Override
    public void save() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("enrolment.dat"))) {
            objectOutputStream.writeObject(objects);
        } catch (IOException ex) {
            displayErrorMessage(ex.getMessage(), this.getTitle());
        }
    }

    @Override
    public void startUp() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("enrolment.dat"))) {
            Vector enrolemts = (Vector) objectInputStream.readObject();
            objects.addAll(enrolemts);
        } catch (Exception ex) {
            displayErrorMessage(ex.getMessage(), this.getTitle());
        }
    }
}
