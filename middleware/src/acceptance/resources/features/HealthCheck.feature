Feature: Retrieve server status
  As a SRE I would like to retrieve my server status

  Scenario: HealthCheck
    When a consumer makes an API GET request "/health"
    Then the API returns a 200 status code
