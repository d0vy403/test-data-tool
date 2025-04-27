# Test Data Tool API

RESTful API for managing test data, including user data, account data, and user login methods.

## Installation

Use Maven to build the project.

The application will be accessible at `http://localhost:8080`.

## API Endpoints

### User Data

- **POST** `/api/user-data`: Create user.
    - **Request**: `CreateUserDataRequest`
    - **Response**: `GetUserDataResponse`

- **GET** `/api/user-data`: Get users (optional filtering).
    - **Parameters**: `username`, `environment`, `channel`
    - **Response**: `GetUserDataResponse`

- **GET** `/api/user-data/{id}`: Get user by ID.
    - **Response**: `GetUserDataResponse`

- **PATCH** `/api/user-data/{id}`: Update user.
    - **Request**: `UpdateUserDataRequest`
    - **Response**: `GetUserDataResponse`

- **DELETE** `/api/user-data/{id}`: Delete user.
    - **Response**: `204 No Content`

### User Login Methods

- **POST** `/api/user-login-method`: Create user login method.
    - **Request**: `CreateUserLoginMethodRequest`
    - **Response**: `GetUserLoginMethodResponse`

- **GET** `/api/user-login-method`: Get user login methods (optional filtering).
    - **Parameters**: `method`, `userId`
    - **Response**: `GetUserLoginMethodResponse`

- **GET** `/api/user-login-method/{id}`: Get user login method by ID.
    - **Response**: `GetUserLoginMethodResponse`

- **DELETE** `/api/user-login-method/{id}`: Delete user login method.
    - **Response**: `204 No Content`

### Account Data

- **POST** `/api/account-data`: Create account data.
    - **Request**: `CreateAccountDataRequest`
    - **Response**: `GetAccountDataResponse`

- **GET** `/api/account-data`: Get account data (optional filtering).
    - **Parameters**: `currency`, `type`, `userId`
    - **Response**: `GetAccountDataResponse`

- **GET** `/api/account-data/{id}`: Get account data by ID.
    - **Response**: `GetAccountDataResponse`

- **PATCH** `/api/account-data/{id}`: Update account data.
  - **Request**: `UpdateAccountDataRequest`
  - **Response**: `GetAccountDataResponse`

- **DELETE** `/api/account-data/{id}`: Delete account data.
    - **Response**: `204 No Content`

## Swagger Documentation

API documentation is available at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).
