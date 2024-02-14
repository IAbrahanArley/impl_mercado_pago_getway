package br.com.implgetway.api.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.preference.*;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.resources.preference.PreferencePaymentMethod;
import com.mercadopago.resources.preference.PreferencePaymentMethods;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoPagoService {



    public String createCheckout() throws MPException, MPApiException{

        MercadoPagoConfig.setAccessToken("TEST-2453313229452572-092911-e2a5b87ac71ba0c577c887a3ee599639-1160953381");
        MercadoPagoConfig.setIntegratorId("dev_24c65fb163bf11ea96500242ac130004");

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(createItems())
                .payer(createPayer())
                .backUrls(PreferenceBackUrlsRequest.builder()
                        .success("https://9328-2804-29b8-5086-886b-cbc6-a04-b7a5-1f08.ngrok-free.app/checkout/sucess")
                        .pending("https://9328-2804-29b8-5086-886b-cbc6-a04-b7a5-1f08.ngrok-free.app/checkout/pending")
                        .failure("https://9328-2804-29b8-5086-886b-cbc6-a04-b7a5-1f08.ngrok-free.app/checkout/failure")
                        .build())
                .notificationUrl("https://9328-2804-29b8-5086-886b-cbc6-a04-b7a5-1f08.ngrok-free.app/checkout/webhook")
                .paymentMethods(PreferencePaymentMethodsRequest.builder()
                        .defaultInstallments(1)
                        .installments(6)
                        .excludedPaymentMethods(getExcludedPaymentsMethod())
                        .build())
                .statementDescriptor("MeuNegocioTest")
                .externalReference("Abrahanarley@gmail.com")
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

    private List<PreferenceItemRequest> createItems(){
        List<PreferenceItemRequest> items = new ArrayList<>();

        PreferenceItemRequest preferenceItemRequest = PreferenceItemRequest.builder()
                .id("1234")
                .title("Macbook Air")
                .description("Dispositivo de loja de comércio eletrônico móvel")
                .categoryId("art")
                .quantity(1)
                .unitPrice(BigDecimal.valueOf(1500.00))
                .currencyId("BRL")
                .pictureUrl("https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/macbook-air-space-gray-select-201810?wid=452&hei=420&fmt=jpeg&qlt=95&.v=1664472289661")
                .build();
        items.add(preferenceItemRequest);
        return items;

    }

    private PreferencePayerRequest createPayer(){

        PreferencePayerRequest payer = PreferencePayerRequest.builder()
                .name("Lalo")
                .surname("Landa")
                .email("test_user_33467020@testuser.com")
                .phone(PhoneRequest.builder()
                        .areaCode("83")
                        .number("991754322")
                        .build())
                .identification(IdentificationRequest.builder()
                        .type("CPF")
                        .number("12345678909")
                        .build())
                .address(AddressRequest.builder()
                        .streetName("Rua Falsa 123")
                        .zipCode("58000-000")
                        .build())
                .build();

        return payer;
    }


    public List<PreferencePaymentMethodRequest> getExcludedPaymentsMethod() {
        List<PreferencePaymentMethodRequest> excludedPaymentsMethod = new ArrayList<>();
        excludedPaymentsMethod.add(PreferencePaymentMethodRequest.builder().id("visa").build());
        return excludedPaymentsMethod;
    }



}
