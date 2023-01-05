package com.musobek.clickapp.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;


@Component
public class JwtProvayder {

    private static final long extraTime = 1000*60*60*24*10; // 10 kunlik

    private static final  String secretKey = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
//    Key key = new SecretKeySpec( //WRONG secretKeyEncoded.getBytes(), SignatureAlgorithm.HS256.getJcaName());
//            secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
     String secretKeyEncoded = Base64.getEncoder().encodeToString(secretKey.getBytes());
    Date expireDate = new Date(System.currentTimeMillis() + extraTime);

    public String generateToken(
            String userName){


        return Jwts
                .builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secretKeyEncoded)
                .compact();
    }

    //token aynan yaroqli va bzniki ekanligini tekshirish
    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public  String getUserNameFromToken(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

        }catch (Exception e) {
            return null;
        }
    }
}
