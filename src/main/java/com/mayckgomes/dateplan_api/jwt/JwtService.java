package com.mayckgomes.dateplan_api.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mayckgomes.dateplan_api.domains.RefreshTokenDecoded;
import com.mayckgomes.dateplan_api.domains.UserDomain;
import com.mayckgomes.dateplan_api.dto.auth.TokensResponse;
import com.mayckgomes.dateplan_api.exception.custom.token.TokenExpiredException;
import com.mayckgomes.dateplan_api.exception.custom.token.TokenInvalidException;
import com.mayckgomes.dateplan_api.exception.custom.token.TokenInvalidTypeException;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

import java.time.Duration;
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

        Instant expireTimeAccess = Instant.now()
                .plus(Duration.ofMinutes(15));

        String accessTokenId = UUID.randomUUID().toString();

        return JWT.create()
                .withJWTId(accessTokenId)
                .withClaim("id", user.getId())
                .withClaim("type", "Access")
                .withExpiresAt(expireTimeAccess)
                .withAudience(audience)
                .withIssuer(issuer)
                .sign(algorithm);

    }

    public String createRefreshToken(UserDomain user){

        Instant expireTimeRefresh = Instant.now()
                .plus(Duration.ofDays(7));

        String refreshTokenId = UUID.randomUUID().toString();

        return JWT.create()
                .withJWTId(refreshTokenId)
                .withClaim("id", user.getId())
                .withClaim("type", "Refresh")
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

    public Long decodeAccessToken(String token){

        try{

            DecodedJWT verifier = JWT.require(algorithm)
                    .build()
                    .verify(token);

            if (!verifier.getClaim("type").asString().equals("Access")){
                throw new TokenInvalidTypeException("Access");
            }

            return verifier.getClaim("id").asLong();

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

            System.out.println(verifier.getClaim("type").asString());

            if (!verifier.getClaim("type").asString().equals("Refresh")){
                throw new TokenInvalidTypeException("Refresh");
            }

            return new RefreshTokenDecoded(verifier.getId(),verifier.getClaim("id").asLong());

        } catch (com.auth0.jwt.exceptions.TokenExpiredException exception){
            System.out.println(exception.getMessage());
            throw new TokenExpiredException();
        } catch (JWTVerificationException exception){
            throw new TokenInvalidException();
        }

    }

    public String getTokenId(String token){

        try {

            DecodedJWT decoded = JWT.decode(token);

            return decoded.getId();

        } catch (JWTDecodeException exception){
            throw new TokenInvalidException();
        }


    }

}
