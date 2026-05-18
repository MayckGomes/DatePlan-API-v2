# Invite routes

## All routes starts with `/invites`

--- 

### POST `/create`

Request body expected:

   ```
   {
        "publicIdTo": String,
        "publicIdFrom": String
   }
   ```

Response body expected

   ```
   {
        "inviteId": Long
   }
   ```

#### Returns

* `400 - BAD REQUEST` - Invalid parameters(null parameters).
* `404 - NOT FOUND` - User (from or to) not exists.
* `409 - CONFLIT` - User (from or to) already in relationship.

---

### GET `/toUser`

Behavior expected:

`The user id is get by access token`

Response body expected

   ```
   [
        {
            "id": Long,
            "toId": Long,
            "toName": String,
            "fromId": Long,
            "fromName": String,
        }
   ]
   ```

#### Returns

* `400 - BAD REQUEST` - Invalid parameters(null parameters).
* `404 - NOT FOUND` - User not exists.

---

### POST `/accept`

Request body expected:

   ```
   {
        "inviteId": Long
   }
   ```

Response body expected

   ```
   {
        "relationshipId": Long
        "accessToken: String
   }
   ```

#### Returns

* `400 - BAD REQUEST` - Invalid parameters(null parameters).
* `403 - FORBBIDEN` - User is not the decision maker.
* `404 - NOT FOUND` - User (sender or reciver) not exists, invite not exists.
* `409 - CONFLIT` - User (sender or reciver) already in relationship.

---

### POST `/decline`

Request body expected:

   ```
   {
        "inviteId": Long
   }
   ```

#### Returns

* `403 - FORBBIDEN` - User is not the decision maker.
* `404 - NOT FOUND` - invite not exists.

---