package hdm.pk070.jscheme.reader.obj;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeInteger;
import hdm.pk070.jscheme.obj.builtin.simple.number.floatComplex.SchemeFloat;
import hdm.pk070.jscheme.reader.SchemeCharacterReader;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class NumberObjReader extends SchemeObjReader {


    public static NumberObjReader createInstance(SchemeCharacterReader schemeCharacterReader) {
        return new NumberObjReader(schemeCharacterReader);
    }

    private NumberObjReader(SchemeCharacterReader schemeCharacterReader) {
        super(schemeCharacterReader);
    }

    @Override
    public SchemeNumber read() throws SchemeError {
        List<Character> numericCharBuffer = new LinkedList<>();
        while (schemeCharacterReader.nextCharIsDigit() || schemeCharacterReader.nextCharIsDecimalSeparator()) {
            numericCharBuffer.add(schemeCharacterReader.nextChar());
        }

        return toSchemeNumber(numericCharBuffer);
    }

    private SchemeNumber toSchemeNumber(List<Character> numericCharBuffer) {
        if (numericCharBuffer.contains('.')) {
            String floatValString = "";
            for (Character character : numericCharBuffer) {
                floatValString += character;
            }
            return new SchemeFloat(Float.valueOf(floatValString));
        } else {
            int intVal = 0;
            for (Character character : numericCharBuffer) {
                intVal = intVal * 10 + numericCharToInt(character);
            }
            return new SchemeInteger(intVal);
        }
    }

    private int numericCharToInt(char ch) {
        return Character.getNumericValue(ch);
    }

}
