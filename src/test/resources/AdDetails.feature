Feature: Ad details are displayed

  Scenario: Displaying ad details

    WF 2.1.5 Użytkownik może wyświetlić szczegółowe informacje o ogłoszeniu oraz
    komentarze użytkowników pod tym ogłoszeniem.
    PU 1.6 Zobacz_ogłoszenie

    Given I visit the home page
    When I click the ad
    Then Ad details are displayed