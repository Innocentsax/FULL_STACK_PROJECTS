package com.goCash.utils.email;

import org.springframework.stereotype.Component;

@Component
public class Template {

    public static String accountVerification(String name, String OTP) {
        String appName = "GoCash";
        return "Hello " + name + ",<br><br>\n" +
                "Welcome to <h1 style=\"font-size: 28px; color: #800000; margin: 0;\">" + appName + "</h1>.<br>\n" +
                "Use this OTP to verify your account:<br><br>\n" +
                "<strong style=\"font-size: 24px; color: #800000;\">" + OTP + "</strong><br><br>\n" +
                "OTP will expire in 10 minutes.<br>\n" +
                "Cheers.";
    }

    public static String passwordResetEmail(String name, String token, String link) {
        return "<div style=\"font-family: Helvetica, Arial, sans-serif; font-size: 16px; margin: 0; color: #0b0c0c;\">\n" +
                "<p>Hello " + name + ",</p>\n" +
                "<p>You have requested to reset your GoCash account password. Please click on the link below to reset your password:</p>\n" +
                "<p style=\"padding: 10px; background-color: #f5f5f5; border: 1px solid #ddd; border-radius: 5px; word-wrap: break-word;\">\n" +
                "<a href=\"" + Template.passwordResetLink(token, link) + "\">" + Template.passwordResetLink(token, link) + "</a>\n" +
                "</p>\n" +
                "<p>If you did not request a password reset, you can ignore this email.</p>\n" +
                "<p>Thank you,</p>\n" +
                "<p>The GoCash Team</p>\n" +
                "</div>";
    }

    public static String welcomeEmail(String name, String accountNumber) {
        return "<div style=\"font-family: Helvetica, Arial, sans-serif; font-size: 16px; margin: 0; color: #0b0c0c;\">\n" +
                "<p>Hello " + name + ",</p>\n" +
                "<p>Welcome to GoCash!</p>\n" +
                "<p>We are excited to have you as part of our community. Your GoCash account has been successfully created, and your account details are provided below:</p>\n" +
                "<table style=\"border-collapse: collapse; width: 100%; margin-bottom: 20px;\">\n" +
                "    <tr>\n" +
                "        <td style=\"padding: 10px; border: 1px solid #ddd;\">Account Number:</td>\n" +
                "        <td style=\"padding: 10px; border: 1px solid #ddd;\">" + accountNumber + "</td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "<p>Please keep your account details safe and secure. If you have any questions or need assistance, feel free to reach out to our support team.</p>\n" +
                "<p>Thank you for choosing GoCash. We look forward to serving you!</p>\n" +
                "<p>Best regards,</p>\n" +
                "<p>The GoCash Team</p>\n" +
                "</div>";
    }

    public static String accountCredited(String name, String amountCredited) {
        return "<div style=\"font-family: Helvetica, Arial, sans-serif; font-size: 16px; margin: 0; color: #0b0c0c;\">\n" +
                "<p>Hello " + name + ",</p>\n" +
                "<p>Your GoCash account has been credited with $" + amountCredited + ".</p>\n" +
                "<p>Thank you for using GoCash. If you have any questions or need further assistance, feel free to reach out to our support team.</p>\n" +
                "<p>Best regards,</p>\n" +
                "<p>The GoCash Team</p>\n" +
                "</div>";
    }

    public static String accountBalance(String name, String accountNumber, double accountBalance) {
        return "<div style=\"font-family: Helvetica, Arial, sans-serif; font-size: 16px; margin: 0; color: #0b0c0c;\">\n" +
                "<p>Hello " + name + ",</p>\n" +
                "<p>Your GoCash account balance is as follows:</p>\n" +
                "<table style=\"border-collapse: collapse; width: 100%; margin-bottom: 20px;\">\n" +
                "    <tr>\n" +
                "        <td style=\"padding: 10px; border: 1px solid #ddd;\">Account Number:</td>\n" +
                "        <td style=\"padding: 10px; border: 1px solid #ddd;\">" + accountNumber + "</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td style=\"padding: 10px; border: 1px solid #ddd;\">Account Balance:</td>\n" +
                "        <td style=\"padding: 10px; border: 1px solid #ddd;\">" + accountBalance + " USD</td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "<p>Please keep your account details safe and secure. If you have any questions or need assistance, feel free to reach out to our support team.</p>\n" +
                "<p>Thank you for choosing GoCash. We look forward to serving you!</p>\n" +
                "<p>Best regards,</p>\n" +
                "<p>The GoCash Team</p>\n" +
                "</div>";
    }

    public String accountDebited(String recipientName, String amountDebited) {
        return "<div style=\"font-family: Helvetica, Arial, sans-serif; font-size: 16px; margin: 0; color: #0b0c0c;\">\n" +
                "<p>Hello " + recipientName + ",</p>\n" +
                "<p>We want to inform you that $" + amountDebited + " has been debited from your GoCash account.</p>\n" +
                "<p>If you have any concerns or questions about this transaction, please don't hesitate to contact our support team.</p>\n" +
                "<p>Best regards,</p>\n" +
                "<p>The GoCash Team</p>\n" +
                "</div>";
    }

    public static String transactionConfirmationEmailWithOTP(String recipientName, String transactionAmount, String transactionType, String otp) {
        return "<div style=\"font-family: Helvetica, Arial, sans-serif; font-size: 16px; margin: 0; color: #0b0c0c;\">\n" +
                "  <p>Hello " + recipientName + ",</p>\n" +
                "  <p>We are pleased to confirm the following transaction:</p>\n" +
                "  <ul>\n" +
                "    <li>Transaction Type: " + transactionType + "</li>\n" +
                "    <li>Transaction Amount: $" + transactionAmount + "</li>\n" +
                "  </ul>\n" +
                "  <p>To complete this transaction, please use the One-Time Password (OTP) provided below:</p>\n" +
                "  <p style=\"font-size: 24px; font-weight: bold;\">" + otp + "</p>\n" +
                "  <p>This OTP will expire in 10 minutes. Please do not share it with anyone for security reasons.</p>\n" +
                "  <p>If you did not initiate this transaction or need assistance, please contact our support team immediately.</p>\n" +
                "  <p>Thank you for using GoCash!</p>\n" +
                "  <p>Best regards,</p>\n" +
                "  <p>The GoCash Team</p>\n" +
                "</div>";
    }

    public static String passwordResetLink(String token, String link) {
        return   link + token;
    }
}

