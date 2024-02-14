package br.com.implgetway.api.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferencePaymentMethodsRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class MercadoPagoService {

    public String createCheckout() throws MPException, MPApiException{

        MercadoPagoConfig.setAccessToken("TEST-6588139419036949-020912-42e70f317b1ccb35ccf70b43bcbeefb9-1639440789");
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(createItems)
                .payer(createPayer)
                .backUrls(PreferenceBackUrlsRequest.builder()
                        .success("https://localhost:8080/checkout/sucess")
                        .pending("https://localhost:8080/checkout/pending")
                        .failure("https://localhost:8080/checkout/failure")
                        .build())
                .notificationUrl("https://localhost:8080/checkout/webhook")
                .paymentMethods(PreferencePaymentMethodsRequest.builder()
                        .defaultInstallments(1)
                        .installments(6)
                        .build())
                .statementDescriptor("MeuNegocioTest")
                .externalReference("Reference_1234")
                .expirationDateFrom(OffsetDateTime.now())
                .expirationDateTo(OffsetDateTime.now().plusDays(1))
                .expires(true)
                .binaryMode(true)
                .build();
        PreferenceClient client = new PreferenceClient();
        Preference resultset = client.create(preferenceRequest);

        // Printa o url do pagamento
        System.out.println(resultset.getInitPoint());
        return resultset.getId();
    }
}
