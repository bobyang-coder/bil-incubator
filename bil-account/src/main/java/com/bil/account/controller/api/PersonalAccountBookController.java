package com.bil.account.controller.api;

import com.alibaba.excel.EasyExcel;
import com.bil.account.contants.BookConstants.BookkeepingType;
import com.bil.account.engine.AccountEngine;
import com.bil.account.model.param.AccountTransferReq;
import com.bil.account.model.param.PersonalBookkeepingReq;
import com.bil.account.utils.BookkeepingUtils;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * 个人账本账户相关接口
 * - 基于账务系统进行记账
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2022/01/13
 */
@RestController
@RequestMapping("personal-account-book")
@Api("个人账本账户相关接口")
public class PersonalAccountBookController {

    @Resource
    private AccountEngine accountEngine;

    /**
     * 导入记账
     */
    @GetMapping("export-bookkeeping")
    public void exportBookkeeping() {
        File file = new File("/Users/bob/bozige.xls");
        List<PersonalBookkeepingReq> reqList = EasyExcel.read(file, PersonalBookkeepingReq.class, null)
                .doReadAllSync();
        for (PersonalBookkeepingReq req : reqList) {
            BookkeepingType bookkeepingType = BookkeepingType.findByCode(req.getBookkeepingType());
            AccountTransferReq transferReq = BookkeepingUtils.build(req.getTradeDate(), bookkeepingType.getD(),
                    bookkeepingType.getC(), req.getAmount(), req.getNote());
            accountEngine.transfer(transferReq);
        }
    }
}
