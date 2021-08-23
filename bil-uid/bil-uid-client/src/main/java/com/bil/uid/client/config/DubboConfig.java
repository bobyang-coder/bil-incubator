package com.bil.uid.client.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import lombok.Data;

import java.util.ResourceBundle;

/**
 * Created by bob on 2020/3/29.
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @version v1.0.0
 * @since 2020/3/29
 */
@Data
public class DubboConfig<T> {

    private String zkAddr;

    private String group;

    private Class<T> interfaceClass;

    private String url = null;

    private String version;

    private Integer timeout;


    public static <T> DubboConfig<T> load(ResourceBundle bundle, Class<T> interfaceClass) {
        DubboConfig<T> config = new DubboConfig<>();
        config.setInterfaceClass(interfaceClass);
        config.setZkAddr(bundle.getString("zkAddr"));
        config.setGroup(bundle.getString("group"));
        config.setUrl(bundle.getString("url"));
        config.setVersion(bundle.getString("version"));
        if (bundle.containsKey("timeout")) {
            config.setTimeout(Integer.parseInt(bundle.getString("timeout")));
        }
        return config;
    }


    public ReferenceConfig<T> build() {
        ReferenceConfig<T> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setVersion(version);
        referenceConfig.setInterface(interfaceClass);
        if (timeout != null) {
            referenceConfig.setTimeout(timeout);
        }
        referenceConfig.setRegistry(buildRegistryConfig());
        referenceConfig.setApplication(buildApplicationConfig());
        return referenceConfig;
    }


    public RegistryConfig buildRegistryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress(zkAddr);
        registryConfig.setGroup(group);
        return registryConfig;
    }

    public ApplicationConfig buildApplicationConfig() {
        return new ApplicationConfig("bil-uid-client-app");
    }
}
