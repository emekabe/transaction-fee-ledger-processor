# transaction-fee-ledger-processor

A kotlin service that calculates, charges and records transaction fees using double-entry accounting principles.

This project was created using the [Ktor Project Generator](https://start.ktor.io).

Here are some useful links to get you started:

- [Ktor Documentation](https://ktor.io/docs/home.html)
- [Ktor GitHub page](https://github.com/ktorio/ktor)
- The [Ktor Slack chat](https://app.slack.com/client/T09229ZC6/C0A974TJ9). You'll need
  to [request an invite](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up) to join.

## Features

Here's a list of features included in this project:

| Name                                                               | Description                                                                        |
|--------------------------------------------------------------------|------------------------------------------------------------------------------------|
| [CORS](https://start.ktor.io/p/cors)                               | Enables Cross-Origin Resource Sharing (CORS)                                       |
| [Routing](https://start.ktor.io/p/routing)                         | Provides a structured routing DSL                                                  |
| [Content Negotiation](https://start.ktor.io/p/content-negotiation) | Provides automatic content conversion according to Content-Type and Accept headers |
| [Jackson](https://start.ktor.io/p/ktor-jackson)                    | Handles JSON serialization using Jackson library                                   |
| [Koin](https://start.ktor.io/p/koin)                               | Provides dependency injection                                                      |
| [Restate](https://restate.dev/)                                    | For workflow creation and execution (durable execution platform)                   |
| [TigerBeetle](https://tigerbeetle.com/)                            | Database                                                                           |
| [Gradle](https://gradle.org/)                                      | Build tool                                                                         |

## Language version

JDK Version Used: 21

Kotlin Version: 2.2.20

## Setting up TigerBeetle

TigerBeetle is a reliable, fast, and highly available database for financial accounting.
TigerBeetle is used in this application to record calculated fees in different ledger accounts according to the transaction type.

To set up TigerBeetle, follow the steps [here](https://docs.tigerbeetle.com/start/).

## Building & Running

To build or run the project, use one of the following tasks:

| Task                          | Description                                                          |
| -------------------------------|----------------------------------------------------------------------|
| `./gradlew test`              | Run the tests                                                        |
| `./gradlew build`             | Build everything                                                     |
| `buildFatJar`                 | Build an executable JAR of the server with all dependencies included |
| `buildImage`                  | Build the docker image to use with the fat JAR                       |
| `publishImageToLocalRegistry` | Publish the docker image locally                                     |
| `run`                         | Run the KTOR server                                                  |
| `runDocker`                   | Run using the local docker image                                     |

If the server starts successfully, you'll see the following output:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8082
```

### Sample cURL to call the transaction fee endpoint

#### Request
```bash
curl --location 'localhost:8082/transaction/fee' \
--header 'Content-Type: application/json' \
--data '{
    "transaction_id": "txn_001",
    "amount": 2000000,
    "asset": "USD",
    "asset_type": "FIAT",
    "type": "Mobile Top Up",
    "state": "SETTLED - PENDING FEE",
    "created_at": "2025-08-30 15:42:17.610059"
}'
```

#### Response
```json
{
  "transaction_id": "txn_001",
  "amount": 2000000,
  "asset": "USD",
  "type": "Mobile Top Up",
  "fee": 3000,
  "rate": 0.0015,
  "description": "Standard fee rate of 0.15%"
}
```

## Reading Recorded Entries in TigerBeetle

Assuming the transaction posts a journal entry on a debit account `100000` and credit account `100001`, Run this command in your tigerbeetle CLI to get the transfers on account 10000. Check [here](https://docs.tigerbeetle.com/start/) on how to get to the CLI.

```
get_account_transfers account_id=100000 flags=debits|credits;
```

The output should come out like this

```
{
  "id": "2125849725346249226574125730965464587",
  "debit_account_id": "100000",
  "credit_account_id": "100001",
  "amount": "3000",
  "pending_id": "0",
  "user_data_128": "1",
  "user_data_64": "0",
  "user_data_32": "0",
  "timeout": "0",
  "ledger": "1",
  "code": "1",
  "flags": [],
  "timestamp": "1758461678038714001"
}
```

## Setting up Restate

To set up Restate Server on your local, follow the steps [here](https://docs.restate.dev/quickstart).

The main function for execution can be found in the `FeeWorkflow.kt` file in the `workflow` package.

If the server starts successfully, you'll see the following output:

```
Starting FeeWorkflow Restate Server...
Started FeeWorkflow Restate Server in 2 seconds
Responding at http://localhost:8081
```

The Restate server UI can be accessed in your browser on http://localhost:9070 for registering deployments.
Kindly register the endpoint http://localhost:8081 as a service deployment.

### Sample cURL to call the transaction fee endpoint via Restate

#### Request
```bash
curl --location 'localhost:8080/FeeWorkflow/chargeFee' \
--header 'Content-Type: application/json' \
--data '{
    "transaction_id": "txn_001",
    "amount": 2000000,
    "asset": "USD",
    "asset_type": "FIAT",
    "type": "Mobile Top Up",
    "state": "SETTLED - PENDING FEE",
    "created_at": "2025-08-30 15:42:17.610059"
}'
```

#### Response
```json
{
    "transaction_id": "txn_001",
    "amount": 2000000,
    "asset": "USD",
    "type": "Mobile Top Up",
    "fee": 3000,
    "rate": 0.0015,
    "description": "Standard fee rate of 0.15%"
}
```
