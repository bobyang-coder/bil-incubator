package com.bil.account.controller.page;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * home
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2022/01/26
 */
@Controller
public class HomeController {

    @ApiOperation("首页")
    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }
}
