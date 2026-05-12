# User Routes

## All routes starts with `/users`
with except DELETE method.

--- 

### PATCH `/changeName`

Request body expected:

   ```
   {
        "newName": ""
   }
   ```

#### Returns

* `400 - BAD REQUEST` - Invalid parameters(null parameters, size invalid) .
* `401 - UNAUTHORIZED` - token invalid
* `404 - NOT FOUND  ` - user dont exists.

--- 

### PATCH `/changePassword`

Request body expected:

   ```
   {
        "newPassword": ""
        "refreshToken": ""
   }
   ```

#### Returns

* `400 - BAD REQUEST` - Invalid (null parameters, size invalid) .
* `401 - UNAUTHORIZED  ` - JWT invalid.
* `404 - NOT FOUND  ` - user dont exists.

---

### DELETE `/users`

Request body expected:

   ```
   {
        "id": 
        "refreshToken": ""
   }
   ```

#### Returns

* `400 - BAD REQUEST` - Invalid (null parameters, size invalid) .
* `401 - UNAUTHORIZED  ` - JWT invalid.
* `404 - NOT FOUND  ` - user dont exists.
* `409 - CONFLICT  ` - jwt user id and body request user id dont matches.