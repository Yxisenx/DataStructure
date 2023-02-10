package cn.onecolour.util;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yang
 * @date 2023/2/10
 * @description
 */
public class SerializedLambdaUtils {
    private final static Pattern RETURN_TYPE_PATTERN = Pattern.compile("\\(.*\\)L(.*);");
    private final static Pattern PARAMETER_TYPE_PATTERN = Pattern.compile("\\((.*)\\).*");

    public static SerializedLambda getFunctionalInterfaceSerializedLambda(Serializable serializable) {
        SerializedLambda serializedLambda;
        try {
            Method writeReplace = serializable.getClass().getDeclaredMethod("writeReplace");
            if (!writeReplace.isAccessible()) {
                writeReplace.setAccessible(true);
            }
            serializedLambda = (SerializedLambda) writeReplace.invoke(serializable);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return serializedLambda;
    }

    /**
     * 获取Lambda表达式返回类型, 如果lambda没有返回值, 则返回 void.class
     */
    public static Class<?> getReturnType(Serializable serializable, SerializedLambda serializedLambda) {
        if (serializedLambda == null) {
            serializedLambda = getFunctionalInterfaceSerializedLambda(serializable);
        }
        String expr = serializedLambda.getInstantiatedMethodType();
        Matcher matcher = RETURN_TYPE_PATTERN.matcher(expr);

        boolean hasReturn = matcher.find();
        if (!hasReturn) {
            return void.class;
        }
        String className = matcher.group(1).replace("/", ".");
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("can not find class! ", e);
        }
    }

    public static Class<?> getReturnType(SerializedLambda serializedLambda) {
        return getReturnType(null, serializedLambda);
    }

    public static Class<?> getReturnType(Serializable serializable) {
        return getReturnType(serializable, null);
    }

    /**
     * 获取Lambda表达式的参数类型
     */
    public static Class<?>[] getParameterTypes(Serializable serializable, SerializedLambda serializedLambda) {
        if (serializedLambda == null) {
            serializedLambda = getFunctionalInterfaceSerializedLambda(serializable);
        }
        String expr = serializedLambda.getInstantiatedMethodType();
        Matcher matcher = PARAMETER_TYPE_PATTERN.matcher(expr);
        if (!matcher.find() || matcher.groupCount() != 1) {
            throw new RuntimeException("获取Lambda信息失败");
        }
        Class<?>[] classes;
        expr = matcher.group(1);
        if (!expr.isEmpty()) {
            String[] classNames = expr.split(";");
            classes = new Class<?>[classNames.length];
            for (int i = 0; i < classNames.length; i++) {
                String className = classNames[i].replace("/", ".").substring(1);
                try {
                    classes[i] = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("无法加载类", e);
                }
            }
        } else {
            classes = new Class<?>[0];
        }
        return classes;
    }

    public static Class<?>[] getParameterTypes(Serializable serializable) {
        return getParameterTypes(serializable, null);
    }

    public static Class<?>[] getParameterTypes(SerializedLambda serializedLambda) {
        return getParameterTypes(null, serializedLambda);
    }
}
