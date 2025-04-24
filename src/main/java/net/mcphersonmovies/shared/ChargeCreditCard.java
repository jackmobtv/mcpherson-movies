package net.mcphersonmovies.shared;

import java.math.BigDecimal;
import java.math.RoundingMode;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.CreateTransactionController;
import net.authorize.api.controller.base.ApiOperationBase;

public class ChargeCreditCard {
    public static void main(String[] args) {
        run(
            25.00,
            new String[]{"6011000000000012","1225","999"},
            "test@test.test"
        );
    }

    public static String run(Double amount, String[] creditCardInfo, String email) {
        String apiLoginId = Config.getEnv("AUTHORIZE_NET_API_KEY");
        String transactionKey = Config.getEnv("AUTHORIZE_NET_TRANSACTION_KEY");

        // Set the request to operate in either the sandbox or production environment
        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        // Create object with merchant authentication details
        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);

        // Populate the payment data
        PaymentType paymentType = new PaymentType();
        CreditCardType creditCard = new CreditCardType();
        creditCard.setCardNumber(creditCardInfo[0]);
        creditCard.setExpirationDate(creditCardInfo[1]);
        creditCard.setCardCode(creditCardInfo[2]);
        paymentType.setCreditCard(creditCard);

        // Set email address (optional)
        CustomerDataType customer = new CustomerDataType();
        customer.setEmail(email);

        // Create the payment transaction object
        TransactionRequestType txnRequest = new TransactionRequestType();
        txnRequest.setTransactionType(TransactionTypeEnum.AUTH_CAPTURE_TRANSACTION.value());
        txnRequest.setPayment(paymentType);
        txnRequest.setCustomer(customer);
        txnRequest.setAmount(new BigDecimal(amount).setScale(2, RoundingMode.CEILING));

        // Create the API request and set the parameters for this specific request
        CreateTransactionRequest apiRequest = new CreateTransactionRequest();
        apiRequest.setMerchantAuthentication(merchantAuthenticationType);
        apiRequest.setTransactionRequest(txnRequest);

        // Call the controller
        CreateTransactionController controller = new CreateTransactionController(apiRequest);
        controller.execute();

        // Get the response
        CreateTransactionResponse response = new CreateTransactionResponse();
        response = controller.getApiResponse();

        // Parse the response to determine results
        if (response!=null) {
            // If API Response is OK, go ahead and check the transaction response
            if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {
                TransactionResponse result = response.getTransactionResponse();
                if (result.getMessages() != null) {
                    return "Successfully created transaction";
                } else {
                    return response.getTransactionResponse().getErrors().getError().get(0).getErrorText();
                }
            } else {
                if (response.getTransactionResponse() != null && response.getTransactionResponse().getErrors() != null) {
                    return response.getTransactionResponse().getErrors().getError().get(0).getErrorText();
                } else {
                    return response.getMessages().getMessage().get(0).getText();
                }
            }
        } else {
            ANetApiResponse errorResponse = controller.getErrorResponse();
            if (!errorResponse.getMessages().getMessage().isEmpty()) {
                String errorCode = errorResponse.getMessages().getMessage().get(0).getCode();
                String errorMsg = errorResponse.getMessages().getMessage().get(0).getText();
                if(errorCode.equals("E00003")) {
                    if(errorMsg.contains("cardNumber")) {
                        return "The credit card number is invalid";
                    } else if(errorMsg.contains("expirationDate")) {
                        return "The expiration date is invalid";
                    } else if(errorMsg.contains("cardCode")) {
                        return "The security code is invalid";
                    } else {
                        return "An error occurred. Please try again later.";
                    }
                } else {
                    return errorMsg;
                }
            }
        }
        return "An error occurred. Please try again later.";
    }
}
