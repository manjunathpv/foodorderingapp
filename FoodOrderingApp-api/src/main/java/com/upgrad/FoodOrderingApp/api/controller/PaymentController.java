package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.PaymentListResponse;
import com.upgrad.FoodOrderingApp.api.model.PaymentResponse;
import com.upgrad.FoodOrderingApp.service.businness.PaymentBusinessService;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class PaymentController {

  @Autowired
  private PaymentBusinessService paymentBusinessService;

  @RequestMapping(method = RequestMethod.GET, path = "/payment", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<PaymentListResponse> getAllAvailablePaymentMethods(@RequestHeader("authorization") final String authorization) throws AuthorizationFailedException{

    List<PaymentEntity> paymentEntities = paymentBusinessService.getAllAvailablePaymentMethods();
    List<PaymentResponse> allPaymentResponses = new ArrayList<>();

    for (PaymentEntity payment: paymentEntities) {
      PaymentResponse paymentResponse = new PaymentResponse()
              .id(UUID.fromString(payment.getUuid()))
              .paymentName(payment.getPaymentName());

      allPaymentResponses.add(paymentResponse);
    }

    PaymentListResponse paymentListResponse = new PaymentListResponse().paymentMethods(allPaymentResponses);
    return new ResponseEntity<PaymentListResponse>(paymentListResponse, HttpStatus.OK);
  }
}
