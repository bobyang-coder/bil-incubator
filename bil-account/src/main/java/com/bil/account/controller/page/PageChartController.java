package com.bil.account.controller.page;

import com.alibaba.fastjson.JSON;
import com.bil.account.contants.AccountConstants.AccountType;
import com.bil.account.contants.AccountConstants.AccountingSubject;
import com.bil.account.model.chart.AccountDcData;
import com.bil.account.model.chart.LineChartData;
import com.bil.account.model.chart.PieChart;
import com.bil.account.model.chart.PieChartData;
import com.bil.account.model.entity.Account;
import com.bil.account.model.entity.AccountFlow;
import com.bil.account.service.AccountFlowService;
import com.bil.account.service.AccountService;
import com.bil.account.utils.AmountUtils;
import com.bil.account.utils.BookkeepingUtils;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.LocalDate;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.bil.account.utils.AmountUtils.fen2Yuan;

/**
 * 账务图表页面
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2022/01/22
 */
@Controller
@RequestMapping("chart")
public class PageChartController {

    @Resource
    private AccountFlowService accountFlowService;
    @Resource
    private AccountService accountService;

    @ApiOperation("查询账户流水变化图表")
    @GetMapping("account-flow-change-chart")
    public String queryAccountFlowChangeChart(Model model) {
        //query data
        Collection<Account> accountList = accountService.queryByObjectNo(BookkeepingUtils.OBJECT_NO);
        accountList = CollectionUtils.emptyIfNull(accountList);
        //resp
        List<LineChartData> dataList = accountList.stream()
                .sorted(Comparator.comparing(Account::getAccountType))
                .map(this::buildLineChartData)
                .collect(Collectors.toList());
        model.addAttribute("dataList", JSON.toJSONString(dataList));
        return "chart/account-flow-change-chart";
    }


    @ApiOperation("查询账户余额图表")
    @GetMapping("account-balance-chart")
    public String queryAccountBalanceChart(Model model) {
        Collection<Account> accountList = accountService.queryByObjectNo(BookkeepingUtils.OBJECT_NO);
        accountList = CollectionUtils.emptyIfNull(accountList);
        //all
        List<PieChartData> dataList = accountList.stream()
                .map(account -> PieChartData.builder()
                        .name(AccountType.findByCode(account.getAccountType()).getName())
                        .value(fen2Yuan(account.getBalance()))
                        .build()
                ).collect(Collectors.toList());
        //当前负债
        List<PieChartData> liabilityList = accountList.stream()
                .filter(e -> AccountingSubject.LIABILITY.equals(AccountType.findByCode(e.getAccountType()).getAccountingSubject().getRootAccountingSubject()))
                .map(account -> PieChartData.builder()
                        .name(AccountType.findByCode(account.getAccountType()).getName())
                        .value(fen2Yuan(account.getBalance()))
                        .build()
                ).collect(Collectors.toList());
        //当前资产
        List<PieChartData> assertList = accountList.stream()
                .filter(e -> AccountingSubject.ASSET.equals(AccountType.findByCode(e.getAccountType()).getAccountingSubject().getRootAccountingSubject()))
                .map(account -> PieChartData.builder()
                        .name(AccountType.findByCode(account.getAccountType()).getName())
                        .value(fen2Yuan(account.getBalance()))
                        .build()
                ).collect(Collectors.toList());
        //resp
//        model.addAttribute("dataList", JSON.toJSONString(Lists.newArrayList(dataList, assertList, liabilityList)));
        model.addAttribute("dataList", JSON.toJSONString(dataList));
        model.addAttribute("liabilityList", JSON.toJSONString(liabilityList));
        model.addAttribute("assertList", JSON.toJSONString(assertList));
        return "chart/account-balance-pie-chart";
    }


    @ApiOperation("查询账户借贷金额")
    @GetMapping("query-account-dc-amount")
    public String queryAccountDcData(Model model) {
        Collection<AccountDcData> list = CollectionUtils.emptyIfNull(accountFlowService.queryAccountDcData());
        List<PieChart> dataList = list.stream().map(e -> {
            Account account = accountService.findByAccountNo(e.getAccountNo());
            return PieChart.builder()
                    .titleName(account.getAccountName())
                    .dataList(
                            Lists.newArrayList(
                                    PieChartData.builder().name("借").value(fen2Yuan(e.getDAmount())).build(),
                                    PieChartData.builder().name("贷").value(fen2Yuan(e.getCAmount())).build()
                            )
                    ).build();
        }).collect(Collectors.toList());
        model.addAttribute("dataList", JSON.toJSONString(dataList));
        return "chart/account-dc-account-pie-chart";
    }

    public LineChartData buildLineChartData(Account account) {
        AccountType type = AccountType.findByCode(account.getAccountType());
        Assert.notNull(type, "账户类型非法");
        //query data
        List<AccountFlow> flowList = accountFlowService.queryAccountFlow(account.getAccountNo());
        Map<Integer, Pair<Long, Long>> map = CollectionUtils.emptyIfNull(flowList).stream()
                .sorted(Comparator.comparing(AccountFlow::getId))
                .collect(Collectors.toMap(
                        a -> Integer.valueOf(LocalDate.fromDateFields(a.getTradeTime()).toString(ISODateTimeFormat.basicDate())),
                        e -> Pair.of(AmountUtils.judgeTradeAmount(type, e.getCreditAmount(), e.getDebitAmount()), e.getBalance()),
                        (p1, p2) -> Pair.of(p1.getKey() + p2.getKey(), p2.getValue())
                ));
        List<Integer> dateList = Lists.newArrayList(map.keySet());
        dateList.sort(Integer::compareTo);
        List<String> amountList = dateList.stream()
                .map(e -> fen2Yuan(map.get(e).getKey()))
                .collect(Collectors.toList());
        List<String> balanceList = dateList.stream()
                .map(e -> fen2Yuan(map.get(e).getValue()))
                .collect(Collectors.toList());
        //resp
        return LineChartData.builder()
                .accountTypeName(type.getName())
                .dateList(dateList)
                .amountList(amountList)
                .balanceList(balanceList)
                .build();
    }
}
