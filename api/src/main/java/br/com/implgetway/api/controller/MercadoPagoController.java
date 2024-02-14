package br.com.implgetway.api.controller;

import br.com.implgetway.api.service.MercadoPagoService;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
public class MercadoPagoController {

    @Autowired
    private MercadoPagoService paymentService;

    @GetMapping
    public ResponseEntity<String> createPaymentPreference() throws MPException, MPApiException{
        return ResponseEntity.ok().body(paymentService.createCheckout());
    }

    @GetMapping("/sucess")
    public String sucess(){
        return "sucess";
    }
    @GetMapping("/failure")
    public String fail(){
        return "fail";
    }

    @GetMapping("/pending")
    public String pending(){
        return "pending";
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhookNotification(@RequestBody String payload) {

        System.out.println(payload);

        return ResponseEntity.ok().build();
    }
}
