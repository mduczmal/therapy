Feature: Ad details are displayed

  Scenario: Display ad details

    WF 2.1.5 Użytkownik może wyświetlić szczegółowe informacje o ogłoszeniu oraz
    komentarze użytkowników pod tym ogłoszeniem.
    PU 1.6 Zobacz_ogłoszenie

    Given the ad has email
    And the ad has telephone number
    And I visit the home page
    When I click the ad
    Then name and surname is displayed
    And address is displayed
    And email is displayed
    And telephone number is displayed
