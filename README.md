# Dandan Keycloak Service

## 📌 Overview

`dandan-keycloak` is a Spring Boot backend service responsible for **user provisioning and management in Keycloak** for the Dandan platform.

It supports onboarding of:

* 🧑 Customers
* 🏪 Merchants
* 👨‍💼 Staff

The service integrates with Keycloak Admin APIs to create users, assign roles, and manage tenant-based group structures.

---

## 🏗️ Tech Stack

* Java 17
* Spring Boot 4
* Keycloak Admin Client (v26.x)
* Maven

---

## 📂 Project Structure

```
src/main/java/com/dandan/kc/dandan
│
├── config/                 # Keycloak configuration
├── controller/             # REST APIs
│   ├── CustomerController
│   ├── MerchantController
│   └── StaffController
│
├── dto/                    # Request payloads
│   ├── CustomerOnboardRequest
│   ├── MerchantOnboardRequest
│   └── StaffOnboardRequest
│
├── service/                # Business logic
│   └── KeycloakProvisioningService
│
└── DandanKeycloakApplication.java
```

---

## ⚙️ Configuration

Update the `application.yaml`:

```yaml
spring:
  application:
    name: dandan-keycloak

keycloak:
  url: http://localhost:5000
  realm: master
  clientid: admin-cli
  username: admin
  password: admin
```

### 🔐 Notes:

* Ensure Keycloak is running
* Admin credentials must have permission to manage users
* You can later externalize secrets (Vault recommended)

---

## 🚀 Running the Application

### 1. Start Keycloak

```bash
docker run -p 5000:8080 quay.io/keycloak/keycloak:latest start-dev
```

### 2. Build the project

```bash
mvn clean install
```

### 3. Run the application

```bash
mvn spring-boot:run
```

App will start at:

```
http://localhost:8080
```

---

## 📡 API Endpoints

### 👤 Customer Onboarding

```
POST /customers/onboard
```

### 🏪 Merchant Onboarding

```
POST /merchants/onboard
```

### 👨‍💼 Staff Onboarding

```
POST /staff/onboard
```

---

## 🧾 Sample Request

### Merchant Onboarding

```json
{
  "merchantName": "ABC Store",
  "email": "owner@abc.com",
  "phone": "9876543210"
}
```

---

## 🧠 How It Works

1. Receives onboarding request via REST API
2. Calls `KeycloakProvisioningService`
3. Uses Keycloak Admin Client to:

   * Create user
   * Assign roles
   * Add user to groups (multi-tenancy model)
4. Returns success/failure response

---

## 🧩 Key Features

* ✅ Centralized Keycloak provisioning
* ✅ Multi-tenant ready (merchant-based grouping)
* ✅ Clean separation of controller, service, DTO
* ✅ Easily extendable for roles & permissions

---

## 🔮 Future Enhancements

* Token-based authentication (instead of admin credentials)
* Integration with OpenFGA for fine-grained authorization
* Audit logging
* Retry & error handling improvements
* Bulk onboarding support

---

## 🧪 Testing

```bash
mvn test
```

---

## 👨‍💻 Author
Dandan Platform Backend Service