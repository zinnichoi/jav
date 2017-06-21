package courseman2.model;

import courseman2.DomainConstraint;

import java.io.Serializable;

/**
 * @author : Nguyen Manh Tien 3c14
 * @Overview : Module present course that student can errol.
 * @Attribute :
 * code         String
 * codeCount    int[]
 * name         String
 * semester     int
 * credit       int
 * @Objects :
 * @Abstract_properties:
 * mutable(code) = false /\ optional(id) = true /\
 * mutable(codeCount) = false /\ optional(name) = true /\
 * mutable(name) = true /\ optional(dob) = false /\
 * mutable(semester) = true /\ optional(address) = false /\
 * mutable(credit) = true /\ optional(email) = false.
 */
public abstract class Module implements Serializable {
    @DomainConstraint(type = DomainConstraint.Type.String, mutable = false, optional = true)
    protected String code;
    @DomainConstraint(type = DomainConstraint.Type.Collection, mutable = false, optional = true)
    protected static int[] codeCount = new int[100];
    @DomainConstraint(type = DomainConstraint.Type.String, mutable = true, optional = false)
    protected String name;
    @DomainConstraint(type = DomainConstraint.Type.Integer, mutable = true, optional = false)
    protected int semester;
    @DomainConstraint(type = DomainConstraint.Type.Integer, mutable = true, optional = false)
    protected int credit;

    /**
     * initialise Module with inputs
     *      and auto-increse code
     */
    public Module(String name, int semester, int credit) {
        this.code = nextCode(semester);
        this.name = name;
        this.semester = semester;
        this.credit = credit;
    }

    /**
     *@effect :
     *        code = 'M' + semester*100
     *         using int[] to save semester count
     *         example :  semester 1 current code = int[1]
     *                    semester 2 current code = int[2]
     *                    and so on
     *
     *         if currentCode = 0
     *              return "M" + (semester*100 +1) // this is first code of this semester
     *          else
     *              return "M" + (semester*100 + currentCode +1)
     */
    protected String nextCode(int semester) {
        int currentCode = codeCount[semester];
        if (currentCode == 0) {
            codeCount[semester] = currentCode + 1;
            return "M" + (semester * 100 + 1);
        } else {
            codeCount[semester] = currentCode + 1;
            return "M" + (semester * 100 + currentCode + 1);
        }
    }

    /**
     * @return this.code
     */
    public String getCode() {
        return code;
    }

    /**
     *@effect :
     *      this.code = code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return this.name
     */
    public String getName() {
        return name;
    }

    /**
     *@effect :
     *        this.name = name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return this.semester
     */
    public int getSemester() {
        return semester;
    }

    /**
     *@effect :
     *      this.semester = semester
     */
    public void setSemester(int semester) {
        this.semester = semester;
    }

    /**
     * @return  this.credit
     */
    public int getCredit() {
        return credit;
    }

    /**
     *@effect :
     *       this.credir = credit
     */
    public void setCredit(int credit) {
        this.credit = credit;
    }

    /**
     * @return  a String present Module
     */
    @Override
    public String toString() {
        return "Module{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", semester=" + semester +
                ", credit=" + credit +
                '}';
    }
}
