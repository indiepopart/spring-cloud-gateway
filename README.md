# Tutorial: Spring Cloud Gateway OAuth2 Patterns

This repository contains all the code for testing a Spring Cloud Gateway, and different OAuth2 patterns with Okta as authorization server.

**Prerequisites:**

- Java 8+
- cURL

## Getting Started

To install this example, run the following commands:
```bash
git clone https://github.com/indiepopart/spring-cloud-gateway.git
```

## Create the API Gateway Client Application in Okta

Log in to your Okta Developer account (or [sign up](https://developer.okta.com/signup/) if you don’t have an account).
Setup the client application:

From the **Applications** page, choose **Add Application**. On the Create New Application page, select **Web**. Set the following values:
- Name: API Gateway
- Base URIs: http://localhost:8080/
- Login redirect URIs: http://localhost:8080/login/oauth2/code/okta
- Logout redirect URIs: http://localhost:8080
- Grant type allowed: Authorization Code, Refresh Token

Copy the **ClientId** and **ClientSecret**. Copy the issuer, found under **API** > **Authorization Servers**.

## Create the Cart Service Client Application in Okta

From the **Applications** page, choose **Add Application**. On the Create New Application page, select **Service**. Set the following values:
- Name: Cart Service

Copy the **ClientId** and **ClientSecret**.

## Run the applications with Maven

Run `eureka`:

```shell
cd spring-gateway/eureka
./mvnw spring-boot:run
```

Run `api-gateway`:

```shell
cd spring-gateway/api-gateway
OKTA_OAUTH2_ISSUER={yourOktaIssuer} \
OKTA_OAUTH2_CLIENT_ID={yourOktaClientId} \
OKTA_OAUTH2_CLIENT_SECRET={yourOktaClientSecret} \
./mvnw spring-boot:run
```

Run `cart-service`:

```shell
cd spring-gateway/cart-service
PRICING_OAUTH2_CLIENT_CLIENTID={serviceClientId} \
PRICING_OAUTH2_CLIENT_CLIENTSECRET={serviceClientSecret} \
./mvnw spring-boot:run
```

Run `pricing-service`:

```shell
cd spring-gateway/pricing-service
./mvnw spring-boot:run
```


Got to http://localhost:8080/greeting and login with Okta.
Copy the accessToken and send a create cart request through the gateway using curl:

```shell
curl -v\
  -d '{"customerId": "uijoon@mail.com", "lineItems": [{ "productName": "jeans", "quantity": 1}]}' \
  -H "Authorization: Bearer {accessToken}" \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json' \
  http://localhost:8080/cart
```
