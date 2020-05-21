package com.example.ebook.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.example.ebook.dto.InvestOrderDTO;
import com.example.ebook.enums.InvestChannelEnum;
import com.example.ebook.enums.OrderTypeEnum;
import com.example.ebook.exception.ResultCode;
import com.example.ebook.response.ResponseResult;
import com.example.ebook.service.AlipayService;
import com.example.ebook.util.OrderCodeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author pxx
 * Date 2020/3/20 15:29
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("alipay")
public class PayController {
	
	@Autowired
	private AlipayService alipayService;
	
	@PostMapping("go")
	public Object goPay(@RequestBody InvestOrderDTO investOrderDTO) throws Exception {
		
		Integer channel;
		if (investOrderDTO.getType().equals(InvestChannelEnum.ALIPAY.getType())) {
			channel = InvestChannelEnum.ALIPAY.getType();
		}else {
			channel = InvestChannelEnum.WEIXIN.getType();
		}
		String outTradeNo = OrderCodeFactory.genSerialNum(
				OrderTypeEnum.MONEY_INVEST.getType(),
				channel,
				investOrderDTO.getUserId()
				);
		String subject = investOrderDTO.getSubject();
		Double money = investOrderDTO.getMoney();
		String pay;
		if (investOrderDTO.getType().equals(InvestChannelEnum.ALIPAY.getType())) {
			 pay = alipayService.webPagePay(outTradeNo, money, subject);
		}else {
			pay = "weixin";
		}
		System.out.println(pay);
		return new ResponseResult<>(ResultCode.CLICK_OK, pay);
	}
	
	/**
	 * app 订单支付
	 */
	@GetMapping("getAppPagePay")
	public Object getAppPagePay() throws Exception{
		/** 模仿数据库，从后台调数据*/
		String outTradeNo = "131233";
		Integer totalAmount = 1000;
		String subject = "天猫超市012";
		
		String pay = alipayService.appPagePay(outTradeNo, totalAmount, subject);
		
		Object json = JSONObject.toJSON(pay);
		
		System.out.println(json);
		
		return new ResponseResult<>(ResultCode.CLICK_OK, json);
	}
	/**
	 * 交易查询
	 */
	@PostMapping("aipayQuery")
	public Object alipayQuery() throws Exception{
		/**调取支付订单号*/
		String outTradeNo = "13123";
		
		String query = alipayService.query(outTradeNo);
		
		Object json = JSONObject.toJSON(query);
        
        /*JSONObject jObject = new JSONObject();
        jObject.get(query);*/
		return new ResponseResult<>(ResultCode.CLICK_OK, json);
	}
	
	/**
	 * 退款
	 * @throws AlipayApiException
	 */
	@GetMapping("alipayRefund")
	public Object alipayRefund(
			@RequestParam("outTradeNo")String outTradeNo,
			@RequestParam(value = "outRequestNo", required = false)String outRequestNo,
			@RequestParam(value = "refundAmount", required = false)Integer refundAmount
	) throws AlipayApiException {
		
		/** 调取数据*/
		//String outTradeNo = "15382028806591197";
		String refundReason = "用户不想购买";
		//refundAmount = 1;
		//outRequestNo = "22";
		
		String refund = alipayService.refund(outTradeNo, refundReason, refundAmount, outRequestNo);
		
		System.out.println(refund);
		
		return new ResponseResult<>(ResultCode.CLICK_OK,refund);
	}
	
	/**
	 * 退款查询
	 * @throws AlipayApiException
	 */
	@PostMapping("refundQuery")
	public Object refundQuery() throws AlipayApiException{
		
		/** 调取数据*/
		String outTradeNo = "13123";
		String outRequestNo = "2";
		
		String refund = alipayService.refundQuery(outTradeNo, outRequestNo);
		
		return new ResponseResult<>(ResultCode.CLICK_OK,refund);
		
	}
	
	/**
	 * 交易关闭
	 * @throws AlipayApiException
	 */
	@PostMapping("alipayclose")
	public Object alipaycolse() throws AlipayApiException{
		
		/** 调取数据*/
		String outTradeNo = "13123";
		
		String close = alipayService.close(outTradeNo);
		
		return new ResponseResult<>(ResultCode.CLICK_OK, close);
	}
	
	@PostMapping("notify")
	public void alipayNotify(HttpServletRequest request, HttpServletResponse response) {
		alipayService.paymentCallback(request,response);
	}
}
