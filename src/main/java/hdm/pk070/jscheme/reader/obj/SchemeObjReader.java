package hdm.pk070.jscheme.reader.obj;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.reader.SchemeCharacterReader;

import java.util.Objects;

/**
 * This class acts as a base class for concrete {@link SchemeObjReader} implementations, depending on what kind of
 * object should be read.
 *
 * @author patrick.kleindienst
 */
abstract class SchemeObjReader {

    SchemeCharacterReader schemeCharacterReader;


    SchemeObjReader(SchemeCharacterReader schemeCharacterReader) {
        Objects.requireNonNull(schemeCharacterReader);
        this.schemeCharacterReader = schemeCharacterReader;
    }

    /**
     * Read the next {@link SchemeObject} available from the input stream.
     *
     * @return The object which has been read.
     * @throws SchemeError
     *         if anything goes wrong during read process.
     */
    public abstract SchemeObject read() throws SchemeError;

    public void setCharacterReader(SchemeCharacterReader schemeCharacterReader) {
        this.schemeCharacterReader = schemeCharacterReader;
    }

}
