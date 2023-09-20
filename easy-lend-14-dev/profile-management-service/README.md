# Profile Management Service

The Profile Management Service handles the creation and management of user profiles.

## Base Method

**createProfile()**:

This method is used to create a new user profile, collecting essential details and verifying the user's identity.

## Responsibilities

The Profile Management Service is responsible for the following tasks:

- Collecting additional user details such as contact information, employment status, income, and any other necessary
  information to create a comprehensive user profile.
- Verifying the user's identity through a robust document verification process to ensure the authenticity of the
  provided information.
- Securely storing the user profile information in the database.
- Providing options for users to update or modify their profile information as needed.

# How to Start the Project

To start using the Profile Management Service, follow the steps below:

1. Ensure you have RabbitMQ, MySQL, and Cloudinary set up with the required configurations in your environment.

2. Clone the Profile Management Service repository from the source code.

3. Open the `application.properties` file and update the necessary configurations for RabbitMQ, MySQL, Cloudinary, and
   JWT secret.

4. Build the project using a build tool like Maven or Gradle.

5. Run the project as a Spring Boot application.

6. The Profile Management Service will start running on the specified port (default: 8080).

# API Requests and Responses

## Fetch User Profile (GET /api/profile/get-profile)

**Request:**

```http
GET /api/profile/get-profile
Authorization: Bearer <JWT Token>
```

**Response:**

```json
{
  "success": true,
  "data": {
    "userId": "user123",
    "status": "NEW",
    "contactInformationDTO": {
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com"
    },
    "employmentStatusDTO": {
      "previouslyEmployed": true,
      "employmentSituation": "Employed",
      "income": 5000.0,
      "jobType": "Full-time"
    },
    "incomeStatusDTO": {
      "employmentStatus": "Employed",
      "monthlyPersonalIncome": "5000",
      "hasOtherSourcesOfIncome": false,
      "extraIncomeDescription": ""
    },
    "governmentIDDTO": {
      "documentType": "Passport",
      "documentNumber": "ABC123XYZ",
      "url": "https://example.com/documents/government_id"
    },
    "proofOfAddressDTO": {
      "documentUrl": "https://example.com/documents/proof_of_address"
    },
    "bankAccountDTO": {
      "bank": "Sample Bank",
      "accountNumber": "1234567890",
      "accountName": "John Doe"
    }
  }
}
```

## Update Contact Information (PUT /api/profile/contact-information)

**Request:**

```http
PUT /api/profile/contact-information
Authorization: Bearer <JWT Token>
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "+1234567890"
}
```

**Response:**

```json
{
  "success": true,
  "data": {
    "userId": "user123",
    "status": "CONTACT_UPDATED",
    "contactInformationDTO": {
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "phoneNumber": "+1234567890"
    },
    "employmentStatusDTO": null,
    "incomeStatusDTO": null,
    "governmentIDDTO": null,
    "proofOfAddressDTO": null,
    "bankAccountDTO": null
  }
}
```

## Update Employment Status (PUT /api/profile/employment-status)

**Request:**

```http
PUT /api/profile/employment-status
Authorization: Bearer <JWT Token>
Content-Type: application/json

{
  "previouslyEmployed": true,
  "employmentSituation": "Employed",
  "income": 5000.0,
  "jobType": "Full-time"
}
```

**Response:**

```json
{
  "success": true,
  "data": {
    "userId": "user123",
    "status": "EMPLOYMENT_UPDATED",
    "contactInformationDTO": null,
    "employmentStatusDTO": {
      "previouslyEmployed": true,
      "employmentSituation": "Employed",
      "income": 5000.0,
      "jobType": "Full-time"
    },
    "incomeStatusDTO": null,
    "governmentIDDTO": null,
    "proofOfAddressDTO": null,
    "bankAccountDTO": null
  }
}
```

## Update Government ID (POST /api/profile/government-id)

**Request:**

```http
POST /api/profile/government-id
Authorization: Bearer <JWT Token>
Content-Type: multipart/form-data

documentType=Passport
documentNumber=ABC123XYZ
file=@/path/to/government_id_document.pdf
```

**Response:**

```json
{
  "success": true,
  "data": {
    "userId": "user123",
    "status": "GOVERNMENT_UPDATED",
    "contactInformationDTO": null,
    "employmentStatusDTO": null,
    "incomeStatusDTO": null,
    "governmentIDDTO": {
      "documentType": "Passport",
      "documentNumber": "ABC123XYZ",
      "url": "https://example.com/documents/government_id"
    },
    "proofOfAddressDTO": null,
    "bankAccountDTO": null
  }
}
```

## Update Income Status (PUT /api/profile/income-status)

**Request:**

```http
PUT /api/profile/income-status
Authorization: Bearer <JWT Token>
Content-Type: application/json

{
  "employmentStatus": "Employed",
  "monthlyPersonalIncome": "5000",
  "hasOtherSourcesOfIncome": false,
  "extraIncomeDescription": ""
}
```

**Response:**

```json
{
  "success": true,
  "data": {
    "userId": "user123",
    "status": "INCOME_UPDATED",
    "contactInformationDTO": null,
    "employmentStatusDTO": null,
    "incomeStatusDTO": {
      "employmentStatus": "Employed",
      "monthlyPersonalIncome": "5000",
      "hasOtherSourcesOfIncome": false,
      "extraIncomeDescription": ""
    },
    "governmentIDDTO": null,
    "proofOfAddressDTO": null,
    "bankAccountDTO": null
  }
}
```

## Update Bank Account (PUT /api/profile/bank-account)

**Request:**

```http
PUT /api/profile/bank-account
Authorization: Bearer <JWT Token>
Content-Type: application/json

{
  "bank": "Sample Bank",
  "accountNumber": "1234567890",
  "accountName": "John Doe"
}
```

**Response:**

```json
{
  "success": true,
  "data": {
    "userId": "user123",
    "status": "BANK_ACCOUNT_UPDATED",
    "contactInformationDTO": null,
    "employmentStatusDTO": null,
    "incomeStatusDTO": null,
    "governmentIDDTO": null,
    "proofOfAddressDTO": null,
    "bankAccountDTO": {
      "bank": "Sample Bank",
      "accountNumber": "1234567890",
      "accountName": "John Doe"
    }
  }
}
```

## Update Proof of Address (POST /api/profile/proof-of-address)

**Request:**

```http
POST /api/profile/proof-of-address
Authorization: Bearer <JWT Token>
Content-Type: multipart/form-data

documentUrl=@/path/to/proof_of_address_document.pdf
```

**Response:**

```json
{
  "success": true,
  "data": {
    "userId": "user123",
    "status": "PROOF_OF_ADDRESS",
    "contactInformationDTO": null,
    "employmentStatusDTO": null,
    "incomeStatusDTO": null,
    "governmentIDDTO": null,
    "proofOfAddressDTO": {
      "documentUrl": "https://example.com/documents/proof_of_address"
    },
    "bankAccountDTO": null
```

}

With these API requests and responses, you can effectively manage user profiles and update their information as needed
using the Profile Management Service.

THANK YOU :)