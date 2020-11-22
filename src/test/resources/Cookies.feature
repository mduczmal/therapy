Feature: Cookies

  Scenario: Cookies are displayed on the home page
    WF 2.1.8 Użytkownik widzi informację o ciasteczkach.

    Given I visit the home page
    Then info about cookies is displayed

  Scenario: Cookies are displayed on the details page
    WF 2.1.8 Użytkownik widzi informację o ciasteczkach.

    Given I see the ad details
    Then info about cookies is displayed

  Scenario: Cookies are displayed on the ad creation page
    WF 2.1.8 Użytkownik widzi informację o ciasteczkach.

    Given I visit the home page
    When I click the ad
    Then info about cookies is displayed


  Scenario: Accept cookies on the home page
    WF 2.1.9 Użytkownik może zamknąć informację o ciasteczkach.

    Given I visit the home page
    When I accept the cookies
    Then info about cookies is not displayed

  Scenario: Accept cookies on the details page
    WF 2.1.9 Użytkownik może zamknąć informację o ciasteczkach.

      Given I see the ad details
      When I accept the cookies
      Then info about cookies is not displayed

  Scenario: Accept cookies on the home page and go to the details page
    WF 2.1.9 Użytkownik może zamknąć informację o ciasteczkach.

    Given I visit the home page
    When I accept the cookies
    And I click the ad
    Then info about cookies is not displayed

  Scenario: Accept cookies on the home page and go to the details page as Therapist

    Given I am a therapist
    When I visit the home page
    When I accept the cookies
    And I click the ad
    Then info about cookies is not displayed
