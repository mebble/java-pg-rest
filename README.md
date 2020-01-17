# java-pg-rest

A Java REST API

## Requirements:

- Java 11
- Gradle 6
- Postgres 12

## Setup

Set up the database:
```
./gradlew dbSetup
```

Run the server in development:
```
./gradlew run
```

Build the Uber jar:
```
./gradlew shadowJar
```

## API

The REST API structure:

`GET /items` Fetches all items

`GET /items/:id` Fetches an item at location `:id`

`POST /items` Posts a new item

`PUT /items/:id` Replaces an item at location `:id`. If an item doesn't exist at this location, create an item there

`DELETE /items/:id` Perform soft deletion of item at location `:id`. If a new item is `PUT` at the location of a deleted item, the deleted item is replaced
