package com.example.server_controller;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey; 

@Service 
public class jwtUtils { 
    private final static String signingKey = "d0fc0f43ca8cdb0bad9a443df3c898bb8f38ae916e4be51955d3fb0515de42b3" + 
                                            "c4b678bc31842f11dc8ecf1a0c32a1ef9a011fe496c9b5ee6b3c6e534b087972" + 
                                            "a9631921b8d340920728ed65da81e30a6b276577e7f7e8f35dab9044dbbd24d7" + 
                                            "6ee2272fb44d60d71876c36f45697e3c1105bd4c21a0f5a9fccac1fd497ffba8" + 
                                            "c1ca3b06229651db7d3b499771cc3b76420f4e13ced83b9638e51a4d2a078681" + 
                                            "2a78af5f2467391e773551578a00bb1f2490b283c9e82899e01d581fa6358bd3" + 
                                            "72cfa0ae58daec1b0c7d3a16fcf67a4d6bc5ee1b3de9743f705e878e53ee7fc6" + 
                                            "dbf6f1e4c3fe4a65aef186956ea64e987ad8d8e37a9413cea5824886a502deb5" + 
                                            "70b7f718d950e2d039a1ca0956b76d91534ca1ffac2bbd365b1b17d6edd064a3" + 
                                            "150ce6678e560e3628fa68f028ccdd0f577a0a65d84a9c31d50942b8f6fbc43c"; 
    public static String genToken(String username){ 
        SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder().subject(username).signWith(key).claim("permissions","rw").compact(); 
    } 
    public static Boolean rwPermissions(String auth){ 
        SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8)); 
        Claims claimsJws = Jwts.parser()
                                .verifyWith(key)
                                .build()
                                .parseSignedClaims(auth)
                                .getPayload(); 
        return claimsJws.get("permissions").equals("rw"); 
    } 
    public static String getUsername(String auth){ 
        SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8)); 
        Claims claimsJws = Jwts.parser()
                                .verifyWith(key)
                                .build()
                                .parseSignedClaims(auth)
                                .getPayload(); 
        return claimsJws.getSubject(); 
    }
}
