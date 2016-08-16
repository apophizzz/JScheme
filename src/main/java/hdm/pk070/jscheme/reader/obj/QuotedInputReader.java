package hdm.pk070.jscheme.reader.obj;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.reader.SchemeCharacterReader;
import hdm.pk070.jscheme.reader.SchemeReader;
import hdm.pk070.jscheme.table.symbolTable.SchemeSymbolTable;

/**
 * @author patrick.kleindienst
 */
public class QuotedInputReader extends SchemeObjReader {


    public static QuotedInputReader createInstance(SchemeCharacterReader schemeCharacterReader) {
        return new QuotedInputReader(schemeCharacterReader);
    }

    private QuotedInputReader(SchemeCharacterReader schemeCharacterReader) {
        super(schemeCharacterReader);
    }

    @Override
    public SchemeObject read() throws SchemeError {
        this.schemeCharacterReader.skipNext();

        SchemeReader schemeReader = SchemeReader.withCurrentStream()
                .orElseThrow(() -> new IllegalStateException("SchemeReader has not been initialized!"));
        SchemeObject recursiveReadResult = schemeReader.read();


        return new SchemeCons(SchemeSymbolTable.getInstance().get("quote").orElse(SchemeSymbolTable.getInstance()
                .add(new SchemeSymbol("quote"))), new SchemeCons(recursiveReadResult, new SchemeNil()));
    }

}
