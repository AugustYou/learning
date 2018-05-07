package top.felixu.framework.utils;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @Author felixu
 * @Date 2018/5/7
 */
public class Try {

    public static <T, R> Function<T, R> of(UncheckedFunction<T, R> mapper) {
        Objects.requireNonNull(mapper);
        return t -> {
            try {
                return mapper.apply(t);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    public static<T> Consumer<T> of(UncheckedConsumer<T> mapper) {
        Objects.requireNonNull(mapper);
        return t -> {
            try {
                mapper.accept(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    public static interface UncheckedConsumer<T> {

        /**
         * Consumer抛异常
         * @param t
         * @throws Exception
         */
        void accept(T t) throws Exception;
    }

    public static interface UncheckedFunction<T, R> {

        /**
         * Function抛异常
         * @param t
         * @return
         * @throws Exception
         */
        R apply(T t) throws Exception;
    }
}
