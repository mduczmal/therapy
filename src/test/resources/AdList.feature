#PU 1.7 Zobacz_listę_ogłoszeń
Feature: List of ads
  Scenario: Displaying list of ads from home page
    Given I visit the home page
    Then The list of ads is displayed

  Scenario: Displaying list of ads after seeing ad details
    Given I see the ad details
    When I press the back button
    Then The list of ads is displayed
