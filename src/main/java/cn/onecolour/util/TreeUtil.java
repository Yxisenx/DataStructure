package cn.onecolour.util;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author yang
 * @date 2023/2/7
 * @description
 */

@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public class TreeUtil {
    private final static Map<String, Instance<?>> INSTANCE_MAP = new HashMap<>();
    private final static Map<SFunction<?, ?>, ReflectName> FUNCTION_REFLECT_NAME_MAP = new HashMap<>();


    public static <T, R, L extends Collection> List<T> treeToList(@Nonnull Collection<T> nodes, @Nonnull SFunction<T, R> refIdGetFunction,
                                                                  @Nonnull SFunction<T, R> idGetFunction, @Nonnull SFunction<T, L> subGetFunction) {
        return execute(nodes, ArrayList::new, () -> instance(refIdGetFunction, idGetFunction, subGetFunction).treeToList(nodes));
    }

    public static <T> List<T> treeToList(@Nonnull Collection<T> nodes, @Nonnull String refIdField,
                                         @Nonnull String idField, @Nonnull String subField) {
        return execute(nodes, ArrayList::new, () -> TreeUtil.<T>instance((Class<T>) nodes.iterator().next().getClass(), refIdField, idField, subField).treeToList(nodes));
    }

    public static <T, R, L extends Collection> Set<T> treeToSet(@Nonnull Collection<T> nodes, @Nonnull SFunction<T, R> refIdGetFunction,
                                                                @Nonnull SFunction<T, R> idGetFunction, @Nonnull SFunction<T, L> subGetFunction) {
        return execute(nodes, HashSet::new, () -> instance(refIdGetFunction, idGetFunction, subGetFunction).treeToSet(nodes));
    }

    public static <T> Set<T> treeToSet(@Nonnull Collection<T> nodes, @Nonnull String refIdField,
                                       @Nonnull String idField, @Nonnull String subField) {
        return execute(nodes, HashSet::new, () -> TreeUtil.<T>instance((Class<T>) nodes.iterator().next().getClass(), refIdField, idField, subField).treeToSet(nodes));
    }

    public static <T, R, L extends Collection> List<T> collectionToTree(@Nonnull Collection<T> nodes, @Nonnull SFunction<T, R> refIdGetFunction,
                                                                        @Nonnull SFunction<T, R> idGetFunction, @Nonnull SFunction<T, L> subGetFunction) {
        return execute(nodes, ArrayList::new, () -> instance(refIdGetFunction, idGetFunction, subGetFunction).collectionToTree(nodes));
    }

    public static <T, R, L extends Collection, RESULT extends Collection> RESULT collectionToTree(@Nonnull Collection<T> nodes, @Nonnull SFunction<T, R> refIdGetFunction,
                                                                                                  @Nonnull SFunction<T, R> idGetFunction, @Nonnull SFunction<T, L> subGetFunction, Class<RESULT> resCollectionType) {

        return execute(nodes, () -> {
            try {
                return (RESULT) collectionSupplier(resCollectionType);
            } catch (NoSuchMethodException e) {
                throw new TreeException(e);
            }
        }, () -> TreeUtil.<T, R, L>instance(refIdGetFunction, idGetFunction, subGetFunction).collectionToTree(nodes, resCollectionType));
    }

    public static <T> List<T> collectionToTree(@Nonnull Collection<T> nodes, @Nonnull String refIdField,
                                               @Nonnull String idField, @Nonnull String subField) {
        return execute(nodes, ArrayList::new, () -> TreeUtil.<T>instance((Class<T>) nodes.iterator().next().getClass(), refIdField, idField, subField).collectionToTree(nodes));
    }

    public static <T, RESULT extends Collection> RESULT collectionToTree(@Nonnull Collection<T> nodes, @Nonnull String refIdField,
                                                                         @Nonnull String idField, @Nonnull String subField, Class<RESULT> resCollectionType) {

        return execute(nodes, () -> {
            try {
                return (RESULT) collectionSupplier(resCollectionType);
            } catch (NoSuchMethodException e) {
                throw new TreeException(e);
            }
        }, () -> TreeUtil.<T>instance((Class<T>) nodes.iterator().next().getClass(), refIdField, idField, subField).collectionToTree(nodes, resCollectionType));
    }

    public static <T> T collectionToTreeRoot(@Nonnull Collection<T> nodes, @Nonnull String refIdField,
                                             @Nonnull String idField, @Nonnull String subField) {
        return treeRoot(() -> collectionToTree(nodes, refIdField, idField, subField));
    }

    public static <T, R, L extends Collection> T collectionToTreeRoot(@Nonnull Collection<T> nodes, @Nonnull SFunction<T, R> refIdGetFunction,
                                                                        @Nonnull SFunction<T, R> idGetFunction, @Nonnull SFunction<T, L> subGetFunction) {
        return treeRoot(() -> collectionToTree(nodes, refIdGetFunction, idGetFunction, subGetFunction));
    }

    private static <T> T treeRoot(Supplier<List<T>> supplier) {
        List<T> result = supplier.get();
        if (result == null || result.size() != 1) {
            throw new TreeException("Transfer result is exceptional.");
        }
        return result.get(0);
    }


    private static <T> Instance<T> instance(Class<T> clazz, String refIdField, String idField, String subField) {
        String cacheKey = instanceName(clazz.getName(), refIdField, idField, subField);
        return (Instance<T>) INSTANCE_MAP.computeIfAbsent(cacheKey, s -> new Instance<>(clazz, new ReflectName(refIdField), new ReflectName(idField), new ReflectName(subField)));
    }

    private static <T, R, L extends Collection> Instance<T> instance(SFunction<T, R> refIdGetFunction, SFunction<T, R> idGetFunction,
                                                                     SFunction<T, L> subGetFunction) {
        SerializedLambda serializedLambda = SerializedLambdaUtils.getFunctionalInterfaceSerializedLambda(refIdGetFunction);
        String className = serializedLambda.getImplClass().replace("/", ".");
        String cacheKey = instanceName(className, refIdGetFunction, idGetFunction, subGetFunction);
        return (Instance<T>) INSTANCE_MAP.computeIfAbsent(cacheKey, key -> {
            Class<T> clazz;
            try {
                clazz = (Class<T>) Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new TreeException(e);
            }

            return new Instance<>(clazz, new ReflectName(refIdGetFunction), new ReflectName(idGetFunction), new ReflectName(subGetFunction));
        });
    }


    private static String instanceName(String clazzName, String refIdField, String idField, String subField) {
        return String.format("%s-%s-%s-%s", clazzName, refIdField, idField, subField);
    }

    private static <T, R, L extends Collection> String instanceName(String clazzName, SFunction<T, R> refIdGetFunction, SFunction<T, R> idGetFunction,
                                                                    SFunction<T, L> subGetFunction) {

        return instanceName(clazzName, getFieldNameBySerializedLambda(refIdGetFunction).getField(),
                getFieldNameBySerializedLambda(idGetFunction).getField(), getFieldNameBySerializedLambda(subGetFunction).getField());
    }

    private static <T, R> ReflectName getFieldNameBySerializedLambda(SFunction<T, R> sf) {
        return FUNCTION_REFLECT_NAME_MAP.computeIfAbsent(sf, sFunction -> new ReflectName(sf));
    }

    private static <T, RESULT extends Collection> RESULT execute(Collection<T> nodes, Supplier<RESULT> emptySupplier, Supplier<RESULT> supplier) {
        return nodes.size() == 0 ? emptySupplier.get() : supplier.get();
    }

    private static Supplier<? extends Collection> collectionSupplier(Class<? extends Collection> clazz) throws NoSuchMethodException {
        switch (clazz.getSimpleName()) {
            case "Collection":
            case "List":
                return ArrayList::new;
            case "Set":
                return HashSet::new;
            default:
                Constructor<? extends Collection> constructor = clazz.getConstructor();
                return () -> {
                    try {
                        return constructor.newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        throw new TreeException(e);
                    }
                };
        }
    }

    private static class Instance<T> {
        private final static Object DEFAULT_PARENT_KEY = new Object();


        private Function<T, ?> GET_REF_ID;
        private Function<T, ?> GET_ID;
        private Function<T, ?> GET_SUBS;
        private BiConsumer<T, Collection> SET_SUBS;
        private Supplier<? extends Collection> NEW_SUB;

        private Instance(@Nonnull Class<T> clazz, @Nonnull ReflectName refIdReflectName, @Nonnull ReflectName idReflectName, @Nonnull ReflectName subReflectName) {
            // check the getter of filed name
            try {
                Method refIdGetter = clazz.getMethod(refIdReflectName.getGetter());
                Method idGetter = clazz.getMethod(idReflectName.getGetter());
                Method subGetter = clazz.getMethod(subReflectName.getGetter());
                Method subSetter = getSubSetterAndSetNewSubSupplier(subReflectName, clazz);
                GET_REF_ID = getFieldValueByGetter(refIdGetter);
                GET_ID = getFieldValueByGetter(idGetter);
                GET_SUBS = getFieldValueByGetter(subGetter);
                SET_SUBS = (ele, collection) -> {
                    try {
                        subSetter.invoke(ele, collection);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new TreeException(e);
                    }
                };
                return;
            } catch (NoSuchMethodException ignore) {
            }


            try {
                Field refIdField = clazz.getDeclaredField(refIdReflectName.field);
                if (!refIdField.isAccessible()) {
                    refIdField.setAccessible(true);
                }
                Field idField = clazz.getDeclaredField(idReflectName.getField());
                if (!idField.isAccessible()) {
                    idField.setAccessible(true);
                }
                Field subField = clazz.getDeclaredField(subReflectName.getField());
                if (!subField.isAccessible()) {
                    subField.setAccessible(true);
                }
                GET_REF_ID = getFieldValueByField(refIdField);
                GET_ID = getFieldValueByField(idField);
                GET_SUBS = getFieldValueByField(subField);
                SET_SUBS = (obj, collection) -> {
                    try {
                        subField.set(obj, collection);
                    } catch (IllegalAccessException e) {
                        throw new TreeException(e);
                    }
                };
                //noinspection unchecked
                Class<? extends Collection> type = (Class<? extends Collection>) subField.getType();
                setNewSubSupplier(type);
            } catch (NoSuchFieldException | NoSuchMethodException e) {
                throw new TreeException("Find field failed! ", e);
            }
        }


        public List<T> treeToList(Collection<T> nodes) {
            List<T> result = new ArrayList<>();
            treeToCollection(result, nodes);
            return result;
        }

        public Set<T> treeToSet(Collection<T> nodes) {
            Set<T> result = new HashSet<>();
            treeToCollection(result, nodes);
            return result;
        }

        private void treeToCollection(Collection<T> result, Collection<T> nodes) {
            Queue<T> queue = new LinkedList<>(nodes);
            T ele;
            while ((ele = queue.poll()) != null) {
                result.add(ele);
                Collection<T> collection = (Collection<T>) GET_SUBS.apply(ele);
                collection.forEach(queue::offer);
                collection.clear();
            }
        }

        public List<T> collectionToTree(Collection<T> collection) {
            return collectionToTree(collection, List.class);
        }

        public <R extends Collection> R collectionToTree(Collection<T> collection, Class<R> clazz) {
            Map<?, T> idObjMap = collection.stream().collect(Collectors.toMap(GET_ID, Function.identity(), (t, t2) -> {
                throw new TreeException("Duplicate key! ");
            }));
            Map<?, ? extends Set<?>> refIdMap = collection.stream().collect(Collectors.groupingBy(obj -> {
                Object parentId = GET_REF_ID.apply(obj);
                return parentId == null ? DEFAULT_PARENT_KEY : parentId;
            }, Collectors.mapping(GET_ID, Collectors.toSet())));

            Set<? extends Map.Entry<?, ? extends Set<?>>> entries = refIdMap.entrySet();
            List<Object> parentIds = new ArrayList<>();
            for (Map.Entry<?, ? extends Set<?>> entry : entries) {
                Object refId = entry.getKey();
                T obj = idObjMap.get(refId);
                Collection subs = NEW_SUB.get();
                boolean objIsNull = obj == null;
                if (DEFAULT_PARENT_KEY.equals(refId) || objIsNull) {
                    parentIds.addAll(entry.getValue());
                }
                if (!objIsNull) {
                    subs.addAll(entry.getValue().stream().map(idObjMap::get).collect(Collectors.toList()));
                    SET_SUBS.accept(obj, subs);
                }
            }
            R result = null;
            try {
                result = (R) collectionSupplier(clazz).get();
                for (Object parentId : parentIds) {
                    result.add(idObjMap.get(parentId));
                }
            } catch (NoSuchMethodException ignore) {
            }
            return result;
        }

        private Method getSubSetterAndSetNewSubSupplier(ReflectName subReflectName, Class<T> clazz) throws NoSuchMethodException {
            Method[] methods = clazz.getMethods();
            String subSetterName = subReflectName.getSetter();
            for (Method method : methods) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (subSetterName.equals(method.getName()) && parameterTypes.length == 1 && Collection.class.isAssignableFrom(parameterTypes[0])) {
                    //noinspection unchecked
                    setNewSubSupplier((Class<? extends Collection>) parameterTypes[0]);
                    return method;
                }
            }
            throw new NoSuchMethodException();
        }

        private void setNewSubSupplier(Class<? extends Collection> clazz) throws NoSuchMethodException {
            NEW_SUB = collectionSupplier(clazz);
        }

        private Function<T, ?> getFieldValueByGetter(Method m) {
            return obj -> {
                try {
                    return m.invoke(obj);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new TreeException(e);
                }
            };
        }

        private Function<T, ?> getFieldValueByField(Field field) {
            return obj -> {
                try {
                    return field.get(obj);
                } catch (IllegalAccessException e) {
                    throw new TreeException(e);
                }
            };
        }
    }

    /**
     * SFunction获取序列化能力
     */
    @FunctionalInterface
    public interface SFunction<T, R> extends Function<T, R>, Serializable {
    }


    private static class ReflectName {
        private final String field;
        private final String getter;
        private final String setter;

        public <T, R> ReflectName(SFunction<T, R> getter) {
            // get class and method info from serializedLambda
            SerializedLambda serializedLambda = SerializedLambdaUtils.getFunctionalInterfaceSerializedLambda(getter);
            String className = serializedLambda.getImplClass().replace("/", ",");
            String getterName = serializedLambda.getImplMethodName();

            Class<?> eleType = SerializedLambdaUtils.getParameterTypes(getter, serializedLambda)[0];
            Class<?> paramType = SerializedLambdaUtils.getReturnType(serializedLambda);

            String setterName;
            try {
                //  check getter
                if (!getterName.startsWith("get")) {
                    throw new TreeException(className + "#" + getterName + " is not a standard getter! Should start with 'get' ");
                }
                Method getterMethod = eleType.getMethod(getterName);
                // check setter
                setterName = getterName.replaceFirst("get", "set");
                Method setterMethod = eleType.getMethod(setterName, paramType);
                if (!setterMethod.getReturnType().isAssignableFrom(void.class)) {
                    throw new RuntimeException("Error setter return type.");
                }
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

            String fieldName = getterName.substring(3, 4).toLowerCase() + getterName.substring(4);
            this.getter = getterName;
            this.setter = setterName;
            this.field = fieldName;
        }

        public ReflectName(String field) {
            this.field = field;
            String upperCaseStartName = field.substring(0, 1).toUpperCase() + field.substring(1);
            this.getter = "get" + upperCaseStartName;
            this.setter = "set" + upperCaseStartName;
        }

        public String getField() {
            return field;
        }

        public String getGetter() {
            return getter;
        }


        public String getSetter() {
            return setter;
        }

    }

    public static class TreeException extends RuntimeException {
        public TreeException() {
            super();
        }

        public TreeException(String message) {
            super(message);
        }

        public TreeException(String message, Throwable cause) {
            super(message, cause);
        }

        public TreeException(Throwable cause) {
            super(cause);
        }

        protected TreeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
