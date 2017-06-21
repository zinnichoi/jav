package courseman2.controller;

import courseman2.DomainConstraint;
import courseman2.NotPossibleException;
import courseman2.model.Student;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Vector;

/**
 * Created by nguyen manh tien 3c14 on 20/04/2017.
 */
public class StudentManager extends Manager {
    @DomainConstraint(type = DomainConstraint.Type.Object, mutable = false, optional = false)
    private JPanel westPanel, centrePanel;

    @DomainConstraint(type = DomainConstraint.Type.Object, mutable = false, optional = false)
    private JTextField jTextFieldName, jTextFieldDob, jTextFieldAddress, jTextFieldEmail;

    public StudentManager(String title, String titleText, int width, int height, int x, int y) {
        super(title, titleText, width, height, x, y);
    }

    @Override
    protected void createMiddlePanel() {
        westPanel = new JPanel();
        centrePanel = new JPanel();

        westPanel.setLayout(new GridLayout(4, 1));
        westPanel.add(new JLabel("Name : "));
        westPanel.add(new JLabel("Dob : "));
        westPanel.add(new JLabel("Address : "));
        westPanel.add(new JLabel("Email : "));

        centrePanel.setLayout(new GridLayout(4, 1));
        jTextFieldName = new JTextField(30);
        centrePanel.add(jTextFieldName);
        jTextFieldDob = new JTextField(30);
        centrePanel.add(jTextFieldDob);
        jTextFieldAddress = new JTextField(30);
        centrePanel.add(jTextFieldAddress);
        jTextFieldEmail = new JTextField(30);
        centrePanel.add(jTextFieldEmail);

        gui.add(westPanel, BorderLayout.WEST);
        gui.add(centrePanel, BorderLayout.CENTER);
    }

    @Override
    public void clearGUI() {
        clearGUI(centrePanel);
    }

    @Override
    public Object createObject() throws NotPossibleException {
        if (jTextFieldName.getText().length() != 0 && jTextFieldDob.getText().length() != 0 &&
                jTextFieldAddress.getText().length() != 0 && jTextFieldEmail.getText().length() != 0) {
            Student student = new Student(jTextFieldName.getText(), jTextFieldDob.getText(), jTextFieldAddress.getText(), jTextFieldEmail.getText());
            this.objects.add(student);
            System.out.println(student.toString());
            displayMessage("Create : " + student.toString(), this.getTitle());
        } else {
            displayErrorMessage("Please full fill the form !", this.getTitle());
        }
        return null;
    }

    @Override
    public void save() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("student.dat"))) {
            objectOutputStream.writeObject(objects);
        } catch (IOException ex) {
            displayErrorMessage(ex.getMessage(), this.getTitle());
        }
    }

    @Override
    public void startUp() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("student.dat"))) {
            Vector students = (Vector) objectInputStream.readObject();
            objects.addAll(students);
        } catch (Exception ex) {
            displayErrorMessage(ex.getMessage(), this.getTitle());
        }
    }

    public Student getStudent(String studentId) {
        for (Object o : objects) {
            if (studentId.equals(((Student) o).getId())) {
                return (Student) o;
            }
        }
        return null;
    }
}
