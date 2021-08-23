package com.bil.account.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 账户表
 *
 * @author bob
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account {

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 账户号
	 */
	@Column(name = "account_no")
	private String accountNo;

	/**
	 * 账户名称
	 */
	@Column(name = "account_name")
	private String accountName;

	/**
	 * 账户类型
	 */
	@Column(name = "account_type")
	private String accountType;

	/**
	 * 余额
	 */
	@Column(name = "balance")
	private Long balance;

	/**
	 * 币种(1、人民币 、积分 3、代金券 4、美元 5、台币)
	 */
	@Column(name = "currency")
	private String currency;

	/**
	 * 是否可透支，true 可透支，false 不可透支
	 */
	@Column(name = "overdraft")
	private String overdraft;

	/**
	 * 借贷方向(D:debit - 借记账户， C:credit - 贷记账户)
	 */
	@Column(name = "direction")
	private String direction;

	/**
	 * 会计科目
	 */
	@Column(name = "accounting_subjects")
	private String accountingSubjects;

	/**
	 * 账户级别
	 */
	@Column(name = "account_level")
	private String accountLevel;

	/**
	 * 账户状态: norm - 正常， freeze - 冻结 , closed - 关闭
	 */
	@Column(name = "status")
	private String status;

	/**
	 * 对象号
	 */
	@Column(name = "object_no")
	private String objectNo;

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
