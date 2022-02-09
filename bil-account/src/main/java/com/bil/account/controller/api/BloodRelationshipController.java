package com.bil.account.controller.api;

import com.alibaba.excel.EasyExcel;
import com.bil.account.dao.BloodRelationshipRepository;
import com.bil.account.model.chart.BloodRelationshipData;
import com.bil.account.model.entity.BloodRelationship;
import com.bil.account.model.param.BloodRelationshipExportVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 血缘关系相关接口
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2022/02/09
 */
@RestController
@RequestMapping("blood-relationship")
@Api("血缘关系相关接口")
public class BloodRelationshipController {

    @Resource
    private BloodRelationshipRepository bloodRelationshipRepository;

    @ApiOperation("导入血缘关系")
    @GetMapping("export-bookkeeping")
    public void export() {
        File file = new File("/Users/bob/Documents/workspace/github/bil-incubator/bil-account/doc/blood_relationship/血缘关系-模板.xlsx");
        List<BloodRelationshipExportVo> reqList = EasyExcel.read(file, BloodRelationshipExportVo.class, null)
                .doReadAllSync();
        List<BloodRelationship> list = reqList.stream().map(BloodRelationshipExportVo::to).collect(Collectors.toList());
        bloodRelationshipRepository.saveAll(list);
    }

    @ApiOperation("查询血缘关系")
    @GetMapping("query-blood-relationship-data")
    public BloodRelationshipData queryBloodRelationshipData() {
        HashMap<String, Integer> tagAndDepthMap = Maps.newHashMap();
        tagAndDepthMap.put("标准化", 1);
        tagAndDepthMap.put("对账", 2);
        tagAndDepthMap.put("报表", 3);

        List<BloodRelationship> relationshipList = bloodRelationshipRepository.findAll();
        Map<String, Integer> nodeNameAndDepthMap = Maps.newHashMap();
        List<BloodRelationshipData.Link> links = Lists.newArrayList();
        for (BloodRelationship e : relationshipList) {
            nodeNameAndDepthMap.put(e.getNode(), tagAndDepthMap.getOrDefault(e.getTag(), 0));
            if (!nodeNameAndDepthMap.containsKey(e.getSource())) {
                nodeNameAndDepthMap.put(e.getSource(), 0);
            }
            links.add(new BloodRelationshipData.Link(e.getSource(), e.getNode(), 0.1f));
        }
        List<BloodRelationshipData.Node> nodes = nodeNameAndDepthMap.entrySet().stream()
                .map(e -> new BloodRelationshipData.Node(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
        return BloodRelationshipData.builder()
                .nodes(nodes)
                .links(links)
                .build();
    }
}
