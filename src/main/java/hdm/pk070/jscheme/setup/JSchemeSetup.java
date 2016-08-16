package hdm.pk070.jscheme.setup;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.builtin.function.base.SchemeBuiltinEq;
import hdm.pk070.jscheme.obj.builtin.function.list.SchemeBuiltinCons;
import hdm.pk070.jscheme.obj.builtin.function.list.SchemeBuiltinGetCar;
import hdm.pk070.jscheme.obj.builtin.function.list.SchemeBuiltinGetCdr;
import hdm.pk070.jscheme.obj.builtin.function.list.SchemeBuiltinIsCons;
import hdm.pk070.jscheme.obj.builtin.function.math.*;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.builtin.syntax.SchemeBuiltinDefine;
import hdm.pk070.jscheme.obj.builtin.syntax.SchemeBuiltinIf;
import hdm.pk070.jscheme.obj.builtin.syntax.SchemeBuiltinLambda;
import hdm.pk070.jscheme.obj.builtin.syntax.SchemeBuiltinQuote;
import hdm.pk070.jscheme.table.environment.GlobalEnvironment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;
import hdm.pk070.jscheme.table.symbolTable.SchemeSymbolTable;

/**
 * @author patrick.kleindienst
 */
public final class JSchemeSetup {

    public static void init() throws SchemeError {
        registerBuiltinFunctions();
        registerBuiltinSyntax();
        printWelcomeScreen();
    }

    private static void registerBuiltinFunctions() throws SchemeError {
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new SchemeSymbol
                ("+")), SchemeBuiltinPlus.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new SchemeSymbol
                ("-")), SchemeBuiltinMinus.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new SchemeSymbol
                ("*")), SchemeBuiltinTimes.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new SchemeSymbol
                ("/")), SchemeBuiltinDivide.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new SchemeSymbol
                ("abs")), SchemeBuiltinAbsolute.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new SchemeSymbol
                ("cons")), SchemeBuiltinCons.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new SchemeSymbol
                ("car")), SchemeBuiltinGetCar.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new SchemeSymbol
                ("cdr")), SchemeBuiltinGetCdr.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new SchemeSymbol
                ("cons?")), SchemeBuiltinIsCons.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new
                SchemeSymbol("eq?")), SchemeBuiltinEq.create()));
    }

    private static void registerBuiltinSyntax() throws SchemeError {
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new
                SchemeSymbol("define")), SchemeBuiltinDefine.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new
                SchemeSymbol("if")), SchemeBuiltinIf.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new
                SchemeSymbol("lambda")), SchemeBuiltinLambda.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new
                SchemeSymbol("quote")), SchemeBuiltinQuote.create()));
    }


    private static void printWelcomeScreen() {
        System.out.println();
        System.out.println(" *****************************************");
        System.out.println("  ####  ###   ### #   # #### #   # ####");
        System.out.println("     # #     #    #   # #    ## ## #");
        System.out.println("     # #### #     ##### #### # # # ####");
        System.out.println("     #    #  #    #   # #    #   # #");
        System.out.println("  #### ###    ### #   # #### #   # ####");
        System.out.println(" *****************************************");
        System.out.println();
    }
}
