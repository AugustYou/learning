package com.felix.act.learning.utils;

import com.felix.act.learning.exception.ErrorCode;
import com.felix.act.learning.exception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: felix.
 * @createTime: 2017/9/21.
 */
public class PageUtils {

    private static Logger logger = LoggerFactory.getLogger(PageUtils.class);

    public static void check(int page, int pageSize) {
        if (!(page > 0) || !(pageSize > 0)) {
            logger.error("分页参数错误 page={} , pageSize={}", page, pageSize);
            throw new MyException(ErrorCode.ERROR_ARGS, "分页参数错误");
        }
    }
}
