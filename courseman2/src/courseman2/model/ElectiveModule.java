package courseman2.model;
import courseman2.DomainConstraint;
/**
 * @author : Nguyen Manh Tien 3c14
 * @Overview : ElectiveModule is a kind of Module
 * @Attribute :
 * code         String
 * codeCount    int[]
 * name         String
 * semester     int
 * credit       int
 * departmentName String
 * @Objects :
 * @Abstract_properties:
 * mutable(code) = false /\ optional(id) = true /\
 * mutable(codeCount) = false /\ optional(name) = true /\
 * mutable(name) = true /\ optional(dob) = false /\
 * mutable(semester) = true /\ optional(address) = false /\
 * mutable(credit) = true /\ optional(email) = false /\
 * mutable(departmentName) = true /\ optional(departmentName) = false.
 */
public class ElectiveModule extends Module {
    @DomainConstraint(type = DomainConstraint.Type.String, mutable = true, optional = false)
    private String departmentName;

    /**
     * initialise ElectiveModule with inputs
     *      and auto-increse code
     */
    public ElectiveModule(String name, int semester, int credit, String departmentName) {
        super(name, semester, credit);
        this.departmentName = departmentName;
    }

    /**
     * @return this.departmentName
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * @effect
     *          this.departmentName = departmentName
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * @return a String represent ElectiveModule
     */
    @Override
    public String toString() {
        return "ElectiveModule{"+code+","+name+","+semester+","+credit+","+departmentName+"}";
    }
}
