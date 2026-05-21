# Relationship routes

## All routes starts with `/relationship`

---

### GET `/`

Behavior expected:

`The relationship id is get by access token`

Response body expected:

   ```
   {
        "id": Long
        "userId1": Long
        "nameUser1": String
        "userId2": Long
        "nameUser2": String
        "nameUser2": Long
   }
   ```


#### Returns

* `400 - BAD REQUEST` - Invalid parameters(null parameters) .
* `404 - NOT FOUND` - relationship not found | user in this relationship is not found | user dont have relationship.

---

### DELETE `/`

Behavior expected:

`The relationship id is get by access token`


#### Returns

* `400 - BAD REQUEST` - Invalid parameters(null parameters) .
* `404 - NOT FOUND` - relationship not found | user in this relationship is not found | user dont have relationship.

---

### PATCH `/initialDay`

Request body expected:

   ```
   {
        "initialDay": String
   }
   ```

Behavior expected:

`The relationship id is get by access token`


#### Returns

* `400 - BAD REQUEST` - Invalid parameters(null parameters).
* `404 - NOT FOUND` - relationship not found | user dont have relationship.

---

