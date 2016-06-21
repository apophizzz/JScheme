package hdm.pk070.jscheme.stack;

import hdm.pk070.jscheme.obj.SchemeObject;

import java.util.Objects;
import java.util.Stack;

/**
 * Created by patrick on 19.06.16.
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
