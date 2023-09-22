package com.decagon.fintechpaymentapisqd11a.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {Util.class})
@ExtendWith(SpringExtension.class)
class UtilTest {
    @Autowired
    private Util util;

    /**
     * Method under test: {@link Util#validatePassword(String, String)}
     */
    @Test
    void testValidatePassword() {
        assertTrue(util.validatePassword("iloveyou", "iloveyou"));
        assertFalse(util.validatePassword("Password", "iloveyou"));
    }
}

