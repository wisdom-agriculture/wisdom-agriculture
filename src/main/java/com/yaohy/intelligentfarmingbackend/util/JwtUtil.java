package com.yaohy.intelligentfarmingbackend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;

public class JwtUtil {

    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;
    private static final String TOKEN_SECRET = "intelligentfarming";

    public static String sign(String id,String end){
        Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

        //创建头部
        HashMap<String,Object> header = new HashMap<>();
        header.put("typ","JWT");
        header.put("alg","HS256");

        return JWT.create().withHeader(header).withClaim("id",id).withClaim("end",end).withIssuer("biber").withExpiresAt(date).sign(algorithm);

    }

    public static boolean verity(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (JWTVerificationException e) {
            return false;
        }
    }


//    public static String decodeRole(String token){
//        try {
//            DecodedJWT jwt = JWT.decode(token);
//            Claim userId = jwt.getClaim("role");
//            return userId.asString();
//        } catch (JWTDecodeException e){
//            return null;
//        }
//    }

    public static Integer decodeId(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            Claim userId = jwt.getClaim("id");
            return Integer.parseInt(userId.asString());
        } catch (JWTDecodeException e){
            return null;
        }

    }

    public static String decodeEnd(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            Claim end = jwt.getClaim("end");
            return end.asString();
        } catch (JWTDecodeException e){
            return null;
        }

    }

}
