Feature: List of ads
  WF 2.1.4 Użytkownik widzi na raz wiele ogłoszeń zawierających podstawowe informacje o
  terapii.

  Scenario: Displaying list of ads from home page
    PU 1.7 Zobacz_listę_ogłoszeń

    Given I visit the home page
    Then The list of ads is displayed

  Scenario: Displaying list of ads after seeing ad details
    PU 1.7 Zobacz_listę_ogłoszeń

    Given I see the ad details
    When I press the back button
    Then The list of ads is displayed
