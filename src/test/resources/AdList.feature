Feature: List of ads
  WF 2.1.4 Użytkownik widzi na raz wiele ogłoszeń zawierających podstawowe informacje o
  terapii.

  Scenario: Display list of ads from home page
    PU 1.7 Zobacz_listę_ogłoszeń

    Given I visit the home page
    Then the list of ads is displayed

  Scenario: Display list of ads after seeing ad details
    PU 1.7 Zobacz_listę_ogłoszeń

    Given I see the ad details
    When I click the "back" button
    Then the list of ads is displayed
