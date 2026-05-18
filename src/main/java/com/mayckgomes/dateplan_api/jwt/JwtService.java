package com.mayckgomes.dateplan_api.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mayckgomes.dateplan_api.domains.RefreshTokenDecoded;
import com.mayckgomes.dateplan_api.domains.UserDomain;
import com.mayckgomes.dateplan_api.dto.auth.TokensResponse;
import com.mayckgomes.dateplan_api.exception.custom.token.TokenExpiredException;
import com.mayckgomes.dateplan_api.exception.custom.token.TokenInvalidException;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class JwtService {

    Dotenv dotenv = Dotenv.load();

    final private String secret = dotenv.get("SECRET");

    final private String issuer = dotenv.get("ISSUER");

    final private String audience = dotenv.get("AUDIENCE");

    final private Algorithm algorithm = Algorithm.HMAC256(secret);

    public String createAccessToken(UserDomain user){

        int fifteenMinutesInSeconds = 900;

        Instant expireTimeAccess = Instant.now().plusSeconds(fifteenMinutesInSeconds);

        String accessTokenId = UUID.randomUUID().toString();

        return JWT.create()
                .withJWTId(accessTokenId)
                .withClaim("id", user.getId())
                .withClaim("name", user.getName())
                .withClaim("email", user.getEmail())
                .withClaim("relationshipId", user.getRelationshipId())
                .withClaim("publicId", user.getPublicId())
                .withClaim("plan", user.getPlan())
                .withExpiresAt(expireTimeAccess)
                .withAudience(audience)
                .withIssuer(issuer)
                .sign(algorithm);

    }

    public String createRefreshToken(UserDomain user){

        int oneWeekInSeconds = 604800;

        Instant expireTimeRefresh = Instant.now().plusSeconds(oneWeekInSeconds);

        String refreshTokenId = UUID.randomUUID().toString();

        return JWT.create()
                .withJWTId(refreshTokenId)
                .withClaim("id", user.getId())
                .withExpiresAt(expireTimeRefresh)
                .withAudience(audience)
                .withIssuer(issuer)
                .sign(algorithm);

    }

    public TokensResponse createTokens(UserDomain user){

        return new TokensResponse(
                createAccessToken(user),createRefreshToken(user)
        );
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

        } catch (com.auth0.jwt.exceptions.TokenExpiredException exception){
            throw new TokenExpiredException();
        } catch (JWTVerificationException exception){
            throw new TokenInvalidException();
        }

    }

    public RefreshTokenDecoded decodeRefreshToken(String token){

        try{

            DecodedJWT verifier = JWT.require(algorithm)
                    .build()
                    .verify(token);

            return new RefreshTokenDecoded(verifier.getId(),verifier.getClaim("id").asLong());

        } catch (com.auth0.jwt.exceptions.TokenExpiredException exception){
            throw new TokenExpiredException();
        } catch (JWTVerificationException exception){
            throw new TokenInvalidException();
        }

    }

    public String getTokenId(String token){

        DecodedJWT verifier = JWT.require(algorithm)
                .build()
                .verify(token);

        return verifier.getId();

    }

}
