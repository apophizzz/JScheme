package hdm.pk070.jscheme.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author patrick.kleindienst
 */
public class ReflectionUtils {

    public static Object invoke(Object caller, String methodName, ReflectionMethodParam... params) {
        Method method = getMethodFromClass(caller.getClass(), methodName, toTypeArray(params));
        makeAccessibleIfNecessary(method);
        return invokeMethod(method, caller, toValueArray(params));
    }

    private static void makeAccessibleIfNecessary(Method method) {
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    private static Object invokeMethod(Method method, Object caller, Object... paramValues) {
        try {
            return method.invoke(caller, paramValues);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Method getMethodFromClass(Class clazz, String methodName, Class... paramClasses) {
        Objects.requireNonNull(clazz);
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName, paramClasses);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }

    private static Class[] toTypeArray(ReflectionMethodParam[] params) {
        if (params.length != 0) {
            Class[] classes = new Class[params.length];
            for (int i = 0; i < params.length; i++) {
                classes[i] = params[i].getClazz();
            }
            return classes;
        } else {
            return new Class[]{};
        }
    }


    private static Object[] toValueArray(ReflectionMethodParam[] params) {
        if (params.length != 0) {
            Object[] paramValues = new Object[params.length];
            for (int i = 0; i < params.length; i++) {
                paramValues[i] = params[i].getValue();
            }
            return paramValues;
        } else {
            return new Object[]{};
        }
    }

}
