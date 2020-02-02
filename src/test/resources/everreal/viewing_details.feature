Feature: Schedule an appointment to visit a apartment
  Background:
    Given the user is logged in to website

  @smoke
  Scenario: Viewing details page when time slot or an appointment is not selected
    Given user is on viewing page
    When user select listing on viewing page
    Then viewing details page should be shown with no appointment selected

  @smoke
  Scenario: User able to select or change appointment
    Given user is on viewing details page
    When user select an appointment
    And click on save button
    Then confirmation messagebox shown
    And selected appointment is shown under selected appointment section

  @smoke
  Scenario: User able to delete an appointment
    Given user is on viewing details page
    When user delete an appointment
    Then there will be no appointment shown under selected appointment section

  @smoke
  Scenario: Send message when user can not make it during shown times slots
    Given user is on viewing details page
    When user click on can not make it during those times link
    Then message input box shown to user
    And user able to send message

  @smoke
  Scenario: Page locale change as per language selection
    Given user is on viewing details page
    When user change language from English to Deutsch
    Then page locale should be change to Deutsch
    And change local back to English

  Scenario: Page should be shown properly on mobile or tablet screen
  Scenario: Page should be shown properly on different browsers for cross browser compatibility test
  Scenario: Google analytics should be captured for actions performed on page
  Scenario: Assert api response should be 200 ok
   # apis called on this page (e.g. booking, viewings, expose etc.)
  Scenario: User able to navigate to other pages using menu and links available on page
