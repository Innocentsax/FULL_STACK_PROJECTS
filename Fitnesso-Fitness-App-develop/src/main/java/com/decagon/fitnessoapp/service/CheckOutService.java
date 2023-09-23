package com.decagon.fitnessoapp.service;

import com.decagon.fitnessoapp.dto.CheckOutRequest;
import com.decagon.fitnessoapp.dto.CheckOutResponse;
import com.decagon.fitnessoapp.model.product.CheckOut;

public interface CheckOutService {
    CheckOutResponse save(CheckOutRequest checkOutRequest);

    CheckOut findByReferenceNumber(String referenceNumber);

    CheckOut updateCheckOut(CheckOut checkOut);
}
