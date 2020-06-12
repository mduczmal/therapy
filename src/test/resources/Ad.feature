Feature: Ad lifecycle

  Scenario: Ad is created
    WF 2.1.1 Terapeuta tworzy ogłoszenie.

    Given I am a therapist
    And I visit the home page
    When I click the "create ad" button
    And I enter the name
    And I enter the surname
    And I enter the address
    And I click the "save ad" button
    Then I am redirected to home page
    And my new ad is displayed

  Scenario: Ad is created after login
  WF 2.1.1 Terapeuta tworzy ogłoszenie.

    Given I visit the home page
    When I click the "create ad" button
    And I log in as therapist
    And I enter the name
    And I enter the surname
    And I enter the address
    And I click the "save ad" button
    Then I am redirected to home page
    And my new ad is displayed

  Scenario: Ad is not created when required fields are not present
    WF 2.1.1 Terapeuta tworzy ogłoszenie.

    Given I am a therapist
    And I visit the home page
    When I click the "create ad" button
    And I enter the name
    And I enter the address
    And I click the "save ad" button
    And I visit the home page
    Then my new ad is not displayed

  Scenario: Ad name is edited
    WF 2.1.2 Terapeuta może zmienić treść ogłoszenia.

    Given I am a therapist
    And I have an ad
    And I visit the home page
    And I change my name to "NewName"
    And I click the "update ad" button
    Then I am redirected to home page
    And my ad is displayed
    And name "NewName" is displayed

  Scenario: Ad surname is edited
  WF 2.1.2 Terapeuta może zmienić treść ogłoszenia.

    Given I am a therapist
    And I have an ad
    And I visit the home page
    And I change my surname to "NewSurname"
    And I click the "update ad" button
    Then I am redirected to home page
    And my ad is displayed
    And surname "NewSurname" is displayed

  Scenario: Ad address is edited
  WF 2.1.2 Terapeuta może zmienić treść ogłoszenia.

    Given I am a therapist
    And I have an ad
    And I visit the home page
    And I change my address to "NewAddress"
    And I click the "update ad" button
    Then I am redirected to home page
    And my ad is displayed
    And address "NewAddress" is displayed

  Scenario: Ad is removed by therapist from homepage
    WF 2.1.3 Terapeuta może usunąć ogłoszenie, a serwis wyświetla informację, że ta akcja
    skutkuje również usunięciem wszystkich zamieszczonych pod ogłoszeniem
    komentarzy.

    Given I am a therapist
    And I have an ad
    And I visit the home page
    When I click the "delete ad" button
    Then the ad is not displayed

  Scenario: Ad is removed by therapist from details
    WF 2.1.3 Terapeuta może usunąć ogłoszenie, a serwis wyświetla informację, że ta akcja
    skutkuje również usunięciem wszystkich zamieszczonych pod ogłoszeniem
    komentarzy.

    Given I am a therapist
    And I have an ad
    And I see the ad details
    When I click the "delete ad" button
    Then the ad is not displayed

  Scenario: Ad is removed by moderator from homepage
    WF 2.1.7 Moderator usuwa wybrane ogłoszenia i komentarze.

    Given I am a moderator
    And I visit the home page
    When I click the "delete ad" button
    Then the ad is not displayed

  Scenario: Ad is removed by moderator from details
  WF 2.1.7 Moderator usuwa wybrane ogłoszenia i komentarze.

    Given I am a moderator
    And I see the ad details
    When I click the "delete ad" button
    Then I am redirected to home page
    And the ad is not displayed

