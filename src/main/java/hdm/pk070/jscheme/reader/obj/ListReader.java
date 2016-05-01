package hdm.pk070.jscheme.reader.obj;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.type.SchemeCons;
import hdm.pk070.jscheme.obj.type.SchemeNil;
import hdm.pk070.jscheme.reader.SchemeCharacterReader;
import hdm.pk070.jscheme.reader.SchemeReader;

/**
 *
 */
public class ListReader extends SchemeObjReader {


    public static ListReader createInstance(SchemeCharacterReader schemeCharacterReader) {
        return new ListReader(schemeCharacterReader);
    }

    private ListReader(SchemeCharacterReader schemeCharacterReader) {
        super(schemeCharacterReader);
    }

    @Override
    public SchemeObject read() throws SchemeError {
        SchemeObject firstElement, rest, returnVal;

        // skip opening brace '('
//        if (schemeCharacterReader.nextNonWhitespaceCharIs('(')) {
//            schemeCharacterReader.skipNext();
//        }

        // check first non whitespace character after opening brace, since input may be like '(   abc ...)'
        if (schemeCharacterReader.nextNonWhitespaceCharIs(')')) {
            // if input is empty list (), return nil
            schemeCharacterReader.skipNext();
            return new SchemeNil();
        }

        // first element (car) could be everything, call reader recursively
        firstElement = SchemeReader.withStdin().read();

        // the rest (cdr) of list is in turn treated as a list, call this.read() recursively
        rest = this.read();

        returnVal = new SchemeCons(firstElement, rest);
        return returnVal;
    }
}
