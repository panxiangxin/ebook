package com.example.ebook.service.Impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.example.ebook.config.AlipayConfig;
import com.example.ebook.mapper.StampOrderMapper;
import com.example.ebook.mapper.UserMapper;
import com.example.ebook.model.StampOrder;
import com.example.ebook.model.User;
import com.example.ebook.service.AlipayService;
import com.example.ebook.util.OrderCodeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author pxx
 * Date 2020/3/20 15:43
 * @Description
 */
@Slf4j
@Service
public class AlipayServiceImpl implements AlipayService {
	
	@Autowired
	private StampOrderMapper stampOrderMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	/** 调取支付宝接口 web端支付*/
	DefaultAlipayClient alipayClient = new DefaultAlipayClient(
			AlipayConfig.GATEWAYURL, AlipayConfig.APP_ID, AlipayConfig.MERCHANT_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
	
	/** 调取支付宝接口app端支付*/
	AlipayClient alipayClients = new DefaultAlipayClient(
			AlipayConfig.GATEWAYURL, AlipayConfig.APP_ID, AlipayConfig.MERCHANT_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
	
	
	@Override
	public String webPagePay(String outTradeNo, Double totalAmount, String subject) throws Exception {
		
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		/** 同步通知，支付完成后，支付成功页面*/
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		/** 异步通知，支付完成后，需要进行的异步处理*/
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
		
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ outTradeNo +"\","
											+ "\"total_amount\":\""+ totalAmount +"\","
											+ "\"subject\":\""+ subject +"\","
											+ "\"body\":\"商品名称\","
											+ "\"timeout_express\":\"90m\","
											+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		
		/**转换格式*/
		return alipayClient.pageExecute(alipayRequest).getBody().replace('\"','\'').replace('\n',' ');
	}
	
	@Override
	public String refund(String outTradeNo,String refundReason,Integer refundAmount,String outRequestNo) throws AlipayApiException {
		AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
		
		/** 调取接口*/
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ outTradeNo +"\","
											+ "\"refund_amount\":\""+ refundAmount +"\","
											+ "\"refund_reason\":\""+ refundReason +"\","
											+ "\"out_request_no\":\""+ outRequestNo +"\"}");
		String result = alipayClient.execute(alipayRequest).getBody();
		return result;
	}
	
	@Override
	public String query(String outTradeNo) throws AlipayApiException {
		AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
		/**请求接口*/
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ outTradeNo +"\","+"\"trade_no\":\""+ "" +"\"}");
		/**转换格式*/
		String result = alipayClient.execute(alipayRequest).getBody();
		return result;
	}
	
	@Override
	public String close(String outTradeNo) throws AlipayApiException {
		AlipayTradeCloseRequest alipayRequest = new AlipayTradeCloseRequest();
		
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ outTradeNo +"\"," +"\"trade_no\":\""+ "" +"\"}");
		
		String result = alipayClient.execute(alipayRequest).getBody();
		
		return result;
	}
	
	@Override
	public String refundQuery(String outTradeNo , String outRequestNo) throws AlipayApiException {
		AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();
		
		/** 请求接口*/
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ outTradeNo +"\","
											+"\"out_request_no\":\""+ outRequestNo +"\"}");
		
		/** 格式转换*/
		String result = alipayClient.execute(alipayRequest).getBody();
		
		return result;
	}
	
	@Override
	public String appPagePay(String outTradeNo, Integer totalAmount, String subject) throws Exception {
		AlipayTradeWapPayRequest alipayRequest=new AlipayTradeWapPayRequest();
		
		/** 同步通知，支付完成后，支付成功页面*/
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		/** 异步通知，支付完成后，需要进行的异步处理*/
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
		
		/**销售产品码（固定）*/
		String productCode="QUICK_WAP_WAY";
		
		/** 进行赋值 */
		AlipayTradeWapPayModel wapPayModel=new AlipayTradeWapPayModel();
		wapPayModel.setOutTradeNo(outTradeNo);
		wapPayModel.setSubject(subject);
		wapPayModel.setTotalAmount(totalAmount.toString());
		wapPayModel.setBody("商品名称");
		wapPayModel.setTimeoutExpress("2m");
		wapPayModel.setProductCode(productCode);
		alipayRequest.setBizModel(wapPayModel);
		
		/** 格式转换*/
		String result = alipayClients.pageExecute(alipayRequest).getBody().replace('\"','\'').replace('\n',' ');
		return result;
	}
	/**
	 *支付回调
	 * @param request
	 * @param response
	 */
	@Transactional
	@Override
	public void paymentCallback(HttpServletRequest request, HttpServletResponse response) {
		log.info("---------------------------支付宝进入异步通知--------------------------");
		String resultFail = "fail";
		//获取支付宝GET过来反馈信息
		Map<String, String> params = new HashMap<>();
		Map requestParams = request.getParameterMap();
//		log.info("返回的map----------------" + requestParams);
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		
//		log.info("params={}", params);
		//调用SDK验证签名
		boolean signVerified = false;
		try {
			signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGN_TYPE);
		} catch (AlipayApiException e) {
			log.error("【支付宝异步通知】支付宝回调通知失败 e={} params={}", e, params);
			responseBody(response, resultFail);
			return;
		}
		if (!signVerified) {
			log.error("【支付宝异步通知】验证签名错误 params={} ", params);
			responseBody(response, resultFail);
			return;
		}
		BigDecimal trade_price = new BigDecimal(request.getParameter("total_amount"));
		//商户订单号
		String out_trade_no = params.get("out_trade_no");
		//支付宝交易号
		String trade_no = params.get("trade_no");
		//交易状态
		String trade_status = params.get("trade_status");
		// 支付成功修改订单状态
		if ("TRADE_FINISHED".equals(trade_status) || "TRADE_SUCCESS".equals(trade_status)) {
			//业务处理，主要是更新订单状态
			long channel = OrderCodeFactory.getShortParam(out_trade_no);
//			log.info("channel: {}", channel);
			long type = OrderCodeFactory.getServiceType(out_trade_no);
//			log.info("type: {}", type);
			long userId = OrderCodeFactory.getLongParam(out_trade_no);
//			log.info("userId: {}", userId);
			//订单入库操作
			StampOrder stampOrder = new StampOrder();
			stampOrder.setSecondId(trade_no);
			stampOrder.setType((int)type);
			stampOrder.setChannel((int)channel);
			stampOrder.setMoney(trade_price.doubleValue());
			stampOrder.setGmtCreate(System.currentTimeMillis());
			stampOrder.setId(out_trade_no);
			stampOrder.setUserId(userId);
			stampOrderMapper.insert(stampOrder);
			log.info("-----------------------订单入库成功--------------------------");
			//用户积分增加
			User user = userMapper.selectByPrimaryKey(userId);
			user.setStamps(user.getStamps() + trade_price.doubleValue());
			userMapper.updateByPrimaryKeySelective(user);
			log.info("------------------------用户充值积分增加----------------------");
			log.info("---------------------------支付成功--------------------------");
		}
		responseBody(response, resultFail);
	}
	
	private void responseBody(HttpServletResponse response, String contentBody) {
		try {
			response.setContentType("type=text/javascript;charset=UTF-8");
			String s = contentBody;
			response.getWriter().write(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
