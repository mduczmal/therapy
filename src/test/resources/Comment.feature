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
    PU 1.4 Dodaj_komentarz

    Given I see the ad details
    When I click the add comment button
    And I enter the comment content
    Then The comment is displayed under the ad

  Scenario: Add anonymous comment
    WF 2.1.6 Użytkownik dodaje komentarze pod istniejącymi ogłoszeniami.
    PU 1.4 Dodaj_komentarz

    Given I see the ad details
    When I click the add comment button
    And I enter the comment content
    But I do not enter the nickname
    Then The comment is displayed under the ad
    And The comment author is "Komentarz anonimowy"

  Scenario: Delete comment
    WF 2.1.7 Moderator usuwa wybrane ogłoszenia i komentarze.
    PU 1.5 Usuń_komentarz

    Given I am a moderator
    And I see a comment
    When I click the delete comment button
    Then The comment is removed
