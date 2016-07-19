package hdm.pk070.jscheme.obj.builtin.simple;

import hdm.pk070.jscheme.obj.SchemeObject;

/**
 * @author patrick.kleindienst
 */
public class SchemeVoid extends SchemeObject {


    @Override
    public Object getValue() {
        return "";
    }

    @Override
    public String toString() {
        return "";
    }
}
