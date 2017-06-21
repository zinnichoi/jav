package courseman2.controller;

import courseman2.DomainConstraint;
import courseman2.NotPossibleException;
import courseman2.model.CompulsoryModule;
import courseman2.model.ElectiveModule;
import courseman2.model.Module;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Vector;

/**
 * Created by nguyen manh tien 3c14 on 20/04/2017.
 */
public class ModuleManager extends Manager {
    @DomainConstraint(type = DomainConstraint.Type.Object, mutable = false, optional = false)
    private JPanel westPanel, centrePanel;

    @DomainConstraint(type = DomainConstraint.Type.Object, mutable = false, optional = false)
    private JLabel jLabelDepartment;

    @DomainConstraint(type = DomainConstraint.Type.Object, mutable = false, optional = false)
    private JTextField jTextFieldName, jTextFieldSemester, jTextFieldCredit, jTextFieldDepartment;

    @DomainConstraint(type = DomainConstraint.Type.Object, mutable = false, optional = false)
    private JComboBox moduleType;

    public ModuleManager(String title, String titleText, int width, int height, int x, int y) {
        super(title, titleText, width, height, x, y);
    }

    @Override
    protected void createMiddlePanel() {
        westPanel = new JPanel();
        centrePanel = new JPanel();

        westPanel.setLayout(new GridLayout(5, 1));
        westPanel.add(new JLabel("Module Type :"));
        westPanel.add(new JLabel("Name :"));
        westPanel.add(new JLabel("Semester :"));
        westPanel.add(new JLabel("Credit :"));
        jLabelDepartment = new JLabel("DepartMent :");
        westPanel.add(jLabelDepartment);

        centrePanel.setLayout(new GridLayout(5, 1));
        moduleType = new JComboBox();
        moduleType.addItem("");
        moduleType.addItem("Compulsory");
        moduleType.addItem("Elective");
        jTextFieldName = new JTextField(30);
        jTextFieldSemester = new JTextField(30);
        jTextFieldCredit = new JTextField(30);
        jTextFieldDepartment = new JTextField(30);
        centrePanel.add(moduleType);
        centrePanel.add(jTextFieldName);
        centrePanel.add(jTextFieldSemester);
        centrePanel.add(jTextFieldCredit);
        centrePanel.add(jTextFieldDepartment);

        moduleType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (moduleType.getSelectedItem().equals("Compulsory")) {
                    jLabelDepartment.setVisible(false);
                    jTextFieldDepartment.setVisible(false);
                } else {
                    jLabelDepartment.setVisible(true);
                    jTextFieldDepartment.setVisible(true);
                }
            }
        });

        this.gui.add(westPanel, BorderLayout.WEST);
        this.gui.add(centrePanel, BorderLayout.CENTER);
    }


    @Override
    public void clearGUI() {
        clearGUI(centrePanel);
    }

    @Override
    public Object createObject() throws NotPossibleException {
        int semester = 0, credit = 0;
        try {
            semester = Integer.parseInt(jTextFieldSemester.getText());
        } catch (Exception ex) {
            displayErrorMessage("Semester must be number only !", this.getTitle());
        }

        try {
            credit = Integer.parseInt(jTextFieldCredit.getText());
        } catch (Exception ex) {
            displayErrorMessage("Credit must be number only !", this.getTitle());
        }

        // compulsory module
        if (!jTextFieldDepartment.isVisible()) {
            if (jTextFieldName.getText().length() != 0 && semester > 0 && credit > 0) {
                CompulsoryModule compulsoryModule = new CompulsoryModule(jTextFieldName.getText(), semester, credit);
                displayMessage("Created : " + compulsoryModule.toString(), this.getTitle());
                objects.add(compulsoryModule);
                System.out.println(compulsoryModule.toString());
            } else {
                displayErrorMessage("Please full fill the form !", this.getTitle());
            }
            //elective module
        } else {
            if (jTextFieldName.getText().length() != 0 && semester > 0 && credit > 0 && jTextFieldDepartment.getText().length() != 0) {
                ElectiveModule electiveModule = new ElectiveModule(jTextFieldName.getText(), semester, credit, jTextFieldDepartment.getText());
                displayMessage("Created : " + electiveModule.toString(), this.getTitle());
                objects.add(electiveModule);
                System.out.println(electiveModule.toString());
            } else {
                displayErrorMessage("Please full fill the form !", this.getTitle());
            }
        }
        return null;
    }

    @Override
    public void save() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("module.dat"))) {
            objectOutputStream.writeObject(objects);
        } catch (IOException ex) {
            displayErrorMessage(ex.getMessage(), this.getTitle());
        }
    }

    @Override
    public void startUp() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("module.dat"))) {
            Vector modules = (Vector) objectInputStream.readObject();
            objects.addAll(modules);
        } catch (Exception ex) {
            displayErrorMessage(ex.getMessage(), this.getTitle());
        }
    }

    public Module getModule(String moduleCode) {
        for (Object o : objects) {
            if (moduleCode.equals(((Module) o).getCode())) {
                return (Module) o;
            }
        }
        return null;
    }
}
