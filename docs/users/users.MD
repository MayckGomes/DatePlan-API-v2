# User Routes

## All routes starts with `/users`

--- 

### PATCH `/changeName`

Request body expected:

   ```
   {
        "newName": String
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
        "newPassword": String
        "refreshToken": String
   }
   ```

#### Returns

* `400 - BAD REQUEST` - Invalid (null parameters, size invalid) .
* `401 - UNAUTHORIZED  ` - JWT invalid.
* `404 - NOT FOUND  ` - user dont exists.

---

### DELETE `/`

Request body expected:

   ```
   {
        "id": Long
        "refreshToken": String
   }
   ```

#### Returns

* `400 - BAD REQUEST` - Invalid (null parameters, size invalid) .
* `401 - UNAUTHORIZED  ` - JWT invalid.
* `404 - NOT FOUND  ` - user dont exists.
* `409 - CONFLICT  ` - jwt user id and body request user id dont matches. | user in a relationship

---

### PATCH `/updateNotificationToken`

Request body expected:

   ```
   {
        "notificationToken" : String
   }
   ```

#### Returns

* `400 - BAD REQUEST` - Invalid (null parameters, size invalid) .
* `401 - UNAUTHORIZED  ` - JWT invalid.
* `404 - NOT FOUND  ` - user dont exists.

---

### PATCH `/updatePolicyPrivacy`

Request body expected:

   ```
   {
        "version" : Long
        "acceptedDate" : String
   }
   ```

#### Returns

* `400 - BAD REQUEST` - Invalid (null parameters, size invalid) .
* `401 - UNAUTHORIZED  ` - JWT invalid.
* `404 - NOT FOUND  ` - user dont exists.

---


### PATCH `/updateTermsOfUse`

Request body expected:

   ```
   {
        "version" : Long
        "acceptedDate" : String
   }
   ```

#### Returns

* `400 - BAD REQUEST` - Invalid (null parameters, size invalid) .
* `401 - UNAUTHORIZED  ` - JWT invalid.
* `404 - NOT FOUND  ` - user dont exists.