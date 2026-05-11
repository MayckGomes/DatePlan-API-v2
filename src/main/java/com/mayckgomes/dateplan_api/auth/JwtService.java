package com.mayckgomes.dateplan_api.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mayckgomes.dateplan_api.domains.RefreshTokenDecoded;
import com.mayckgomes.dateplan_api.domains.UserDomain;
import com.mayckgomes.dateplan_api.dto.TokensResponse;
import com.mayckgomes.dateplan_api.exception.custom.token.TokenExpiredException;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class JwtService {

    Dotenv dotenv = Dotenv.load();

    final private String secret = dotenv.get("SECRET");



    final private Algorithm algorithm = Algorithm.HMAC256(secret);

    public TokensResponse createTokens(UserDomain user){

        int fifteenMinutesInSeconds = 900;
        int tenSeconds = 10;
        int oneWeekInSeconds = 604800;

        Instant expireTimeAccess = Instant.now().plusSeconds(fifteenMinutesInSeconds);
        Instant expireTimeRefresh = Instant.now().plusSeconds(oneWeekInSeconds);

        String accessTokenId = UUID.randomUUID().toString();
        String refreshTokenId = UUID.randomUUID().toString();

        try {

            return new TokensResponse(
                    JWT.create()
                            .withJWTId(accessTokenId)
                            .withClaim("id", user.getId())
                            .withClaim("name", user.getName())
                            .withClaim("email", user.getEmail())
                            .withClaim("relationshipId", user.getRelationshipId())
                            .withClaim("publicId", user.getPublicId())
                            .withClaim("plan", user.getPlan())
                            .withExpiresAt(expireTimeAccess)
                            .sign(algorithm),
                    JWT.create()
                            .withJWTId(refreshTokenId)
                            .withClaim("id", user.getId())
                            .withExpiresAt(expireTimeRefresh)
                            .sign(algorithm)
            );

        } catch (JWTCreationException exception) {
            return null;
        }
    }

    public UserDomain decodeAccessToken(String token){

        try{

            DecodedJWT verifier = JWT.require(algorithm)
                    .build()
                    .verify(token);

            return new UserDomain(
                    verifier.getClaim("id").asLong(),
                    verifier.getClaim("publicId").asString(),
                    verifier.getClaim("name").asString(),
                    verifier.getClaim("email").asString(),
                    verifier.getClaim("relationshipId").asLong(),
                    verifier.getClaim("plan").asString(),
                    verifier.getClaim("notificationToken").asString()
            );

        } catch (JWTVerificationException exception){
            throw new TokenExpiredException();
        }

    }

    public RefreshTokenDecoded decodeRefreshToken(String token){

        try{

            DecodedJWT verifier = JWT.require(algorithm)
                    .build()
                    .verify(token);

            return new RefreshTokenDecoded(verifier.getId(),verifier.getClaim("id").asLong());

        } catch (JWTVerificationException exception){
            throw new TokenExpiredException(exception.getMessage());
        }

    }

    public String getTokenId(String token){

        DecodedJWT verifier = JWT.require(algorithm)
                .build()
                .verify(token);

        return verifier.getId();

    }

}
