# Relationship routes

## All routes starts with `/relationship`

---

### GET `/`

Behavior expected:

`The relationship id is get by access token`


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

### PUT `/initialDay`

Request body expected:

   ```
   {
        "initialDay": ""
   }
   ```

Behavior expected:

`The relationship id is get by access token`


#### Returns

* `400 - BAD REQUEST` - Invalid parameters(null parameters).
* `404 - NOT FOUND` - relationship not found | user dont have relationship.

---

