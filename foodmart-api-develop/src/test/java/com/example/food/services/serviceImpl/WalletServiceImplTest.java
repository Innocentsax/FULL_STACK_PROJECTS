package com.example.food.services.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.food.pojos.WalletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class WalletServiceImplTest {
    @Autowired
    private WalletServiceImpl walletServiceImpl;

    /**
     * Method under test: {@link WalletServiceImpl#getWalletBalance()}
     */
    @Test
    void testGetWalletBalance() {
        // Arrange and Act
        WalletResponse actualWalletBalance = walletServiceImpl.getWalletBalance();

        // Assert
        assertEquals(-1, actualWalletBalance.getCode());
        assertNull(actualWalletBalance.getWalletBalance());
        assertNull(actualWalletBalance.getUserName());
        assertEquals("An error occurred. Error message : ${errorMessage}", actualWalletBalance.getDescription());
    }
}

