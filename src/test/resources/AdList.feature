Feature: List of ads

  Scenario: Display list of ads from home page
    WF 2.1.4 Użytkownik widzi na raz wiele ogłoszeń zawierających podstawowe informacje o
    terapii.

    Given I visit the home page
    Then the list of ads is displayed

  Scenario: Display list of ads after seeing ad details
    WF 2.1.4 Użytkownik widzi na raz wiele ogłoszeń zawierających podstawowe informacje o
    terapii.

    Given I see the ad details
    When I click the "back" button
    Then the list of ads is displayed

  Scenario: Display list of ads from home page as moderator
    WF 2.1.4 Użytkownik widzi na raz wiele ogłoszeń zawierających podstawowe informacje o
    terapii.

    Given I am a moderator
    And I visit the home page
    Then the list of ads is displayed
