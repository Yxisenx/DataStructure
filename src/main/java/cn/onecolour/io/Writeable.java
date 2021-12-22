package cn.onecolour.io;

import java.io.IOException;

/**
 * @author yang
 * @date 2021/12/16
 * @description
 */
public interface Writeable<T> {
    boolean write(T t, String fullPath) throws IOException;
}
