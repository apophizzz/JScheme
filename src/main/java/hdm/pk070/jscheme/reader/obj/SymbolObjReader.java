package hdm.pk070.jscheme.reader.obj;

import hdm.pk070.jscheme.SchemeConstants;
import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.type.SchemeFalse;
import hdm.pk070.jscheme.obj.type.SchemeNil;
import hdm.pk070.jscheme.obj.type.SchemeSymbol;
import hdm.pk070.jscheme.obj.type.SchemeTrue;
import hdm.pk070.jscheme.reader.SchemeCharacterReader;
import hdm.pk070.jscheme.symbolTable.SchemeSymbolTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 */
public class SymbolObjReader extends SchemeObjReader {


    private static final Logger LOGGER = LogManager.getRootLogger();

    public static SymbolObjReader createInstance(SchemeCharacterReader schemeCharacterReader) {
        return new SymbolObjReader(schemeCharacterReader);
    }

    private SymbolObjReader(SchemeCharacterReader schemeCharacterReader) {
        super(schemeCharacterReader);
    }

    @Override
    public SchemeSymbol read() throws SchemeError {
        StringBuffer symbolBuffer = new StringBuffer();

        while (nextCharIsValid()) {
            char ch = schemeCharacterReader.nextChar();
            symbolBuffer.append(ch);
            LOGGER.debug(String.format("Added character %c (%d) to symbol buffer", ch, (int) ch));
        }

        if (symbolBuffer.toString().equals(SchemeConstants.NIL_VAL)) {
            return new SchemeNil();
        }

        if (symbolBuffer.toString().startsWith("#")) {
            if (symbolBuffer.toString().equals(SchemeConstants.BOOL_TRUE_VAL)) {
                return new SchemeTrue();
            } else if (symbolBuffer.toString().equals(SchemeConstants.BOOL_FALSE_VAL)) {
                return new SchemeFalse();
            }
        }

        if (symbolBuffer.toString().length() > 0) {
            return SchemeSymbolTable.getInstance().getOrAdd(symbolBuffer.toString());
        } else {
            throw new SchemeError("Cannot process empty symbol name!");
        }
    }


    private boolean nextCharIsValid() {
        return ((!schemeCharacterReader.nextCharIs('(')) && (!schemeCharacterReader
                .nextCharIs(')')) && (!schemeCharacterReader.nextCharIsWhiteSpace()) && (!schemeCharacterReader
                .nextCharIs((char) SchemeConstants.EOF)));
    }
}
