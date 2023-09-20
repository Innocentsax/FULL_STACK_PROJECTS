The Investment Preference API allows users to manage their investment preferences. It enables users to create and view their investment preferences based on various criter

API Endpoints
Create Investment Preference
Endpoint: POST /api/v1/create
Description: This endpoint allows users to create their investment preference. Users can specify the loan amount, interest rate, risk tolerance, and duration in days for their investment.

{
"loanAmount": 10000.00,
"interestRate": 0.05,
"riskTolerance": 3,
"durationInDays": 30
}
Additional Notes
* The API endpoints require the user to be authenticated. The JWT token with the user's details is passed in the Authorization header as a Bearer token.
* The API enforces validation checks for the loan amount to ensure it is greater than zero.
* The API checks the user's role to ensure that borrowers are not allowed to create investments.
* The API prevents duplicate investment preferences for the same user.