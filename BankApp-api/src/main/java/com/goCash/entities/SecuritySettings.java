package com.goCash.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "security_settings")
public class SecuritySettings extends BaseEntity{


    @Column(name = "device_authorization_enabled", nullable = false)
    private boolean deviceAuthorizationEnabled;

    @Column(name = "pin_enabled", nullable = false)
    private boolean PINEnabled;

    @Column(name = "biometric_auth_enabled", nullable = false)
    private boolean biometricAuthEnabled;

    @Column(name = "transaction_limits_enabled", nullable = false)
    private boolean transactionLimitsEnabled;

    @Column(name = "notification_enabled", nullable = false)
    private boolean notificationEnabled;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    // Constructors, getters, and setters
}
