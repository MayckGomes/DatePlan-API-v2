# User Routes

## All routes starts with `/users`

--- 

### PATCH `/changeName`

Request body expected:

   ```
   {
        "newName: ""
   }
   ```

#### Returns

* `400 - BAD REQUEST` - Invalid parameters(null parameters, size invalid) .
* `404 - NOT FOUND  ` - user dont exists.
* `401 - UNAUTHORIZED` - token invalid

--- 

### PATCH `/changePassword`

Request body expected:

   ```
   {
        "newPassword: ""
        "refreshToken: ""
   }
   ```

#### Returns

* `400 - BAD REQUEST` - Invalid (null parameters, size invalid) .
* `401 - UNAUTHORIZED  ` - JWT invalid.
* `404 - NOT FOUND  ` - user dont exists.