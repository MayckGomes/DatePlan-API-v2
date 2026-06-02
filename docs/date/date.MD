# Date routes

## All routes starts with `/dates`

---

### GET `/`

Behavior expected:

`The relationship id is get by access token`

Response body expected:

   ```
   [
       {
            "id": Long
            "date": String,
            "time": String
            "local": String
            "title": String
            "description": String
            "iconId": Long
            "relationshipId": Long
            "authorId": Long
       }
   ]
   ```

#### Returns

* `400 - BAD REQUEST` - Invalid parameters(null parameters).
* `404 - NOT FOUND` - Relationship not exists.

---


### POST `/create`

Request body expected:

   ```
   {
        "date": String,
        "time": String
        "local": String
        "title": String
        "description": String
        "iconId": Long
        "relationshipId": Long
        "authorId": Long
   }
   ```


#### Returns

* `400 - BAD REQUEST` - Invalid parameters(null parameters).
* `404 - NOT FOUND` - Relationship not exists.

---

### PUT `/edit`

Request body expected:

   ```
   {
        "id": Long
        "date": String,
        "time": String
        "local": String
        "title": String
        "description": String
        "iconId": Long
        "relationshipId": Long
        "authorId": Long
   }
   ```


#### Returns

* `400 - BAD REQUEST` - Invalid parameters(null parameters).
* `404 - NOT FOUND` - Relationship not exists.
* `409 - CONFLICT` - Date not exists.

---

### DELETE `/delete`

Request body expected:

   ```
   {
        "dateId": Long
   }
   ```


#### Returns

* `400 - BAD REQUEST` - Invalid parameters(null parameters).
* `404 - NOT FOUND` - Relationship not exists.
* `409 - CONFLICT` - Date not exists.