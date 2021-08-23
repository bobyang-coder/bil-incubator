package com.bil.uid.client.service;

import com.bil.uid.api.UidService;
import com.bil.uid.client.config.DubboConfig;

import java.util.ResourceBundle;

/**
 * dubbo service 服务发现
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @version v1.0.0
 * @since 2020/3/29
 */
public class DubboServiceDiscover implements ServiceDiscover<UidService> {

    private UidService uidService;

    public DubboServiceDiscover() {
        uidService = loadDubboConfig().build().get();
    }

    public DubboConfig<UidService> loadDubboConfig() {
        ResourceBundle bundle = ResourceBundle.getBundle("uid-config.properties");
        return DubboConfig.load(bundle, UidService.class);
    }

    @Override

    public UidService get() {
        return uidService;
    }
}
