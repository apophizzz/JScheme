package hdm.pk070.jscheme.util;

import hdm.pk070.jscheme.util.exception.ReflectionMethodCallException;
import hdm.pk070.jscheme.util.exception.ReflectionUtilsException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author patrick.kleindienst
 */
public class ReflectionUtils {

    public static Object invokeMethod(Object caller, String methodName, ReflectionCallArg... params) {
        Method method = getMethodFromClass(caller.getClass(), methodName, toTypeArray(params));
        makeAccessibleIfNecessary(method);
        return invoke(method, caller, toValueArray(params));
    }

    private static void makeAccessibleIfNecessary(Method method) {
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    private static Object invoke(Method method, Object caller, Object... paramValues) {
        try {
            return method.invoke(caller, paramValues);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ReflectionMethodCallException("An exception was raised during reflection method call.", e);
        }
    }

    private static Method getMethodFromClass(Class clazz, String methodName, Class<?>... paramClasses) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(methodName);
        return checkClassHierarchy(clazz, methodName, paramClasses);
    }

    private static Method checkClassHierarchy(Class clazz, String methodName, Class<?>... paramClasses) {
        Class type = clazz;
        while (Objects.nonNull(type)) {
            try {
                Method method = type.getDeclaredMethod(methodName, paramClasses);
                if (Objects.nonNull(method)) {
                    return method;
                }
            } catch (NoSuchMethodException e) {
                type = type.getSuperclass();
            }
        }
        throw new ReflectionUtilsException(String.format("Unable to find method %s in class hierarchy of %s",
                methodName, clazz.getSimpleName()));
    }

    private static Class[] toTypeArray(ReflectionCallArg[] params) {
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


    private static Object[] toValueArray(ReflectionCallArg[] params) {
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


    public static Object getAttributeVal(Object obj, String attribute) {
        Field field = getFieldByName(obj, attribute);
        makeAccessibleIfNecessary(field);
        return getFieldValFromObj(obj, field);
    }

    public static void setAttributeVal(Object obj, String attribute, Object value) {
        Field field = getFieldByName(obj, attribute);
        makeAccessibleIfNecessary(field);
        if (field.getType().equals(value.getClass())) {
            try {
                field.set(obj, value);
            } catch (IllegalAccessException e) {
                throw new ReflectionUtilsException("Error changing value of attribute");
            }
        }
    }

    private static Field getFieldByName(Object obj, String fieldName) {
        List<Field> allFields = collectFields(obj);
        Optional<Field> foundField = allFields.stream().filter(collectedField -> collectedField.getName().equals
                (fieldName)).findAny();
        if (!foundField.isPresent()) {
            throw new ReflectionUtilsException(String.format("Unable to find field named %s", fieldName));
        }
        return foundField.get();
    }

    private static List<Field> collectFields(Object obj) {
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(obj.getClass().getDeclaredFields()));
        Class type = obj.getClass();

        while (Objects.nonNull(type.getSuperclass())) {
            type = type.getSuperclass();
            fields.addAll(Arrays.asList(type.getDeclaredFields()));
        }
        return fields;
    }

    private static void makeAccessibleIfNecessary(Field field) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    private static Object getFieldValFromObj(Object obj, Field field) {
        Object result = null;
        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Constructor getConstructorWithArgs(Class clazz, ReflectionCallArg... callArgs) {
        Constructor targetConstructor;
        List<Class> collectedTypes = Arrays.stream(callArgs).map(ReflectionCallArg::getClazz)
                .collect(Collectors.toList());

        try {
            targetConstructor = clazz.getDeclaredConstructor(collectedTypes.toArray(new Class[collectedTypes.size()]));
        } catch (NoSuchMethodException e) {
            throw new ReflectionUtilsException("Unable to find matching constructor!", e);
        }
        return targetConstructor;
    }


    public static Object createInstance(Class clazz, ReflectionCallArg... callArgs) {
        Object instance;
        Constructor targetConstructor = getConstructorWithArgs(clazz, callArgs);
        List<Object> collectedArgs = Arrays.stream(callArgs).map(ReflectionCallArg::getValue)
                .collect(Collectors.toList());
        try {
            instance = targetConstructor.newInstance(collectedArgs.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ReflectionUtilsException("Something went wrong while invoking a constructor!", e);
        }
        return instance;
    }

}
