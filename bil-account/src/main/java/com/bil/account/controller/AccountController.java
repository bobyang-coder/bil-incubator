package com.bil.account.controller;

import com.bil.account.engine.AccountEngine;
import com.bil.account.model.base.AccountResponse;
import com.bil.account.model.entity.Account;
import com.bil.account.model.entity.AccountFlow;
import com.bil.account.model.param.AccountTransferReq;
import com.bil.account.service.AccountFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 账户相关接口
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2021/08/23
 */
@RestController
@RequestMapping("account")
@Api("账户相关接口")
public class AccountController {

    @Resource
    private AccountEngine accountEngine;
    @Resource
    private AccountFlowService accountFlowService;

    @ApiOperation("查询账户")
    @GetMapping("find-account")
    public AccountResponse<Account> findAccount(@RequestParam("accountNo") String accountNo) {
        return AccountResponse.success(accountEngine.findAccount(accountNo));
    }

    @ApiOperation("开账户")
    @GetMapping("open-account")
    public AccountResponse<Account> openAccount(@RequestParam("objectNo") String objectNo,
                                                @RequestParam("accountTypeCode") int accountTypeCode) {
        return AccountResponse.success(accountEngine.openAccount(objectNo, accountTypeCode));
    }

    @ApiOperation("账户转账")
    @PostMapping("transfer-account")
    public AccountResponse<String> transfer(@RequestBody AccountTransferReq transferReq) {
        return AccountResponse.success(accountEngine.transfer(transferReq));
    }

    @ApiOperation("查询账务流水")
    @PostMapping("query-account-flow")
    public AccountResponse<List<AccountFlow>> queryAccountFlow(@RequestParam("accountNo") String accountNo) {
        return AccountResponse.success(accountFlowService.queryAccountFlow(accountNo));
    }
}
