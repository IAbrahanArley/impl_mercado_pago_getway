package br.com.implgetway.api.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoPagoService {

    public String createCheckout() throws MPException, MPApiException{

        MercadoPagoConfig.setAccessToken("TEST-6588139419036949-020912-42e70f317b1ccb35ccf70b43bcbeefb9-1639440789");
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(createItems())
                .payer(createPayer())
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

    private List<PreferenceItemRequest> createItems(){
        List<PreferenceItemRequest> items = new ArrayList<>();

        PreferenceItemRequest preferenceItemRequest = PreferenceItemRequest.builder()
                .id("Item-D143F")
                .title("Macbook Air")
                .description("Notebook Macbook Air Apple, Tela de Retina 13, M2, 8GB RAM, CPU 8 Núcleos, GPU 8 Núcleos, SSD 256GB, Cinza Espacial - MLXW3BZ/A")
                .categoryId("Notebook")
                .quantity(1)
                .unitPrice(BigDecimal.valueOf(9000.00))
                .currencyId("BRL")
                .pictureUrl("https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/macbook-air-space-gray-select-201810?wid=452&hei=420&fmt=jpeg&qlt=95&.v=1664472289661")
                .build();
        items.add(preferenceItemRequest);
        return items;

    }

    private PreferencePayerRequest createPayer(){

        PreferencePayerRequest payer = PreferencePayerRequest.builder()
                .name("Fulano")
                .surname("Pagador Test")
                .email("Fulano@pagador.com")
                .phone(PhoneRequest.builder()
                        .areaCode("83")
                        .number("991754322")
                        .build())
                .identification(IdentificationRequest.builder()
                        .type("CPF")
                        .number("12345678909")
                        .build())
                .address(AddressRequest.builder()
                        .streetName("")
                        .streetName("")
                        .zipCode("")
                        .build())
                .build();

        return payer;
    }
}
