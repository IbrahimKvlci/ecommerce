package com.ibrahimkvlci.ecommerce.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AkbankPaymentResultRequest {

    private String txnCode;

    private String responseCode;

    private String responseMessage;

    private String hostResponseCode;

    private String orderId;

    private String secureEcomInd;

    private String mdStatus;

    private String hostMessage;

    private String txnDateTime;

    private String merchantSafeId;

    private String terminalSafeId;

    private String cardHolderName;

    private String authCode;

    private String rrn;

    private String batchNumber;

    private String stan;

    private String amount;

    private String okUrl;

    private String failUrl;

    private String installCount;

    private String additionalInstallCount;

    private String deferingMonth;

    private String ccbEarnedRewardAmount;

    private String ccbBalanceRewardAmount;

    private String ccbRewardDesc;

    private String pcbEarnedRewardAmount;

    private String pcbBalanceRewardAmount;

    private String pcbRewardDesc;

    private String xcbEarnedRewardAmount;

    private String xcbBalanceRewardAmount;

    private String xcbRewardDesc;

    private String hashParams;

    private String hash;
}
