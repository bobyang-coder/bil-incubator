package com.bil.uid.spring.annotation;

import com.bil.uid.spring.config.UidClientAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Created by bob on 2020/3/29.
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @version v1.0.0
 * @since 2020/3/29
 */
@Configuration
public class UidClientConfiguration implements ImportAware {
    protected AnnotationAttributes enableIncrementer;

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        this.enableIncrementer = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableUidClient.class.getName(), false));
        if (this.enableIncrementer == null) {
            throw new IllegalArgumentException("@EnableUid is not present on importing class " + importMetadata.getClassName());
        }
    }

    @Bean
    public UidClientAnnotationBeanPostProcessor incrementerClientAnnotationBeanPostProcessor() {
        Assert.notNull(this.enableIncrementer, "@EnableUid annotation metadata was not injected");
        UidClientAnnotationBeanPostProcessor bpp = new UidClientAnnotationBeanPostProcessor();
        String order = this.enableIncrementer.getString("order");
        if (StringUtils.hasLength(order)) {
            bpp.setOrder(Integer.parseInt(order));
        }
        return bpp;
    }
}
