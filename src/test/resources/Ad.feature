Feature: Ad lifecycle

  Scenario: Ad is created
    WF 2.1.1 Terapeuta tworzy ogłoszenie.
    PU 1.1 Dodaj_ogłoszenie

    Given I am a therapist
    And I visit the home page
    When I click the "create ad" button
    And I enter the name
    And I enter the surname
    And I enter the address
    And I click the "save ad" button
    Then I am redirected to home page
    And my ad is displayed

  Scenario: Ad is edited
    WF 2.1.2 Terapeuta może zmienić treść ogłoszenia.
    PU 1.2 Edytuj_ogłoszenie

    Given I am a therapist
    And I have an ad
    And I visit the home page
    When I click the "edit ad" button
    And I change my address
    And I click the "update ad" button
    Then I am redirected to home page
    And the ad is displayed
    And new address is displayed

  Scenario: Ad is removed by therapist
    WF 2.1.3 Terapeuta może usunąć ogłoszenie, a serwis wyświetla informację, że ta akcja
    skutkuje również usunięciem wszystkich zamieszczonych pod ogłoszeniem
    komentarzy.
    PU 1.3 Usuń_ogłoszenie

    Given I am a therapist
    And I have an ad
    And I visit the home page
    When I click the "delete ad" button
    Then the ad is not displayed

  Scenario: Ad is removed by moderator
    WF 2.1.7 Moderator usuwa wybrane ogłoszenia i komentarze.
    PU 1.3 Usuń_ogłoszenie

    Given I am a moderator
    And I visit the home page
    When I click the "delete ad" button
    Then the ad is not displayed

