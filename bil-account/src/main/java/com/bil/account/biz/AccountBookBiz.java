package com.bil.account.biz;

import com.alibaba.excel.EasyExcel;
import com.bil.account.contants.BookConstants;
import com.bil.account.engine.AccountEngine;
import com.bil.account.model.param.AccountTransferReq;
import com.bil.account.model.param.PersonalBookkeepingReq;
import com.bil.account.service.AccountBookService;
import com.bil.account.utils.BookkeepingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;

/**
 * 账本服务
 *
 * @author haibo.yang
 * @since 2022/1/28
 */
@Component
@Slf4j
public class AccountBookBiz {
    @Resource
    private AccountEngine accountEngine;
    @Resource
    private AccountBookService accountBookService;

    /**
     * 导入记账
     *
     * @param inputStream
     */
    public void exportBookkeeping(InputStream inputStream) {
        List<PersonalBookkeepingReq> reqList = EasyExcel.read(inputStream, PersonalBookkeepingReq.class, null)
                .doReadAllSync();
        for (PersonalBookkeepingReq req : reqList) {
            BookConstants.BookkeepingType bookkeepingType = BookConstants.BookkeepingType.findByCode(req.getBookkeepingType());
            AccountTransferReq transferReq = BookkeepingUtils.build(req.getTradeDate(), bookkeepingType.getD(),
                    bookkeepingType.getC(), req.getAmount(), req.getNote());
            accountEngine.transfer(transferReq);
        }
    }

    /**
     * 清理账本
     */
    public void clearBookkeeping() {
        accountBookService.clearBookkeeping();
    }
}
