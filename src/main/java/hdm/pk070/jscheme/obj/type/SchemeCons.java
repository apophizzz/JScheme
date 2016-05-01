package hdm.pk070.jscheme.obj.type;

import hdm.pk070.jscheme.obj.SchemeObject;

/**
 * @author patrick.kleindienst
 */
public class SchemeCons extends SchemeObject {

    private SchemeObject car;
    private SchemeObject cdr;

    public SchemeCons(SchemeObject car, SchemeObject cdr) {
        this.car = car;
        this.cdr = cdr;
    }

    @Override
    public Object getValue() {
        throw new UnsupportedOperationException("SchemeCons does not have a value, use getCar() and getCdr()");
    }

    public SchemeObject getCar() {
        return car;
    }

    public SchemeObject getCdr() {
        return cdr;
    }


    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("(");
        buffer.append(prettyPrintList(this));
        return buffer.toString();
    }

    private String prettyPrintList(SchemeObject schemeObject) {
        String listPrint = "";
        listPrint += ((SchemeCons) schemeObject).getCar().toString();
        if (((SchemeCons) schemeObject).getCdr().typeOf(SchemeNil.class)) {
            listPrint += ")";
            return listPrint;
        }
        listPrint += " ";
        return listPrint + prettyPrintList(((SchemeCons) schemeObject).getCdr());
    }
}
