package com.reapal.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author jack-cooper
 * @version 1.0.0
 * @ClassName SpringContextUtil.java
 * @Description 获取applicationContext
 * @createTime 2020年06月11日 17:24:00
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        applicationContext = ac;
    }
    public static ApplicationContext getContext() {
        return applicationContext;
    }

    public static void autowireBean(Object bean) {
        applicationContext.getAutowireCapableBeanFactory().autowireBean(bean);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }


}
