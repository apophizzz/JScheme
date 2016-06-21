package hdm.pk070.jscheme.reader.obj;

import hdm.pk070.jscheme.SchemeConstants;
import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeFalse;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeTrue;
import hdm.pk070.jscheme.reader.SchemeCharacterReader;
import hdm.pk070.jscheme.table.symbolTable.SchemeSymbolTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

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
        StringBuilder symbolBuilder = new StringBuilder();

        while (nextCharIsValid()) {
            char ch = schemeCharacterReader.nextChar();
            symbolBuilder.append(ch);
            LOGGER.debug(String.format("Added character %c (%d) to symbol buffer", ch, (int) ch));
        }

        if (symbolBuilder.toString().equals(SchemeConstants.NIL_VAL)) {
            return new SchemeNil();
        }

        if (symbolBuilder.toString().startsWith("#")) {
            if (symbolBuilder.toString().equals(SchemeConstants.BOOL_TRUE_VAL)) {
                return new SchemeTrue();
            } else if (symbolBuilder.toString().equals(SchemeConstants.BOOL_FALSE_VAL)) {
                return new SchemeFalse();
            }
        }

        if (symbolBuilder.toString().length() > 0) {
            Optional<SchemeSymbol> searchedSymbolOptional = SchemeSymbolTable.getInstance().
                    get(symbolBuilder.toString());
            return searchedSymbolOptional.orElse(SchemeSymbolTable.getInstance().
                    add(new SchemeSymbol(symbolBuilder.toString())));
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
