package com.example.ebook.enums;

/**
 * @author pxx
 * Date 2020/3/21 10:37
 * @Description
 */
public enum OrderTypeEnum {
	/**
	 * 1.充值订单
	 * 2.购买订单
	 */
	MONEY_INVEST(1),
	BUY_BOOK(2);
	private Integer type;
	
	OrderTypeEnum(Integer type) {
		this.type = type;
	}
	
	public Integer getType() {
		return type;
	}
}
