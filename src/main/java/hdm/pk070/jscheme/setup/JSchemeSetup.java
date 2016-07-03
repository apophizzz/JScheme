package hdm.pk070.jscheme.setup;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinDivide;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinMinus;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinPlus;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinTimes;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.table.environment.GlobalEnvironment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;
import hdm.pk070.jscheme.table.symbolTable.SchemeSymbolTable;

/**
 * @author patrick.kleindienst
 */
public final class JSchemeSetup {

    public static void init() throws SchemeError {
        printWelcomeScreen();

        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new SchemeSymbol
                ("+")), SchemeBuiltinPlus.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new SchemeSymbol
                ("-")), SchemeBuiltinMinus.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new SchemeSymbol
                ("*")), SchemeBuiltinTimes.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new SchemeSymbol
                ("/")), SchemeBuiltinDivide.create()));
    }


    private static void printWelcomeScreen() {
        System.out.println();
        System.out.println("*****************************************");
        System.out.println(" ####  ###   ### #   # #### #   # ####");
        System.out.println("    # #     #    #   # #    ## ## #");
        System.out.println("    # #### #     ##### #### # # # ####");
        System.out.println("    #    #  #    #   # #    #   # #");
        System.out.println(" #### ###    ### #   # #### #   # ####");
        System.out.println("*****************************************");
        System.out.println();
    }
}
