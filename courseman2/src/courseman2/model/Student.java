package courseman2.model;
import courseman2.DomainConstraint;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author : Nguyen Manh Tien 3c14
 * @Overview : Student is a person who has information about id, name, date ò birth ,address and email.
 * @Attribute :
 * id           String
 * currentId    String
 * year         int
 * name         String
 * dob          String
 * address      String
 * email        String
 * @Objects :
 * @Abstract_properties:
 * mutable(id) = false /\ optional(id) = true /\
 * mutable(currentId) = false /\ optional(currentId) = true /\
 * mutable(year) = false /\ optional(year) = true /\
 * mutable(name) = true /\ optional(name) = false /\
 * mutable(dob) = true /\ optional(dob) = false /\
 * mutable(address) = true /\ optional(address) = false /\
 * mutable(email) = true /\ optional(email) = false.
 */
public class Student implements Serializable {
    @DomainConstraint(type = DomainConstraint.Type.String, mutable = false, optional = false)
    private String id;
    @DomainConstraint(type = DomainConstraint.Type.String, mutable = false,optional = true)
    private static String currentId = "0";
    @DomainConstraint(type = DomainConstraint.Type.Integer, mutable = false,optional = true)
    private static int year = 0;
    @DomainConstraint(type = DomainConstraint.Type.String, mutable = true, optional = false)
    private String name;
    @DomainConstraint(type = DomainConstraint.Type.String, mutable = true, optional = false)
    private String dob;
    @DomainConstraint(type = DomainConstraint.Type.String, mutable = true, optional = false)
    private String address;
    @DomainConstraint(type = DomainConstraint.Type.String, mutable = true, optional = false)
    private String email;

    /**
     * initialise Student with inputs
     *      and auto-increse id
     */
    public Student(String name, String dob, String address, String email) {
        this.id = nextId();
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.email = email;
    }

    /**
     * @Effect :
     *          get currentYear by Calendar in java
     *          if this.year equals currentYear
     *              increase currentId by one
     *              return currentId
     *          else
     *              this.year = currentYear
     *              currentId = S + this.year
     *              return currentId.
     */
    private String nextId() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (currentYear == year) {
            currentId = "S" + (Integer.parseInt(currentId.replaceAll("\\D+", "")) + 1);
            return currentId;
        } else {
            year = currentYear;
            currentId = ("S" + year);
            return currentId;
        }
    }

    /**
     * @return this.id
     */
    public String getId() {
        return id;
    }

    /**
     * @effect :
     *          this.id = id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return this.name
     */
    public String getName() {
        return name;
    }

    /**
     *@effect :
     *      this.name = name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *@return :
     *          this.dob;
     */
    public String getDob() {
        return dob;
    }

    /**
     * @effect :
     *          this.dob = dob;
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     *@return
     *      this.address
     */
    public String getAddress() {
        return address;
    }

    /**
     *@effect :
     *      this.address = address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *@return this.email
     */
    public String getEmail() {
        return email;
    }

    /**
     *@effect :
     *          this.email = email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return
     *      a string present Student
     */
    @Override
    public String toString() {
        return "Student{" +id +","+name+","+dob+","+address+","+email+"}";
    }
}
