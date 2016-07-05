package hdm.pk070.jscheme.obj.builtin.syntax;

import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

/**
 * @author patrick.kleindienst
 */
public abstract class SchemeBuiltinSyntax extends SchemeObject {

    public abstract SchemeObject apply(SchemeObject argumentList, Environment<SchemeSymbol, EnvironmentEntry>
            environment);

}
