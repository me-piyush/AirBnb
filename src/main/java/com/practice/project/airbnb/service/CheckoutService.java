package com.practice.project.airbnb.service;

import com.practice.project.airbnb.entity.Booking;

public interface CheckoutService {

    String getCheckoutSession(Booking booking, String successUrl , String failureUrl);

}
