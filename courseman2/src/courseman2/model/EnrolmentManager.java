
package courseman2.model;
import courseman2.DomainConstraint;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author : Nguyen Manh Tien 3c14
 * @Overview : EnrolmentManager is a class to manage enrolment
 * @Attribute :
 * enrolments       : ArrayList<Enrolment>
 * @Objects :
 * @Abstract_properties:
 * mutable(enrolments) = true /\  optional(enrolments) = false.
 */
public class EnrolmentManager {
    @DomainConstraint(type = DomainConstraint.Type.Collection, mutable = true, optional = false)
    ArrayList<Enrolment> enrolments;

    /**
     * @effect :
     *          initialise EnrolmentManager
     */
    public EnrolmentManager() {
        enrolments = new ArrayList<>();
    }

    /**
     * @effect add enrolment into enrolments
     */
    public void addEnrolment(Enrolment enrolment) {
        enrolments.add(enrolment);
    }

    /**
     *@effect :
     *      loop from start to end of enrolments
     *          if exist enrolment contain student and module
     *              return that enrolment
     *          else
     *              return null
     */
    public Enrolment getEnrolment(Student student, Module module) {
        for (Enrolment enrolment : enrolments) {
            if (enrolment.getStudent().equals(student) && enrolment.getModule().equals(module)) {
                return enrolment;
            }
        }
        return null;
    }

    /**
     *@effect :
     *      get enrolment with student and module
     *          set internalMark and examinationMark
     */
    public void setMark(Student student, Module module, double internalMark, double examinationMark) {
        Enrolment enrolment = getEnrolment(student, module);
        enrolment.setInternalMark(internalMark);
        enrolment.setExaminationMark(examinationMark);
    }

    /**
     * @return a String represent all enrolment with student id and module code
     */
    public String report() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Enrolment enrolment : enrolments) {
            stringBuilder.append("StudentID: " + enrolment.getStudent().getId() + "   " + "ModuleID: " + enrolment.getModule().getCode() + "\n");
        }
        return stringBuilder.toString();
    }

    /**
     * @return a String represent all enrolment with student id, module code, internalMark, examinationMark and finalGrade
     */
    public String reportAssessment() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Enrolment enrolment : enrolments) {
            stringBuilder.append("StudentID: " + enrolment.getStudent().getId() + "   " + "ModuleID: " + enrolment.getModule().getCode() + " Internal Mark: " + enrolment.getInternalMark() + " Exam Mark: " + enrolment.getExaminationMark() + " Final Mark: " + enrolment.getFinalGrade() + "\n");
        }
        return stringBuilder.toString();
    }

    /**
     * sort enrolment by student id
     */
    public void sort() {
        Collections.sort(enrolments);
    }

}
