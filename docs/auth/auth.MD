# Auth routes

## All routes starts with `/auth`

--- 

### POST `/register`

Request body expected:

   ```
   {
        "name": String,
        "email": String,
        "password": String
   }
   ```

Response body expected

   ```
   {
        "accessToken": String,
        "refreshToken": String
   }
   ```

#### Returns

 * `400 - BAD REQUEST` - Invalid parameters(null parameters, size invalid or email invalid) .
 * `409 - CONFLIT` - user already exists.

---

### POST `/login`

Request body expected:

   ```
   {
        "email": String,
        "password": String
   }
   ```

Response body expected

   ```
   {
        "accessToken": String,
        "refreshToken": String
   }
   ```

#### Returns

* `400 - BAD REQUEST` - Invalid parameters.
* `404 - NOT FOUND` - user dont exists.

---

### POST `/logout`

Request body expected:

   ```
   {
        "accessToken": String,
        "refreshToken": String
   }
   ```

#### Returns

* `400 - BAD REQUEST` - Invalid parameters

---

### POST `/refreshToken`

header expect: Authorization: Bearer <Token>

Response body expected

   ```
   {
        "accessToken": String,
        "refreshToken": String
   }
   ```

#### Returns

* `401 - UNAUTHORIZED` - Invalid jwt.
* `400 - BAD REQUEST` - Invalid format


