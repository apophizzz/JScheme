package hdm.pk070.jscheme.reader.obj;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.reader.SchemeCharacterReader;

import java.util.Objects;

/**
 *
 */
public abstract class SchemeObjReader {

    protected SchemeCharacterReader schemeCharacterReader;


    protected SchemeObjReader(SchemeCharacterReader schemeCharacterReader) {
        Objects.requireNonNull(schemeCharacterReader);
        this.schemeCharacterReader = schemeCharacterReader;
    }

    public abstract SchemeObject read() throws SchemeError;

    public void setCharacterReader(SchemeCharacterReader schemeCharacterReader) {
        this.schemeCharacterReader = schemeCharacterReader;
    }

}
