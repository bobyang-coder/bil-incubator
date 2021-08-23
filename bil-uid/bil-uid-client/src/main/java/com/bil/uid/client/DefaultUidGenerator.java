package com.bil.uid.client;

import com.bil.uid.api.UidService;
import com.bil.uid.client.service.DubboServiceDiscover;
import com.bil.uid.client.service.ServiceDiscover;

import java.util.List;

/**
 * Created by bob on 2020/3/29.
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @version v1.0.0
 * @since 2020/3/29
 */
public class DefaultUidGenerator implements UidGenerator {

    private ServiceDiscover<UidService> discover;

    public DefaultUidGenerator() {
        discover = new DubboServiceDiscover();
    }

    @Override
    public long next() {
        return discover.get().nextRange("").removeAndGet();
    }

    @Override
    public List<Long> batchNext(int batchSize) {
        return discover.get().nextRange("", batchSize).toList();
    }
}
