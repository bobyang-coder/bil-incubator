package com.bil.account.controller.api;

import com.bil.account.biz.AccountBookBiz;
import com.bil.account.model.base.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

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
    private AccountBookBiz accountBookBiz;


    @ApiOperation("导入记账")
    @PostMapping("export-bookkeeping")
    public Response<Void> exportBookkeeping(MultipartFile file) throws IOException {
        accountBookBiz.exportBookkeeping(file.getInputStream());
        return Response.OK();
    }

    @ApiOperation("一键清理账本")
    @GetMapping("clear-bookkeeping")
    public Response<Void> clearBookkeeping() {
        accountBookBiz.clearBookkeeping();
        return Response.OK();
    }
}
