package com.mduczmal.therapy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TherapyAppTests {

    @Test
    void cookiesAreNotAcceptedByDefault() {
        Cookies cookies = new Cookies();
        assertFalse(cookies.areAccepted());
    }

    @Test
    void cookiesAreAcceptedAfterAccept() {
        Cookies cookies = new Cookies();
        cookies.accept();
        assertTrue(cookies.areAccepted());
    }

}
