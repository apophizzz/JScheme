package hdm.pk070.jscheme.stack;

import hdm.pk070.jscheme.obj.SchemeObject;

import java.util.Objects;
import java.util.Stack;

/**
 * Represents the global JScheme stack. Since there's only one instance at runtime, it's implemented as a singleton.
 *
 * @author patrick.kleindienst
 */
public class SchemeCallStack extends Stack<SchemeObject> {

    private static SchemeCallStack schemeCallStack;

    public static SchemeCallStack instance() {
        if (Objects.isNull(schemeCallStack)) {
            schemeCallStack = new SchemeCallStack();
        }
        return schemeCallStack;
    }

    private SchemeCallStack() {
    }

}
