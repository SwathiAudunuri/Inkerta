
package com.tecnics.einvoice.util;

import com.tecnics.einvoice.constants.Constants;
import com.tecnics.einvoice.exceptions.*;
import com.tecnics.einvoice.log.BaseLogger;
import com.tecnics.einvoice.exceptions.CustomExceptionHandler;
import com.tecnics.einvoice.model.UserLoginDetails;

import io.jsonwebtoken.*;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>This is a utility class to implement JWT tokens</p>
 * @author  shashi kumar
 * @version 1.0
 * @since 1.0
 */
@Service
public class JWTUtil {

    BaseLogger log = BaseLogger.getBaseLogger(JWTUtil.class);
    private String SECRET_KEY  = "G-KaPdSgUkXp2s5v8y/B?E(H+MbQeThWmYq3t6w9z$C&F)J@NcRfUjXn2r4u7x!A";

    /**
     *
     * @param userName
     * @return
     * @throws InternalException
     */
    public String getJWTToken(@NonNull  String userName,@NonNull UserLoginDetails user) throws InternalException {
        String methodName = "getJWTToken";
        log.logMethodEntry(methodName);
        String jwtToken  = null;
        try {
            Date now = new Date(System.currentTimeMillis());
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
            System.out.println(userName);
            jwtToken = Jwts.
                    builder().
                    claim(Constants.JWT_CLAIM_NAME,userName).
                    setSubject(userName).
                    setId(UUID.randomUUID().toString()).
                    claim("partnerId",user.getPartnerId()).
                    claim("partnerName",user.getPartnerName()).
                    claim("partnerType",user.getPartnerType()).
                    claim("roles",user.getRoles()).
                    claim("partnerRoles",user.getPartnerRoles()).
                    claim("userAlias",user.getUserAlias()).
                    claim("email",user.getEmail()).
                    claim("firstName",user.getFirstName()).
                    claim("lastName",user.getLastName()).              
                   
                    setIssuedAt(now).
                    setIssuer(Constants.JWT_TOKEN_ISSUER).
                    setExpiration(new Date(now.getTime() + ( Constants.JWT_TOKEN_EXPIRY_MINUTES * 60000))).
                    signWith(signingKey, SignatureAlgorithm.HS256).
                    compact();
        } catch (Exception e) {
            throw new InternalException(Ex.JWT_TOKEN_GENERATE.getKey(),Ex.formatMessage(Ex.JWT_TOKEN_GENERATE.getKeyMessage(),new String[]{userName}),e);
        }
        return jwtToken;
    }

    /**
     *
     * @param userName
     * @return
     * @throws InternalException
     */
    public String getRefreshJWTToken(@NonNull  String userName,@NonNull UserLoginDetails user) throws InternalException {
        String methodName = "getRefreshJWTToken";
        log.logMethodEntry(methodName);
        String jwtToken  = null;
        try {
            Date now = new Date(System.currentTimeMillis());
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
            jwtToken = Jwts.
                    builder().
                    claim(Constants.JWT_CLAIM_NAME,userName).
                    setSubject(userName).
                    setId(UUID.randomUUID().toString()).
                    claim("partnerId",user.getPartnerId()).
                    claim("partnerName",user.getPartnerName()).
                    claim("partnerType",user.getPartnerType()).
                    claim("roles",user.getRoles()).
                    claim("partnerRoles",user.getPartnerRoles()).
                    claim("userAlias",user.getUserAlias()).
                    claim("email",user.getEmail()).
                    claim("firstName",user.getFirstName()).
                    claim("lastName",user.getLastName()).  
                    setIssuedAt(now).
                    setIssuer(Constants.JWT_TOKEN_ISSUER).
                    setExpiration(new Date(now.getTime() + ( Constants.JWT_REFRESH_TOKEN_EXPIRY_MINUTES * 60000))).
                    signWith(signingKey, SignatureAlgorithm.HS256).
                    compact();
        } catch (Exception e) {
            throw new InternalException(Ex.JWT_TOKEN_GENERATE.getKey(),Ex.formatMessage(Ex.JWT_TOKEN_GENERATE.getKeyMessage(),new String[]{userName}),e);
        }
        return jwtToken;
    }

    /**
     *
     * @param jwtTokenString
     * @param userName
     * @return
     */
    public boolean verifyJWTToken(@NonNull String jwtTokenString, @NonNull String userName) {
        Jws<Claims> jwsClaims  = null;
        boolean isJWTValid = false;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
        try {
            jwsClaims =  Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(jwtTokenString);
            // if valid ... get expiration and check with current time
            Date tokenExpiry =  jwsClaims.getBody().getExpiration();
            Date now = new Date();
            if(now.compareTo(tokenExpiry) < 0) {
                isJWTValid = true;
            }
        } catch (Exception e) {
            if( e instanceof  io.jsonwebtoken.ExpiredJwtException) {
                throw new InternalException(Ex.JWT_TOKEN_EXPIRED.getKey(),Ex.formatMessage(Ex.JWT_TOKEN_EXPIRED.getKeyMessage(),new String[]{userName}),e);
            }
            throw e;
        }
        return isJWTValid;
    }

    /**
     *
     * @param jwtTokenString
     * @return
     */
    public String getUserIdFromToken(@NonNull  String jwtTokenString) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
        String userName = null;
            userName =   Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(jwtTokenString).getBody().getSubject();
        return userName;
    }
    
    public UserLoginDetails getUserObjFromToken(@NonNull  String jwtTokenString) {
  	  byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
        String userName = null;
        UserLoginDetails userObj=null;
        try {
      	  System.out.println("Before claims ");
    Claims claims = Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(jwtTokenString).getBody();
   // System.out.println("After claims ");
  //  System.out.println("claims toString="+claims.toString());
    
  //  System.out.println("PartnerId from claims = " + claims.get("partnerId"));
  //  System.out.println("PartnerName from claims = " + claims.get("partnerName"));
    userObj=new UserLoginDetails();
    userObj.setPartnerId((String)claims.get("partnerId"));
    userObj.setPartnerName((String)claims.get("partnerName"));
    userObj.setPartnerType((String)claims.get("partnerType"));
    
   userObj.setRoles((List<String>) claims.get("roles"));
    userObj.setUserId((String)claims.get("name"));
    userObj.setUserAlias((String)claims.get("userAlias"));
    userObj.setPartnerRoles((List<String>) claims.get("partnerRoles"));
    userObj.setEmail((String)claims.get("email"));
    userObj.setFirstName((String)claims.get("firstName"));
    userObj.setLastName((String)claims.get("firstName"));
    
    if(userObj==null)
  	  System.out.println("User Obj is null in getUserObjFromToken");
   
    
        } catch (Exception e) {
            if( e instanceof  io.jsonwebtoken.ExpiredJwtException) {
                throw new CustomExceptionHandler( "JWT token expired or Invalid Token thrown");
            }
            e.printStackTrace();
            throw new CustomExceptionHandler( "JWT token expired or Invalid Token thrown 1");
        }
   
    return userObj;
  }
}
