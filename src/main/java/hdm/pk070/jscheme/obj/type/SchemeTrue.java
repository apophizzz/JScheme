package hdm.pk070.jscheme.obj.type;

/**
 *
 */
public class SchemeTrue extends SchemeSymbol {


    public static SchemeTrue createObj() {
        return new SchemeTrue();
    }

    private SchemeTrue() {
        super("#t");
    }

}
