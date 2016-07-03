package hdm.pk070.jscheme.obj.builtin.simple.number;

import hdm.pk070.jscheme.obj.SchemeObject;

/**
 * @author patrick.kleindienst
 */
public abstract class SchemeNumber extends SchemeObject {

    public abstract SchemeNumber add(SchemeNumber number);

    public abstract SchemeNumber subtract(SchemeNumber number);

    public abstract SchemeNumber multiply(SchemeNumber number);

    public abstract SchemeNumber divide(SchemeNumber number);

    public abstract SchemeNumber absolute();
}
