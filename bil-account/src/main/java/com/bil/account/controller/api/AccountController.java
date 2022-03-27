package com.bil.account.controller.api;

import com.bil.account.engine.AccountEngine;
import com.bil.account.model.base.EncryptReq;
import com.bil.account.model.base.Response;
import com.bil.account.model.entity.Account;
import com.bil.account.model.entity.AccountFlow;
import com.bil.account.model.param.AccountTransferReq;
import com.bil.account.service.AccountFlowService;
import com.bil.account.utils.des.Decryptable;
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
    @PostMapping("find-account")
    public Response<Account> findAccount(@Decryptable EncryptReq<String> accountNo) {
        return Response.success(accountEngine.findAccount(accountNo.getData()));
    }

    @ApiOperation("开账户")
    @GetMapping("open-account")
    public Response<Account> openAccount(@RequestParam("objectNo") String objectNo,
                                         @RequestParam("accountTypeCode") int accountTypeCode) {
        return Response.success(accountEngine.openAccount(objectNo, accountTypeCode));
    }

    @ApiOperation("账户转账")
    @PostMapping("transfer-account")
    public Response<String> transfer(@RequestBody AccountTransferReq transferReq) {
        return Response.success(accountEngine.transfer(transferReq));
    }

    @ApiOperation("查询账务流水")
    @PostMapping("query-account-flow")
    public Response<List<AccountFlow>> queryAccountFlow(@RequestParam("accountNo") String accountNo) {
        return Response.success(accountFlowService.queryAccountFlow(accountNo));
    }
}
