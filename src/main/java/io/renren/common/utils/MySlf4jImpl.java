package io.renren.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.Log;

//自定义sql的输出级别不然debug干扰太多
@Slf4j
public class MySlf4jImpl implements Log {
    //这个构造函数绝对不能少 不然 java.lang.NoSuchMethodException: io.renren.common.utils.MySlf4jImpl
    public MySlf4jImpl(String clazz) {
        // Do Nothing
    }
    @Override
    public boolean isDebugEnabled() {
        //return false;
        // 将debug级别输出权限改成info级别
        return log.isInfoEnabled();
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public void error(String s, Throwable throwable) {

    }

    @Override
    public void error(String s) {

    }

    @Override
    public void debug(String s) {
        // debug日志输出成info级别日志
        log.info(s);
        //log.info("debug: {}", s);
    }

    @Override
    public void trace(String s) {

    }

    @Override
    public void warn(String s) {

    }
}
