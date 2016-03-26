package gateway.lifecycle;

import java.util.HashMap;
import java.util.Map;

import gateway.bean.PaymentGatewayInfoBean;
import utilities.Constants;
import utilities.HashCalculator;

public class PayUPaymentGateway implements PaymentGateway{

	private static PaymentGatewayInfoBean pgInfo = PaymentGatewayCache.getInstance().paymentGatewayInfo(Constants.PG_ID_PAYU);

	@Override
	public Map<String, String> result(Map<String, String> request, String txnid) {
		Map<String, String> response = new HashMap<String, String>();
		String hash, s = "|";
		String key = pgInfo.key;
		String salt = pgInfo.salt;
		String amount = request.get("amount");
		String productinfo = request.get("productinfo");
		String firstname = request.get("firstname");
		String email = request.get("email");
		String phone = request.get("phone");
		String udf1 = request.get("udf1");
		String udf2 = request.get("udf2");
		String udf3 = request.get("udf3");
		String udf4 = request.get("udf4");
		String udf5 = request.get("udf5");
		String toHash = key + s + txnid + s + amount + s
				+ productinfo + s + firstname + s
				+ email + s + udf1 + s + udf2 + s
				+ udf3 + s + udf4 + s + udf5 + s
				+ s + s + s + s + s + salt;
		hash = HashCalculator.Sha512Digest(toHash);
		
		response.put("url", pgInfo.pgUrl);
		response.put("key", pgInfo.key);
		response.put("hash", hash);
		return response;
	}

}
