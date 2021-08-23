package com.bil.uid.spring.config;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * Created by bob on 2020/3/29.
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @version v1.0.0
 * @since 2020/3/29
 */
public class NamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("annotation-driven", new AnnotationDrivenBeanDefinitionParser());
    }

    public static class AnnotationDrivenBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

        public static final String BEAN_NAME = "uidClientAnnotationBeanPostProcessor";

        @Override
        protected String getBeanClassName(Element element) {
            return UidClientAnnotationBeanPostProcessor.class.getName();
        }

        @Override
        protected String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext) {
            return BEAN_NAME;
        }

        @Override
        protected void doParse(Element element, BeanDefinitionBuilder builder) {
            if ("annotation-driven".equals(element.getLocalName())) {
                String order = element.getAttribute("order");
                if (StringUtils.hasLength(order)) {
                    builder.addPropertyValue("order", Integer.parseInt(order));
                }
            }
        }
    }
}
