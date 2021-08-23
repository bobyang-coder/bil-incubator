package com.bil.uid.spring.config;

import com.bil.uid.client.DefaultUidGenerator;
import com.bil.uid.client.UidGenerator;
import com.bil.uid.spring.annotation.UidClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by bob on 2020/3/29.
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @version v1.0.0
 * @since 2020/3/29
 */
public class UidClientAnnotationBeanPostProcessor implements BeanPostProcessor, Ordered, BeanFactoryAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(UidClientAnnotationBeanPostProcessor.class);

    /**
     * 默认最低
     */
    private int order = Ordered.LOWEST_PRECEDENCE;

    private BeanFactory beanFactory;

    private Map<ClientInfo, UidGenerator> cached = new ConcurrentHashMap<>();


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        parseFields(bean);
        parseMethods(bean);
        return bean;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * Resolve the specified value if possible.
     *
     * @see ConfigurableBeanFactory#resolveEmbeddedValue
     */
    private String resolve(String value) {
        if (this.beanFactory != null && this.beanFactory instanceof ConfigurableBeanFactory) {
            return ((ConfigurableBeanFactory) this.beanFactory).resolveEmbeddedValue(value);
        }
        return value;
    }


    private void parseFields(Object bean) {
        if (bean == null) {
            return;
        }
        Class<?> clazz = bean.getClass();
        parseFields(bean, clazz);
        while (clazz.getSuperclass() != null && !Object.class.equals(clazz.getSuperclass())) {
            clazz = clazz.getSuperclass();
            parseFields(bean, clazz);
        }
    }

    private void parseFields(Object bean, Class<?> clazz) {
        if (clazz == null) {
            return;
        }
        for (Field field : clazz.getDeclaredFields()) {
            try {
                UidClient annotation = AnnotationUtils.getAnnotation(field, UidClient.class);
                if (annotation == null) {
                    continue;
                }

                String uidClientName = annotation.bizTag();
                if (!StringUtils.hasText(uidClientName)) {
                    throw new RuntimeException("uidClientName 必须指定. bean: " + bean + ", field: " + field);
                }

                Class<?> fieldType = field.getType();
                if (fieldType.equals(UidGenerator.class)) {
                    uidClientName = resolve(uidClientName);
                    LOGGER.warn("bean: {}, field: {} 上发现 @UidClient 注解. incrementerName: {}", bean, field, uidClientName);
                    ClientInfo clientInfo = new ClientInfo(annotation.timeout(), uidClientName);

                    UidGenerator incrementer = cached.computeIfAbsent(clientInfo, s -> new DefaultUidGenerator());
                    invokeSetField(field, bean, incrementer);
                    continue;
                }

                throw new RuntimeException("未知字段类型无法初始化. bean: " + bean + ", field: " + field);
            } catch (Throwable e) {
                throw new RuntimeException("初始化字段失败. bean: " + bean + ", field: " + field, e);
            }
        }
    }


    private void parseMethods(Object bean) {
        if (bean == null) {
            return;
        }
        Class<?> clazz = bean.getClass();
        parseMethods(bean, clazz);
        while (clazz.getSuperclass() != null && !Object.class.equals(clazz.getSuperclass())) {
            clazz = clazz.getSuperclass();
            parseMethods(bean, clazz);
        }
    }

    private void parseMethods(Object bean, Class<?> clazz) {
        if (clazz == null) {
            return;
        }
        for (Method method : clazz.getDeclaredMethods()) {
            try {
                UidClient annotation = AnnotationUtils.getAnnotation(method, UidClient.class);
                if (annotation == null) {
                    continue;
                }

                String uidClientName = annotation.bizTag();
                if (!StringUtils.hasText(uidClientName)) {
                    throw new RuntimeException("uidClientName 必须指定. bean: " + bean + ", method: " + method);
                }

                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1) {
                    throw new RuntimeException("@UidClient 只能应用于Bean的Setter方法. bean: " + bean + ", method: " + method);
                }

                Class<?> parameterType = parameterTypes[0];
                if (parameterType.equals(UidGenerator.class)) {
                    uidClientName = resolve(uidClientName);
                    LOGGER.warn("bean: {}, method: {} 上发现 @UidClient 注解. incrementerName: {}", bean, method, uidClientName);
                    ClientInfo clientInfo = new ClientInfo(annotation.timeout(), uidClientName);

                    UidGenerator uidGenerator = cached.computeIfAbsent(clientInfo, s -> new DefaultUidGenerator());
                    invokeSetMethod(method, bean, uidGenerator);
                    continue;
                }

                throw new RuntimeException("未知字段类型无法初始化. bean: " + bean + ", method: " + method);
            } catch (Throwable e) {
                throw new RuntimeException("初始化字段失败. bean: " + bean + ", method: " + method, e);
            }
        }
    }


    private void invokeSetMethod(Method method, Object bean, Object param) {
        ReflectionUtils.makeAccessible(method);
        ReflectionUtils.invokeMethod(method, bean, param);
    }

    private void invokeSetField(Field field, Object bean, Object param) {
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, bean, param);
    }


    /**
     * {@link UidClient} 对象封装
     */
    public static class ClientInfo {
        /**
         * 业务标识
         */
        private String bizTag;

        /**
         * 超时时间
         */
        private Integer timeout;

        public ClientInfo(Integer timeout, String bizTag) {
            this.timeout = timeout;
            this.bizTag = bizTag;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ClientInfo clientInfo = (ClientInfo) o;
            return Objects.equals(bizTag, clientInfo.bizTag) &&
                    Objects.equals(timeout, clientInfo.timeout);
        }

        @Override
        public int hashCode() {
            return Objects.hash(bizTag, timeout);
        }


    }
}
