package com.mduczmal.therapy.unit;

import com.mduczmal.therapy.ad.Ad;
import com.mduczmal.therapy.ad.AdDetails;
import com.mduczmal.therapy.ad.comment.Comment;
import com.mduczmal.therapy.cookies.Cookies;
import com.mduczmal.therapy.therapist.Therapist;
import com.mduczmal.therapy.user.UserAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.collection.IsMapWithSize.aMapWithSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TherapyAppTests {

    @Test
    void cookiesAreNotAcceptedByDefault() {
        Cookies cookies = new Cookies();
        assertFalse(cookies.areAccepted());
    }

    @Test
    void therapistCookiesAcceptedWhenObservedCookiesAccepted() {
        Therapist therapist = new Therapist();
        Cookies cookies = new Cookies();
        cookies.attach(therapist);
        cookies.accept();
        assertTrue(therapist.getCookiesAccepted());
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
        Ad ad = new Ad();
        ad.addComment(mockedComment);

        verify(mockedComment).setAuthor("Komentarz anonimowy");
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = "userDetailsService", value = "Test1")
    void commentByAdCreatorIsMarkedAsSelfComment() {
        UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Therapist therapist = (Therapist) userAccount.getUser();
        Comment comment = new Comment();

        Optional<Ad> optAd = therapist.createAd();
        optAd.ifPresent(ad -> ad.addComment(comment, therapist));

        assertTrue(optAd.isPresent());
        assertTrue(comment.isSelfComment());
    }

    @Test
    void obligatoryFieldsNotPresentIfNameIsMissing() {
        AdDetails details = new AdDetails();
        details.setSurname("Surname");
        details.setAddress("Address");
        assertFalse(details.obligatoryFieldsPresent());
    }

    @Test
    void obligatoryFieldsNotPresentIfSurnameIsMissing() {
        AdDetails details = new AdDetails();
        details.setName("Name");
        details.setAddress("Address");
        assertFalse(details.obligatoryFieldsPresent());
    }

    @Test
    void obligatoryFieldsNotPresentIfAddressIsMissing() {
        AdDetails details = new AdDetails();
        details.setName("Name");
        details.setSurname("Surname");
        assertFalse(details.obligatoryFieldsPresent());
    }

    @Test
    void obligatoryFieldsPresentIfNameAndSurnameAndAddressArePresent() {
        AdDetails details = new AdDetails();
        details.setName("Name");
        details.setSurname("Surname");
        details.setAddress("Address");
        assertTrue(details.obligatoryFieldsPresent());
    }

    @Test
    void polishNameIsSet() {
        AdDetails details = new AdDetails();
        details.setName("PolishNameĄĘŃÓŻŹŁ");
        String name = details.getName();
        assertEquals("PolishNameĄĘŃÓŻŹŁ", name);
    }

    @Test
    void polishSurnameIsSet() {
        AdDetails details = new AdDetails();
        details.setSurname("PolishSurnameąęńóżźł");
        String surname = details.getSurname();
        assertEquals("PolishSurnameąęńóżźł", surname);
    }

    @Test
    void polishAddressIsSet() {
        AdDetails details = new AdDetails();
        details.setAddress("Polish Address ąęńóżźłść");
        String address = details.getAddress();
        assertEquals("Polish Address ąęńóżźłść", address);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\n"})
    void blankNameIsNotSet(String input) {
        AdDetails details = new AdDetails();
        details.setName(input);
        String name = details.getName();
        assertNull(name);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\n"})
    void blankSurnameIsNotSet(String input) {
        AdDetails details = new AdDetails();
        details.setSurname(input);
        String surname = details.getSurname();
        assertNull(surname);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\n"})
    void blankAddressIsNotSet(String input) {
        AdDetails details = new AdDetails();
        details.setAddress(input);
        String address = details.getAddress();
        assertNull(address);
    }

    @Test
    void validEmailIsSet() {
        AdDetails details = new AdDetails();
        details.setEmail("email@example.com");
        String email = details.getEmail();
        assertEquals("email@example.com", email);
    }

    @ParameterizedTest
    @ValueSource(strings = {"emailWithoutAt", "double@@email",
    "emailToLongXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX@example.com"})
    void emailIsSanitized(String input) {
        AdDetails details = new AdDetails();
        details.setEmail(input);
        String email = details.getEmail();
        assertNull(email);
    }

    @Test
    void telephoneNumberWithCharsIsNotSet() {
        AdDetails details = new AdDetails();
        details.setTelephoneNumber("TEL123456");
        String telephoneNumber = details.getTelephoneNumber();
        assertNull(telephoneNumber);
    }


    @Test
    void therapistCannotCreateTwoAds() {
        Therapist therapist = new Therapist();
        therapist.createAd();
        Optional<Ad> secondAd = therapist.createAd();
        assertTrue(secondAd.isEmpty());
    }

    @Test
    void singlePriceIsSet() {
        AdDetails details = new AdDetails();
        details.setPrice("service", 100);
        int price = details.getPrice("service");
        assertEquals(100,  price);
    }

    @Test
    void zeroPriceIsSet() {
        AdDetails details = new AdDetails();
        details.setPrice("service", 0);
        int price = details.getPrice("service");
        assertEquals(0,  price);
    }

    @Test
    void pricingIsSetIfPriceIsSet() {
        AdDetails details = new AdDetails();
        int price = 100;

        details.setPrice("service", price);
        Map<String, Integer> pricing = details.getPricing();

        assertThat(pricing, aMapWithSize(1));
        assertThat(pricing, hasEntry("service", price));
    }

    @Test
    void priceNotSetIfNegative() {
        AdDetails details = new AdDetails();
        details.setPrice("service", -135);
        Integer price = details.getPrice("service");
        assertNull(price);
    }

    @Test
    void priceIsDeleted() {
        AdDetails details = new AdDetails();

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
        AdDetails details = new AdDetails();
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
        Ad ad = new Ad();

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
