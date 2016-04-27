package hdm.pk070.jscheme.obj.type;

/**
 *
 */
public class SchemeFalse extends SchemeSymbol {

    public static SchemeFalse createObj() {
        return new SchemeFalse();
    }

    private SchemeFalse() {
        super("#f");
    }
}
