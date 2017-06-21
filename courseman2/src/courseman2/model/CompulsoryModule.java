package courseman2.model;
import courseman2.DomainConstraint;

/**
 * @author : Nguyen Manh Tien 3c14
 * @Overview : CompulsoryModule is a kind of Module
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
public class CompulsoryModule extends Module {
    /**
     * initialise CompulsoryModule with inputs
     *      and auto-increse code
     */
    public CompulsoryModule(String name, int semester, int credit) {
        super(name, semester, credit);
    }

    /**
     * @return a String represent CompulsoryModule
     */
    @Override
    public String toString() {
        return "CompulsoryModule{"+code+","+name+","+semester+","+credit+"}";

    }
}
