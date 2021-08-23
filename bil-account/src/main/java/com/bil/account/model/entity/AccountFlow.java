package com.bil.account.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 账务流水表
 *
 * @author bob
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_flow")
public class AccountFlow {

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 凭证号
	 */
	@Column(name = "voucher_no")
	private String voucherNo;

	/**
	 * 账户号
	 */
	@Column(name = "account_no")
	private String accountNo;

	/**
	 * 本期余额
	 */
	@Column(name = "balance")
	private Long balance;

	/**
	 * 期初余额
	 */
	@Column(name = "prefix_balance")
	private Long prefixBalance;

	/**
	 * 贷记金额
	 */
	@Column(name = "credit_amount")
	private Long creditAmount;

	/**
	 * 借记金额
	 */
	@Column(name = "debit_amount")
	private Long debitAmount;

	/**
	 * 对端账户号
	 */
	@Column(name = "opposite_account_no")
	private String oppositeAccountNo;

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
