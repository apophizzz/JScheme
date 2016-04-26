package hdm.pk070.jscheme;

import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.reader.SchemeReader;

/**
 * Created by patrick on 19.04.16.
 */
public class JScheme {


    public static void main(String[] args) {
        System.out.println("\n### Welcome to Scheme ###\n");
        SchemeReader schemeReader = SchemeReader.withStdin();
        for (; ; ) {
            System.out.print(">> ");
            SchemeObject result = schemeReader.read();
            System.out.println("=> " + result);
        }
    }

}
