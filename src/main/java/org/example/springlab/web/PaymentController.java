package org.example.springlab.web;

import com.google.gson.Gson;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.example.springlab.models.Payment;
import org.example.springlab.models.Rental;
import org.example.springlab.models.User;
import org.example.springlab.models.Vehicle;
import org.example.springlab.services.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/api/payments")
public class PaymentController {

    private final Gson gson;
    private final VehicleServiceInterface vehicleService;
    private final UserServiceInterface userService;
    private final RentalServiceInterface rentalService;
    private final PaymentServiceInterface paymentService;
    @Value("${stripe.secret.key}")
    private String secretKey;


    public PaymentController(
            VehicleServiceInterface vehicleService,
            UserService userService,
            RentalServiceInterface rentalService,
            PaymentServiceInterface paymentService
    ) {
        this.gson = new Gson();
        this.vehicleService = vehicleService;
        this.userService = userService;
        this.rentalService = rentalService;
        this.paymentService = paymentService;
    }

    @GetMapping("/checkout.js")
    public String getCheckoutSessionJavascriptCode() {
        return "checkout.js";
    }

    @PostMapping("/create-checkout-session")
    @ResponseBody
    public String createCheckoutSession(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            String login = userDetails.getUsername();
            User user = userService.findByLogin(login);
            Rental rental = rentalService.findByUserIdAndReturnDateTimeIsNull(user.getId());
            Payment payment = paymentService.findByRentalIdAndDatePaidIsNull(rental.getId());
            Vehicle rentedVehicle = vehicleService.findById(rental.getVehicle().getId());
            StripeClient client = new StripeClient(secretKey);
            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setUiMode(SessionCreateParams.UiMode.EMBEDDED)
                            .setReturnUrl("http://localhost:8080/api/payment/finished")
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName(rentedVehicle.getBrand())
                                                                    .setDescription(rentedVehicle.getModel())
                                                                    .build()
                                                    )
                                                    .setCurrency("pln")
                                                    .setUnitAmountDecimal(BigDecimal.valueOf(payment.getAmount()))
                                                    .build()
                                            )
                                            .setQuantity(1L)
                                            .build()
                            )
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .build();
            Session session = client.checkout().sessions().create(params);
            Map<String, String> map = new HashMap<>();
            map.put("clientSecret", session.getClientSecret());
            return gson.toJson(map);
        } catch(StripeException e) {
            e.printStackTrace();
            return "";
        }
    }

    @GetMapping("/checkout-session")
    public String checkoutSession() {
       return "checkout.html";
    }
}
