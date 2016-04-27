package hdm.pk070.jscheme.obj.type;

/**
 *
 */
public class SchemeNil extends SchemeSymbol {


    public static SchemeNil createObj() {
        return new SchemeNil();
    }

    private SchemeNil() {
        super("'()");
    }

}
