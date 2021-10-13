Feature: Status endpoint
  As a SRE I would like to test the status endpoint in not stored case

  Background:
     Given A subscription that is not stored in our system

  Scenario:
    When I check the detail from any email
    Then the API returns a 405 status code

  Scenario:
    When I check the detail from any email and any newsletter_id
    Then the API returns a 405 status code

  Scenario:
    When I try to cancel the subscription from any email and any newsletter_id
    Then the API returns a 405 status code
