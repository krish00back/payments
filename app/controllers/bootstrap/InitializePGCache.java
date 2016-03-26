package controllers.bootstrap;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import gateway.lifecycle.PaymentGatewayCache;
import models.PaymentGatewayInfo;
import play.exceptions.BaseException;
import services.serviceimpl.ServicesFactory;

@Named
@Singleton
public class InitializePGCache {
	
	@Inject
	ServicesFactory servicesFactory;

	@PostConstruct
	void initializeCache() {
		try {
			PaymentGatewayInfo pgInfo = new PaymentGatewayInfo();
			/*
			 * Merchant key : gtKFFx
Salt : eCwWELxi
CardName: Any name
CardNumber: 5123456789012346
CVV: 123
Expiry: May 2017
			 */
			pgInfo.key = "gtKFFx";
			pgInfo.salt = "eCwWELxi";
			pgInfo.pgUrl = "https://test.payu.in/_payment";
			pgInfo.pgId = "111";
			pgInfo.callbackUrl = "";
			pgInfo.pgName = "";
			pgInfo.status = "";
			servicesFactory.paymentGatewayInfoService.insertIntoPaymentGateway(pgInfo);
			
			List<PaymentGatewayInfo> pgInfos = servicesFactory.paymentGatewayInfoService.allPaymentGateways();
			PaymentGatewayCache.getInstance().initializePaymentGatewayCache(pgInfos);
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
}
