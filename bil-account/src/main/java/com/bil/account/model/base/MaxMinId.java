package com.bil.account.model.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 最大最小id
 *
 * @author haibo.yang
 * @since 2022/1/28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaxMinId {
    private Long minId;
    private Long maxId;
}
