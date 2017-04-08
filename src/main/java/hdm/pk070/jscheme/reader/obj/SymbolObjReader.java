package hdm.pk070.jscheme.reader.obj;

import hdm.pk070.jscheme.SchemeConstants;
import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeFalse;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeTrue;
import hdm.pk070.jscheme.reader.SchemeCharacterReader;
import hdm.pk070.jscheme.table.symbolTable.SchemeSymbolTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Optional;

/**
 * Read a {@link SchemeSymbol} from {@link InputStream}.
 *
 * @author patrick.kleindienst
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


        String symbolRead = symbolBuilder.toString();

        if (symbolRead.equals(SchemeConstants.NIL_VAL)) {
            return new SchemeNil();
        }

        if (symbolRead.startsWith("#")) {
            if (symbolRead.equals(SchemeConstants.BOOL_TRUE_VAL)) {
                return new SchemeTrue();
            } else if (symbolRead.equals(SchemeConstants.BOOL_FALSE_VAL)) {
                return new SchemeFalse();
            }
        }

        if (!symbolRead.isEmpty()) {
            Optional<SchemeSymbol> searchedSymbolOptional = SchemeSymbolTable.getInstance().
                    get(symbolRead);
            return searchedSymbolOptional.orElse(SchemeSymbolTable.getInstance().
                    add(new SchemeSymbol(symbolRead)));
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
