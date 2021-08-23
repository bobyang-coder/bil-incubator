package com.bil.uid;

import com.bil.uid.client.UidGenerator;
import com.bil.uid.spring.annotation.EnableUidClient;
import com.bil.uid.spring.annotation.UidClient;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by bob on 2020/3/29.
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @version v1.0.0
 * @since 2020/3/29
 */
@EnableUidClient(order = "1")
@Configuration
public class UidSpringTest {

    @Test
    public void testXml() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        UidSpringTest bean = context.getBean(UidSpringTest.class);
        System.out.println(bean.uidGenerator);
    }

    @Test
    public void testAnnotationEnableUid() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(UidSpringTest.class);
        context.refresh();
        UidSpringTest bean = context.getBean(UidSpringTest.class);
        System.out.println(bean.uidGenerator);
        System.out.println(bean.uidGenerator.next());
        System.out.println(bean.uidGenerator.batchNext(2));
    }


    @UidClient(bizTag = "uidGenerator")
    private UidGenerator uidGenerator;
}
