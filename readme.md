# Email Service API README

Welcome to the Email Service API documentation! This API allows you to send personalized emails using Amazon SES (Simple Email Service) by providing a JSON payload containing recipient email addresses, an HTML template name, an email subject and variable values to customize the email content.

## Table of Contents
- [Email Service API README](#email-service-api-readme)
  - [Table of Contents](#table-of-contents)
  - [Getting Started](#getting-started)
  - [API Endpoints](#api-endpoints)
    - [Send Email](#send-email)
  - [Request Format](#request-format)
    - [Examples](#examples)

## Getting Started

To use this Email Service API, you will need to obtain API credentials and have an active Amazon SES account. If you don't have an Amazon SES account, you can sign up [here](https://aws.amazon.com/ses/).

Once you have an Amazon SES account, follow these steps:

1. **Install Necessary Dependencies**: Ensure you have the required dependencies installed to make API requests. You may need libraries like `requests` for Python or an HTTP client for your programming language of choice.

2. **Configure Amazon SES**: Set up your Amazon SES account, verify your sender email address, and configure your SES account's SMTP settings. Make note of your SES SMTP server and credentials. This has to be done in the config.properties file located in the root folder.

3. **Test API Calls**: Before integrating this API into your application, test API calls using a tool like [Postman](https://www.postman.com/) or `curl` to ensure everything is working as expected.


## API Endpoints

### Send Email

- **URL**: `/send`
- **HTTP Method**: POST
- **Description**: Send a personalized email using Amazon SES.
- **Request Payload**: JSON with the following fields:
  - `clientEmail` (string): The recipient's email address.
  - `template` (string): The name of the HTML template to use for the email.
  - `subject` (string): Subject of the email being sent.
  - `secretKey` (string): Value of the secret key hardcoded in the api code.
  - `template_values` (object): Key-value pairs representing variables to be replaced in the template. It is composed of an array with 2 strings:
    - `valueType` (string): Name of the value to be replaced in the HTML.
    - `valueString` (string):  Value to replace in the given name.
  
## Request Format

Example JSON request payload:

```json
{
    "template": "TEMPLATE",
    "clientEmail": "client@client.com",
    "subject": "PAYMENT COMPLETED",
    "secretKey": "RaNdOmSeCrEtStRiNg",
    "values": [
        {
            "valueType": "CLIENT_NAME",
            "valueString": "John Smith"
        },
                {
            "valueType": "AMOUNT",
            "valueString": "5"
        },
                {
            "valueType": "DATE",
            "valueString": "04/9/2023"
        }
    ]
}
```

### Examples

<b>Python:</b>

```
import http.client
import json

conn = http.client.HTTPSConnection("localhost", 8081)
payload = json.dumps({
  "template": "TEMPLATE",
  "clientEmail": "client@email.com",
  "subject": "PAYMENT COMPLETED",
  "secretKey": "RaNdOmSeCrEtStRiNg",
  "values": [
    {
      "valueType": "CLIENT_NAME",
      "valueString": "John Smith"
    },
    {
      "valueType": "AMOUNT",
      "valueString": "5"
    },
    {
      "valueType": "DATE",
      "valueString": "04/9/2023"
    }
  ]
})
headers = {
  'Content-Type': 'application/json'
}
conn.request("POST", "/send", payload, headers)
res = conn.getresponse()
data = res.read()
print(data.decode("utf-8"))
```

<b>Curl:</b>

```
curl --location 'http://localhost:8081/send' \
--header 'Content-Type: application/json' \
--data-raw '{
    "template": "TEMPLATE",
    "clientEmail": "client@email.com",
    "subject": "PAYMENT COMPLETED",
    "secretKey": "RaNdOmSeCrEtStRiNg",
    "values": [
        {
            "valueType": "CLIENT_NAME",
            "valueString": "John Smith"
        },
                {
            "valueType": "AMOUNT",
            "valueString": "5"
        },
                {
            "valueType": "DATE",
            "valueString": "04/9/2023"
        }
    ]
}'
```