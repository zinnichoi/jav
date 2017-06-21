package courseman2.model;


import courseman2.DomainConstraint;

import java.io.Serializable;

/**
 * @author : Nguyen Manh Tien 3c14
 * @Overview : Enrolment present modules of students can enrol
 * @Attribute :
 * student              Student
 * module               Module
 * internalMark         double
 * examinationMark      double
 * finalGrade           char
 * @Objects :
 * @Abstract_properties:
 * mutable(student) = true /\ optional(student) = false /\
 * mutable(module) = true /\ optional(module) = false /\
 * mutable(internalMark) = true /\ optional(internalMark) = false /\
 * mutable(examinationMark) = true /\ optional(examinationMark) = false /\
 * mutable(finalGrade) = true /\ optional(finalGrade) = false.
 */
public class Enrolment implements Comparable,Serializable {
    @DomainConstraint(type = DomainConstraint.Type.Object, mutable = true, optional = false)
    private Student student;
    @DomainConstraint(type = DomainConstraint.Type.Object, mutable = true, optional = false)
    private Module module;
    @DomainConstraint(type = DomainConstraint.Type.Double, mutable = true, optional = false)
    private double internalMark;
    @DomainConstraint(type = DomainConstraint.Type.Double, mutable = true, optional = false)
    private double examinationMark;
    @DomainConstraint(type = DomainConstraint.Type.Char, mutable = true, optional = false)
    private char finalGrade;

    /**
     * initialise Enrolment with inputs
     *      and auto-increse code
     */
    public Enrolment(Student student, Module module, double internalMark, double examinationMark) {
        this.student = student;
        this.module = module;
        this.internalMark = internalMark;
        this.examinationMark = examinationMark;
        this.finalGrade = getFinalGrade(internalMark, examinationMark);
    }

    /**
     *@effect :
     *      aggregatedMark = internalMark * 0.4 + examinationMark * 0.6
     *      if aggregratedMark > 9 return 'E'
     *      if 9 > aggregratedMark > 7 return 'G'
     *      if 7 > aggregratedMark > 5 return 'P'
     *      if aggregratedMark < 5 return 'F'
     */
    public char getFinalGrade(double internalMark, double examinationMark) {
        double aggregatedMark = internalMark * 0.4 + examinationMark * 0.6;
        if (aggregatedMark >= 9) {
            return 'E';
        }
        if (aggregatedMark >= 7) {
            return 'G';
        }
        if (aggregatedMark >= 5) {
            return 'P';
        }
        if (aggregatedMark < 5) {
            return 'F';
        }
        return 0;
    }

    /**
     * @return this.student
     */
    public Student getStudent() {
        return student;
    }

    /**
     *@effect :
     *      this.student = student
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * @return : this.module
     */
    public Module getModule() {
        return module;
    }

    /**
     * this.module = module
     */
    public void setModule(Module module) {
        this.module = module;
    }

    /**
     * @return this.internalMark
     */
    public double getInternalMark() {
        return internalMark;
    }

    /**
     * @effect :
     *      this.internalMark = internalMark
     */
    public void setInternalMark(double internalMark) {
        this.internalMark = internalMark;
    }

    /**
     * @return this.examinationMark
     */
    public double getExaminationMark() {
        return examinationMark;
    }

    /**
     * @effect :
     *      this.examinationMark = examinationMark
     */
    public void setExaminationMark(double examinationMark) {
        this.examinationMark = examinationMark;
    }

    /**
     * @return this.finalGrade
     */
    public char getFinalGrade() {
        return finalGrade;
    }

    /**
     * @effect :
     *          this.finalGrade = finalGrade
     */
    public void setFinalGrade(char finalGrade) {
        this.finalGrade = finalGrade;
    }

    /**
     * @return a String represent Enrolment
     */
    @Override
    public String toString() {
        return "Enrolment{" +student+","+module+","+internalMark+","+examinationMark+","+finalGrade+"}";
    }

    /**
     * @effect :
     *      compare student id of this enrolment and other enrolment
     *      return -1,1 or 0
     */
    @Override
    public int compareTo(Object o) {
        Enrolment enrolment = (Enrolment) o;
        int studentIdWithoutS = Integer.parseInt(enrolment.getStudent().getId().split("S")[1]);
        int thisStudentIdWithoutS = Integer.parseInt(this.getStudent().getId().split("S")[1]);
        if (thisStudentIdWithoutS > studentIdWithoutS) {
            return -1;
        } else if (thisStudentIdWithoutS == studentIdWithoutS) {
            return 0;
        } else {
            return 1;
        }
    }

}
