package org.example.webtechproject;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
//import java.util.Map;

public class JwtToken {

    private static final String ACCESS_TOKEN_SECRET = "2c89043496821892a0d2785bea5742ec77cebb9587667730e75c551de259e7b8cad3788d782cab95ce63819c47f289b8b5d1612f3a87f1bad2a5766c037bd4cc";
    private static final String REFRESH_TOKEN_SECRET = "12d8862fbed17cc54dd8e37025b389b2b519d7531efa869e2e850ed7d223ff9c2166a1c22e4a54d0ba23b080da0bd8b7b9c376e5497738b126cccf65ef6174a5";
    private static final long ACCESS_TOKEN_EXPIRATION = 60 * 60 * 1000; // 1 hour
    private static final long REFRESH_TOKEN_EXPIRATION = 30L * 24 * 60 * 60 * 1000; // 30 days

    public String generateAccessToken(Integer subject, Map<String, Object> userInfo) {
        return generateToken(subject, ACCESS_TOKEN_SECRET, ACCESS_TOKEN_EXPIRATION, userInfo);
    }

    public String generateRefreshToken(Integer subject, Map<String, Object> userInfo) {
        return generateToken(subject, REFRESH_TOKEN_SECRET, REFRESH_TOKEN_EXPIRATION,new HashMap<>());
    }

    public String generateToken(Integer subject, String secret, long expiration, Map<String, Object> userInfo) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(String.valueOf(subject))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret);

        // Add user info to the token payload
        userInfo.forEach(builder::claim);

        return builder.compact();
    }

    public Claims validateToken(String token, String secret) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException | MalformedJwtException  | UnsupportedJwtException | IllegalArgumentException e) {
            // Handle invalid or expired token
            return null;
        }
    }

//    public static void main(String[] args) {
//        // Example usage
//        String subject = "user123";
//        Map<String, Object> userInfo = new HashMap<>();
//        userInfo.put("userId", 123);
//        userInfo.put("email", "user@example.com");
//
//        String accessToken = generateAccessToken(subject, userInfo);
//        String refreshToken = generateRefreshToken(subject, userInfo);
//
//        System.out.println("Access Token: " + accessToken);
//        System.out.println("Refresh Token: " + refreshToken);

    // Validate access token
//        Claims accessClaims = validateToken(accessToken, ACCESS_TOKEN_SECRET);
//        if (accessClaims != null) {
//            System.out.println("Access Token is valid for subject: " + accessClaims.getSubject());
//            System.out.println("User ID: " + accessClaims.get("userId"));
//            System.out.println("Email: " + accessClaims.get("email"));
//        } else {
//            System.out.println("Access Token is invalid or expired.");
//        }
//
//        // Validate refresh token
//        Claims refreshClaims = validateToken(refreshToken, REFRESH_TOKEN_SECRET);
//        if (refreshClaims != null) {
//            System.out.println("Refresh Token is valid for subject: " + refreshClaims.getSubject());
//            System.out.println("User ID: " + refreshClaims.get("userId"));
//            System.out.println("Email: " + refreshClaims.get("email"));
//        } else {
//            System.out.println("Refresh Token is invalid or expired.");
//        }
//    }
}