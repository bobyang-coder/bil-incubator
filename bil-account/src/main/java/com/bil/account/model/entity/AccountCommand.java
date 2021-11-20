package com.bil.account.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 记账指令表
 *
 * @author haibo.yang
 * @since 2021/11/21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_command")
public class AccountCommand {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 记账指令号
     */
    @Column(name = "command_no")
    private String commandNo;

    /**
     * 记账交易号
     */
    @Column(name = "command_trade_no")
    private String commandTradeNo;

    /**
     * 记账指令类型
     */
    @Column(name = "command_type")
    private String commandType;
    /**
     * 记账指令备注
     */
    @Column(name = "command_note")
    private String commandNote;


    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 版本号
     */
    @Column(name = "version")
    private String version;
}
