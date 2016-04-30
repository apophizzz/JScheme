package hdm.pk070.jscheme.reader.obj;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.type.SchemeInteger;
import hdm.pk070.jscheme.reader.SchemeCharacterReader;

/**
 *
 */
public class IntegerObjReader extends SchemeObjReader {


    public static IntegerObjReader createInstance(SchemeCharacterReader schemeCharacterReader) {
        return new IntegerObjReader(schemeCharacterReader);
    }

    private IntegerObjReader(SchemeCharacterReader schemeCharacterReader) {
        super(schemeCharacterReader);
    }

    @Override
    public SchemeInteger read() throws SchemeError {
        int intVal = 0;
        while (schemeCharacterReader.nextCharIsDigit()) {
            intVal = intVal * 10 + numericCharToInt(schemeCharacterReader.nextChar());
        }
        return SchemeInteger.createObj(intVal);
    }

    private int numericCharToInt(char ch) {
        return Character.getNumericValue(ch);
    }

}
