package cn.onecolour.io;

import java.io.IOException;

/**
 * @author yang
 * @date 2021/12/16
 * @description
 */
public interface Readable<T> {
    T read(String fullPath) throws IOException;
}
