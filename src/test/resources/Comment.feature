Feature: Comments

  Scenario: Display comments
    WF 2.1.5 Użytkownik może wyświetlić szczegółowe informacje o ogłoszeniu oraz
    komentarze użytkowników pod tym ogłoszeniem.

    Given the ad has comments
    And I visit the home page
    When  I click the ad
    Then  I see the comments

  Scenario: Add comment
    WF 2.1.6 Użytkownik dodaje komentarze pod istniejącymi ogłoszeniami.

    Given I see the ad details
    When I click the "add comment" button
    And I enter the comment content
    And I enter a nickname "TestNickname"
    And I click the "save comment" button
    Then the comment is displayed
    And the comment author is "TestNickname"

  Scenario: Add anonymous comment
    WF 2.1.6 Użytkownik dodaje komentarze pod istniejącymi ogłoszeniami.

    Given I see the ad details
    When I click the "add comment" button
    And I enter the comment content
    But I leave the nickname field blank
    And I click the "save comment" button
    Then the comment is displayed
    And the comment author is "Komentarz anonimowy"

  Scenario: Add self comment
  WF 2.1.6 Użytkownik dodaje komentarze pod istniejącymi ogłoszeniami.

    Given I am a therapist
    And I have an ad
    And I see the ad details
    When I click the "add comment" button
    And I enter the comment content
    And I enter a nickname "TestSelfAuthor"
    And I click the "save comment" button
    Then the comment is displayed

  Scenario: Delete comment
    WF 2.1.7 Moderator usuwa wybrane ogłoszenia i komentarze.

    Given I am a moderator
    And I see a comment
    When I click the "delete comment" button
    Then The comment is not displayed
