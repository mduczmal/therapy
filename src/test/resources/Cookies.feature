Feature: Cookies

  Scenario: Cookies are displayed on the home page
    WF 2.1.8 Użytkownik widzi informację o ciasteczkach.

    Given I visit the home page
    Then info about cookies is displayed

  Scenario: Accept cookies
    WF 2.1.9 Użytkownik może zamknąć informację o ciasteczkach.
    PU 1.8 Zaakceptuj_ciasteczka

    Given I visit the home page
    When I accept the cookies
    Then info about cookies is not displayed