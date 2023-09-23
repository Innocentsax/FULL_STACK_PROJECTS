package com.example.food.pojos;

import jdk.jfr.Name;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationDetails {
    //@Name("Number Of Registered Customer")
    private Integer numberOfRegisteredCustomer;
    //@Name("Number Of Available Product")
    private Integer numberOfAvailableProduct;
    //@Name("Number Of Orders")
    private Integer numberOfOrders;
}
