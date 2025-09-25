package com.ibrahimkvlci.ecommerce.payment.utils.KuveytTurkUtil;

import org.springframework.stereotype.Component;

@Component
public class KuveytTurkXmlUtilImpl implements KuveytTurkXmlUtil{

    @Override
    public String cardCheckXmlPost(String okUrl, String failUrl, String hashData, String cardNumber,
            String cardExpireDateYear, String cardExpireDateMonth, String cardCVV2, String cardHolderName,
            String cardType, String amount, String displayAmount, String merchantOrderId, String deviceChannel,
            String clientIp, String billAddrCity, String billAddrCountry, String billAddrLine1, String BillAddrPostCode,
            String billAddrState, String email, String cc, String Subscriber) {

        String template = """
                <KuveytTurkVPosMessage xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns:xsd="http://www.w3.org/2001/XMLSchema">
<APIVersion>TDV2.0.0</APIVersion>
<OkUrl>%s</OkUrl>
<FailUrl>%s</FailUrl>
<HashData>%s</HashData>
<MerchantId>496</MerchantId>
<CustomerId>400235</CustomerId>
<DeviceData>
 <DeviceChannel>%s</DeviceChannel>
 <ClientIP>%s</ClientIP>
 </DeviceData>
<CardHolderData>
<BillAddrCity>%s</BillAddrCity>
<BillAddrCountry>%s</BillAddrCountry>
<BillAddrLine1>%s</BillAddrLine1>
<BillAddrPostCode>%s</BillAddrPostCode>
<BillAddrState>%s</BillAddrState>
 <Email>%s</Email>
 <MobilePhone>
 <Cc>%s</Cc>
 <Subscriber>%s</Subscriber>
 </MobilePhone>
 </CardHolderData>
<UserName>apitest</UserName>
<CardNumber>%s</CardNumber>
<CardExpireDateYear>%s</CardExpireDateYear>
<CardExpireDateMonth>%s</CardExpireDateMonth>
<CardCVV2>%s</CardCVV2>
<CardHolderName>%s</CardHolderName>
<CardType>%s</CardType>
<TransactionType>Sale</TransactionType>
<InstallmentCount>0</InstallmentCount>
<Amount>%s</Amount>
<DisplayAmount>%s</DisplayAmount>
<CurrencyCode>0949</CurrencyCode>
<MerchantOrderId>%s</MerchantOrderId>
<TransactionSecurity>3</TransactionSecurity>
</KuveytTurkVPosMessage>
                """;

        String res = template.formatted(
                okUrl,
                failUrl,
                hashData,
                deviceChannel,
                clientIp,
                billAddrCity,
                billAddrCountry,
                billAddrLine1,
                BillAddrPostCode,
                billAddrState,
                email,
                cc,
                Subscriber,
                cardNumber,
                cardExpireDateYear,
                cardExpireDateMonth,
                cardCVV2,
                cardHolderName,
                cardType,
                amount,
                displayAmount,
                merchantOrderId);

        return res;
    }


}
