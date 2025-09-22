package com.ibrahimkvlci.ecommerce.payment.utils.KuveytTurkUtil;

public interface KuveytTurkXmlUtil {

    String cardCheckXmlPost(String okUrl, String failUrl, String hashData, String cardNumber, String cardExpireDateYear, String cardExpireDateMonth, String cardCVV2, String cardHolderName, String cardType,String amount,String displayAmount,String merchantOrderId, String deviceChannel, String clientIp, String billAddrCity, String billAddrCountry, String billAddrLine1, String BillAddrPostCode, String billAddrState, String email, String cc, String Subscriber);
}
