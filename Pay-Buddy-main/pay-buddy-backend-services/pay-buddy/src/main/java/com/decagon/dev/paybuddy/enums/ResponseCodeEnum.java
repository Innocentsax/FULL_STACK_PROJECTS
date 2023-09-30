package com.decagon.dev.paybuddy.enums;

import lombok.Getter;

/**
 *
 * @author Odira Eze
 */
@Getter
public enum ResponseCodeEnum {

    SUCCESS(0, "Success"),
    ERROR(-1, "An error occurred. Error message : ${errorMessage}"),
    PRODUCT_NOT_FOUND(-2, "No products found"),
    ERROR_EMAIL_INVALID(-3, "Invalid email address."),
    ERROR_PASSWORD_MISMATCH(-4,"Password does not match"),
    ERROR_DUPLICATE_USER(-5,"User already exist."),
    ERROR_RESETTING_PASSWORD(-6, "Password reset unsuccessful. Contact the Admin."),
    UNAUTHORISED_ACCESS(-7,"You are not authorised to perform this operation"),
    USER_NOT_FOUND(-8, "Email does not exist"),
    ORDERS_NOT_FOUND(-9, "No Order found"),

    ERROR_UPDATING_ORDER_STATUS(-10, "Could not update order status"),

//    you can add your custom error codes as shown below just ensure that error codes have a minus sign
//    ERROR_SETTING_THRESHOLD(-2, "An error occurred"),
    ;

    ResponseCodeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    private final int code;
    private final String description;

}
