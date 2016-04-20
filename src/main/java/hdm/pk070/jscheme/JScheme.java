package hdm.pk070.jscheme;

import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.reader.SchemeReader;

/**
 * Created by patrick on 19.04.16.
 */
public class JScheme {

    public static void main(String[] args) {
        System.out.println("Welcome to Scheme\n");
        for (; ; ) {
            System.out.print("> ");
            SchemeObject read = SchemeReader.withStdin().read();
//            System.out.println();
        }
    }
}
