package com.example.ebook.service;

import com.alipay.api.AlipayApiException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author pxx
 * Date 2020/3/20 15:40
 * @Description
 */
@Service
public interface AlipayService {
	
	/**
	 * web端订单支付
	 * @param outTradeNo    订单编号（唯一）
	 * @param totalAmount   订单价格
	 * @param subject       商品名称
	 */
	String webPagePay(String outTradeNo, Double totalAmount, String subject) throws Exception;
	
	/**
	 * app端订单支付
	 * @param outTradeNo    订单编号
	 * @param totalAmount   订单价格
	 * @param subject       商品名称
	 */
	String appPagePay(String outTradeNo,Integer totalAmount,String subject) throws Exception;
	
	/**
	 * 退款
	 * @param outTradeNo    订单编号
	 * @param refundReason  退款原因
	 * @param refundAmount  退款金额
	 * @param outRequestNo  标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
	 */
	String refund(String outTradeNo,String refundReason,Integer refundAmount,String outRequestNo) throws AlipayApiException;
	
	/**
	 * 交易查询
	 * @param outTradeNo 订单编号（唯一）
	 */
	String query(String outTradeNo) throws AlipayApiException;
	
	/**
	 * 交易关闭
	 * @param outTradeNo（唯一）
	 */
	String close(String outTradeNo) throws AlipayApiException;
	
	/**
	 * 退款查询
	 * @param outTradeNo 订单编号（唯一）
	 * @param outRequestNo 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
	 */
	String refundQuery(String outTradeNo,String outRequestNo) throws AlipayApiException;
	
	/**
	 * 支付回调
	 * @param request
	 * @param response
	 */
	void paymentCallback(HttpServletRequest request, HttpServletResponse response);
}
