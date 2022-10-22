package cn.onecolour.algorithms.search.array;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

public interface ArraySearch {


    /**
     * 搜索数组, 获得元素index, -1 表示未在数组中找到该元素
     *
     * @param array     数组
     * @param x 待搜索元素
     */
    int getIndex(int[] array, int x);

    default void checkArr(int[] array) {
        Objects.requireNonNull(array, "Array cannot be null!");
    }

    static int getIndex(int[] array, int x, SearchType searchType) {
        return SearchType.getInstance(searchType).getIndex(array, x);
    }

    enum SearchType {
        LinearSearch(cn.onecolour.algorithms.search.array.LinearSearch.class), // 线性搜索
        BinarySearch(cn.onecolour.algorithms.search.array.BinarySearch.class), // 二分搜索
        JumpSearch(cn.onecolour.algorithms.search.array.JumpSearch.class), // 跳跃搜索
        InterpolationSearch(cn.onecolour.algorithms.search.array.InterpolationSearch.class), // 插值搜索
        ExponentialSearch(cn.onecolour.algorithms.search.array.ExponentialSearch.class), // 指数搜索
        FibonacciSearch(cn.onecolour.algorithms.search.array.FibonacciSearch.class); // 斐波拉契搜索
        public final Class<? extends ArraySearch> clazz;
        private static final Map<String, ArraySearch> SEARCH_MAP = new HashMap<>();

        private static final BiFunction<String, Class<? extends ArraySearch>, ArraySearch> BI_FUNCTION = (name, clazz) -> {
            ArraySearch arraySearch = SEARCH_MAP.get(name);
            if (arraySearch == null) {
                synchronized (SearchType.class) {
                    arraySearch = SEARCH_MAP.get(name);
                    if (arraySearch == null) {
                        Constructor<? extends ArraySearch> constructor;
                        try {
                            constructor = clazz.getConstructor();
                            arraySearch = constructor.newInstance();
                        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException("No args constructor execute failed.");
                        }

                        SEARCH_MAP.put(name, arraySearch);
                    }
                }
            }
            return arraySearch;
        };

        SearchType(Class<? extends ArraySearch> clazz) {
            this.clazz = clazz;
        }

        public static ArraySearch getInstance(SearchType searchType) {
            Class<? extends ArraySearch> clazz = Objects.requireNonNull(searchType, "Search type cannot be null.").getClazz();
            return BI_FUNCTION.apply(searchType.name(), clazz);
        }

        public Class<? extends ArraySearch> getClazz() {
            return clazz;
        }
    }
}
