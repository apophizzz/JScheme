package hdm.pk070.jscheme.setup;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.builtin.function.cp.base.SchemeBuiltinEqCP;
import hdm.pk070.jscheme.obj.builtin.function.cp.list.SchemeBuiltinConsCP;
import hdm.pk070.jscheme.obj.builtin.function.cp.list.SchemeBuiltinGetCarCP;
import hdm.pk070.jscheme.obj.builtin.function.cp.list.SchemeBuiltinGetCdrCP;
import hdm.pk070.jscheme.obj.builtin.function.cp.list.SchemeBuiltinIsConsCP;
import hdm.pk070.jscheme.obj.builtin.function.cp.math.*;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.builtin.syntax.cp.define_cp.SchemeBuiltinSyntaxDefineCP;
import hdm.pk070.jscheme.obj.builtin.syntax.cp.if_cp.SchemeBuiltinSyntaxIfCP;
import hdm.pk070.jscheme.obj.builtin.syntax.cp.lambda_cp.SchemeBuiltinSyntaxLambdaCP;
import hdm.pk070.jscheme.obj.builtin.syntax.cp.quote_cp.SchemeBuiltinSyntaxQuoteCP;
import hdm.pk070.jscheme.table.environment.GlobalEnvironment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;
import hdm.pk070.jscheme.table.symbolTable.SchemeSymbolTable;

/**
 * @author patrick.kleindienst
 */
public final class JSchemeSetup {

    public static void init() throws SchemeError {
        registerBuiltinFunctionsCP();
        registerBuiltinSyntaxCP();
        printWelcomeScreen();
    }

    private static void registerBuiltinFunctionsCP() throws SchemeError {
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new SchemeSymbol
                ("+")), SchemeBuiltinPlusCP.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new SchemeSymbol
                ("-")), SchemeBuiltinMinusCP.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new SchemeSymbol
                ("*")), SchemeBuiltinTimesCP.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new SchemeSymbol
                ("/")), SchemeBuiltinDivideCP.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new SchemeSymbol
                ("abs")), SchemeBuiltinAbsoluteCP.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new SchemeSymbol
                ("cons")), SchemeBuiltinConsCP.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new SchemeSymbol
                ("car")), SchemeBuiltinGetCarCP.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new SchemeSymbol
                ("cdr")), SchemeBuiltinGetCdrCP.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new SchemeSymbol
                ("cons?")), SchemeBuiltinIsConsCP.create()));

        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new
                SchemeSymbol("eq?")), SchemeBuiltinEqCP.create()));
    }


    private static void registerBuiltinSyntaxCP() throws SchemeError {
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new
                SchemeSymbol("define")), SchemeBuiltinSyntaxDefineCP.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new
                SchemeSymbol("if")), SchemeBuiltinSyntaxIfCP.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new
                SchemeSymbol("lambda")), SchemeBuiltinSyntaxLambdaCP.create()));
        GlobalEnvironment.getInstance().add(EnvironmentEntry.create(SchemeSymbolTable.getInstance().add(new
                SchemeSymbol("quote")), SchemeBuiltinSyntaxQuoteCP.create()));
    }


    private static void printWelcomeScreen() {
        System.out.println();
        System.out.println("  Welcome to");
        System.out.println(" *****************************************");
        System.out.println("   ####  ###   ### #   # #### #   # ####");
        System.out.println("      # #     #    #   # #    ## ## #");
        System.out.println("      # #### #     ##### #### # # # ####");
        System.out.println("      #    #  #    #   # #    #   # #");
        System.out.println("   #### ###    ### #   # #### #   # ####");
        System.out.println(" *****************************************");
        System.out.println(" [continuation passing enabled]");
        System.out.println();
    }

}
