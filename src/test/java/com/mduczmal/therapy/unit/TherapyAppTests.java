package com.mduczmal.therapy.unit;

import com.mduczmal.therapy.ad.Ad;
import com.mduczmal.therapy.ad.AdFactory;
import com.mduczmal.therapy.ad.TherapyAdDetails;
import com.mduczmal.therapy.ad.TherapyAdFactory;
import com.mduczmal.therapy.ad.comment.Comment;
import com.mduczmal.therapy.cookies.Cookies;
import com.mduczmal.therapy.user.UserAccount;
import com.mduczmal.therapy.user.therapist.Therapist;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;

import java.time.LocalDateTime;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.collection.IsMapWithSize.aMapWithSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TherapyAppTests {

    @Autowired
    AdFactory adFactory;

    @Test
    void cookiesAreNotAcceptedByDefault() {
        Cookies cookies = new Cookies();
        assertFalse(cookies.areAccepted());
    }

    @Test
    void commentContentWitPolishCharsIsSet() {
        Comment comment = new Comment();
        comment.setContent("Polish chars: ąęńóżźł");
        assertEquals("Polish chars: ąęńóżźł", comment.getContent());
    }

    @Test
    void commentWithNoAuthorIsAnonymous() {
        Comment mockedComment = mock(Comment.class);
        AdFactory adFactory = new TherapyAdFactory();
        Ad ad = adFactory.createAd(mock(Therapist.class));
        ad.addComment(mockedComment);

        verify(mockedComment).setAuthor("Komentarz anonimowy");
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = "userDetailsService", value = "Test1")
    void commentByAdCreatorIsMarkedAsSelfComment() {
        UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Therapist therapist = (Therapist) userAccount.getUser();
        Comment comment = new Comment();

        AdFactory adFactory = new TherapyAdFactory();
        Ad ad = adFactory.createAd(therapist);
        ad.addComment(comment, therapist);

        assertTrue(comment.isSelfComment());
    }

    @Test
    void obligatoryFieldsNotPresentIfNameIsMissing() {
        TherapyAdDetails details = new TherapyAdDetails();
        details.setSurname("Surname");
        details.setAddress("Address");
        assertFalse(details.obligatoryFieldsPresent());
    }

    @Test
    void obligatoryFieldsNotPresentIfSurnameIsMissing() {
        TherapyAdDetails details = new TherapyAdDetails();
        details.setName("Name");
        details.setAddress("Address");
        assertFalse(details.obligatoryFieldsPresent());
    }

    @Test
    void obligatoryFieldsNotPresentIfAddressIsMissing() {
        TherapyAdDetails details = new TherapyAdDetails();
        details.setName("Name");
        details.setSurname("Surname");
        assertFalse(details.obligatoryFieldsPresent());
    }

    @Test
    void obligatoryFieldsPresentIfNameAndSurnameAndAddressArePresent() {
        TherapyAdDetails details = new TherapyAdDetails();
        details.setName("Name");
        details.setSurname("Surname");
        details.setAddress("Address");
        assertTrue(details.obligatoryFieldsPresent());
    }

    @Test
    void polishNameIsSet() {
        TherapyAdDetails details = new TherapyAdDetails();
        details.setName("PolishNameĄĘŃÓŻŹŁ");
        String name = details.getName();
        assertEquals("PolishNameĄĘŃÓŻŹŁ", name);
    }

    @Test
    void polishSurnameIsSet() {
        TherapyAdDetails details = new TherapyAdDetails();
        details.setSurname("PolishSurnameąęńóżźł");
        String surname = details.getSurname();
        assertEquals("PolishSurnameąęńóżźł", surname);
    }

    @Test
    void polishAddressIsSet() {
        TherapyAdDetails details = new TherapyAdDetails();
        details.setAddress("Polish Address ąęńóżźłść");
        String address = details.getAddress();
        assertEquals("Polish Address ąęńóżźłść", address);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\n"})
    void blankNameIsNotSet(String input) {
        TherapyAdDetails details = new TherapyAdDetails();
        details.setName(input);
        String name = details.getName();
        assertNull(name);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\n"})
    void blankSurnameIsNotSet(String input) {
        TherapyAdDetails details = new TherapyAdDetails();
        details.setSurname(input);
        String surname = details.getSurname();
        assertNull(surname);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\n"})
    void blankAddressIsNotSet(String input) {
        TherapyAdDetails details = new TherapyAdDetails();
        details.setAddress(input);
        String address = details.getAddress();
        assertNull(address);
    }

    @Test
    void validEmailIsSet() {
        TherapyAdDetails details = new TherapyAdDetails();
        details.setEmail("email@example.com");
        String email = details.getEmail();
        assertEquals("email@example.com", email);
    }

    @ParameterizedTest
    @ValueSource(strings = {"emailWithoutAt", "double@@email",
    "emailToLongXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX@example.com"})
    void emailIsSanitized(String input) {
        TherapyAdDetails details = new TherapyAdDetails();
        details.setEmail(input);
        String email = details.getEmail();
        assertNull(email);
    }

    @Test
    void telephoneNumberWithCharsIsNotSet() {
        TherapyAdDetails details = new TherapyAdDetails();
        details.setTelephoneNumber("TEL123456");
        String telephoneNumber = details.getTelephoneNumber();
        assertNull(telephoneNumber);
    }


    @Test
    void therapistCannotCreateTwoAds() {
        AdFactory adFactory = new TherapyAdFactory();
        Therapist therapist = new Therapist();
        adFactory.createAd(therapist);
        assertThrows(IllegalStateException.class, () -> adFactory.createAd(therapist));
    }

    @Test
    void singlePriceIsSet() {
        TherapyAdDetails details = new TherapyAdDetails();
        details.setPrice("service", 100);
        int price = details.getPrice("service");
        assertEquals(100,  price);
    }

    @Test
    void zeroPriceIsSet() {
        TherapyAdDetails details = new TherapyAdDetails();
        details.setPrice("service", 0);
        int price = details.getPrice("service");
        assertEquals(0,  price);
    }

    @Test
    void pricingIsSetIfPriceIsSet() {
        TherapyAdDetails details = new TherapyAdDetails();
        int price = 100;

        details.setPrice("service", price);
        Map<String, Integer> pricing = details.getPricing();

        assertThat(pricing, aMapWithSize(1));
        assertThat(pricing, hasEntry("service", price));
    }

    @Test
    void priceNotSetIfNegative() {
        TherapyAdDetails details = new TherapyAdDetails();
        details.setPrice("service", -135);
        Integer price = details.getPrice("service");
        assertNull(price);
    }

    @Test
    void priceIsDeleted() {
        TherapyAdDetails details = new TherapyAdDetails();

        details.setPrice("first_service", 30);
        details.setPrice("second_service", Integer.MAX_VALUE);
        details.deletePrice("first_service");
        Integer firstPrice = details.getPrice("first_service");
        Integer secondPrice = details.getPrice("second_service");

        assertNull(firstPrice);
        assertEquals(Integer.MAX_VALUE, secondPrice);
    }

    @Test
    void deletedServiceIsNotPresentInPricing() {
        TherapyAdDetails details = new TherapyAdDetails();
        int price = 100;

        details.setPrice("service", price);
        details.deletePrice("service");
        Map<String, Integer> pricing = details.getPricing();

        assertThat(pricing, aMapWithSize(0));
    }

    @Test
    void newAdHasPresentCreationDate() {
        LocalDateTime beforeCreation = LocalDateTime.now();
        LocalDateTime afterCreation = beforeCreation.plusSeconds(30);
        AdFactory adFactory = new TherapyAdFactory();
        Ad ad = adFactory.createAd(mock(Therapist.class));

        assertTrue(ad.getDateCreated().isAfter(beforeCreation));
        assertTrue(ad.getDateCreated().isBefore(afterCreation));
    }

    @Test
    void newCommentHasPresentCreationDate() {
        LocalDateTime beforeCreation = LocalDateTime.now();
        LocalDateTime afterCreation = beforeCreation.plusSeconds(30);
        Comment comment = new Comment();

        assertTrue(comment.getDateCreated().isAfter(beforeCreation));
        assertTrue(comment.getDateCreated().isBefore(afterCreation));
    }
}
