package hdm.pk070.jscheme.obj.type;

import hdm.pk070.jscheme.obj.SchemeObject;

import java.util.Objects;

/**
 * @author patrick.kleindienst
 */
public final class SchemeCons extends SchemeObject {

    private SchemeObject car;
    private SchemeObject cdr;

    public SchemeCons(SchemeObject car, SchemeObject cdr) {
        Objects.requireNonNull(car);
        Objects.requireNonNull(cdr);
        this.car = car;

        if (cdr.getClass().equals(SchemeCons.class) || cdr.getClass().equals(SchemeNil.class)) {
            this.cdr = cdr;
        } else {
            throw new IllegalArgumentException("Cdr of SchemeCons must be SchemeCons or SchemeNil!");
        }
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(prettyPrintList(this));
        return stringBuilder.toString();
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

    @Override
    public boolean equals(Object obj) {
        if (Objects.isNull(obj)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!obj.getClass().equals(this.getClass())) {
            return false;
        }
        if (!this.getCar().equals(((SchemeCons) obj).getCar())) {
            return false;
        }
        if (!this.getCdr().equals(((SchemeCons) obj).getCdr())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 5;
        if (Objects.nonNull(car)) {
            hashCode = (hashCode * 31) + car.hashCode();
        }
        if (Objects.nonNull(cdr)) {
            hashCode = (hashCode * 31) + cdr.hashCode();
        }
        return hashCode;
    }
}
