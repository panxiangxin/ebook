package com.example.ebook.enums;

/**
 * @author pxx
 * Date 2020/3/21 10:40
 * @Description
 */
public enum InvestChannelEnum {
	/**
	 * 1.支付宝
	 * 2.微信支付
	 * 3.消费
	 */
	ALIPAY(1),
	WEIXIN(2),
	BOUGHT(3);
	private Integer type;
	
	InvestChannelEnum(Integer type) {
		this.type = type;
	}
	
	public Integer getType() {
		return type;
	}
}
