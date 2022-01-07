package com.tecnics.einvoice.chat;

import com.sun.security.auth.UserPrincipal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;


public class UserHandshakeHandler extends DefaultHandshakeHandler {
    private final Logger LOG = LoggerFactory.getLogger(UserHandshakeHandler.class);

    public static Authentication getAuthenticatedUser() {

        SecurityContext context = SecurityContextHolder.getContext();
        return context.getAuthentication();

    }
    private List<String> getAuthHeader(ServerHttpRequest request) {
    	
    	if(request.getHeaders().getOrEmpty("Authorization")==null)
    		return null;
    	else    		
        return request.getHeaders().getAccessControlRequestHeaders();
    }
    UUID getAuthToken(ServerHttpRequest request) {
        try {
        	
            return UUID.fromString(UriComponentsBuilder.fromHttpRequest(request).build()
                    .getQueryParams().get("authentication").get(0));
        } catch (NullPointerException e) {
            return null;
        }
    }

        @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
//        	List<String> headers = request.getHeaders()
//                    .get(ConstantsSecurity.AUTHORIZATION_HEADER);
        	List<String> header=getAuthHeader(request);
        		System.out.println("query"+request.getURI().getQuery());
        	 UUID authToken = getAuthToken(request);
        	// System.out.println(authToken);
        //	System.out.println("auth header"+header);

        	final String randomId = UUID.randomUUID().toString();
//           String ANONYMOUS = "ROLE_ANONYMOUS";
          // HttpSession session;
          // String sessionId = session.getId();  
 //       Principal principal = request.getPrincipal();
//        if (principal == null) {
//            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//            authorities.add(new SimpleGrantedAuthority(ANONYMOUS));
//            principal = new AnonymousAuthenticationToken("WebsocketConfiguration", "anonymous", authorities);
//        }
//        return principal;
       // LOG.info("User with ID '{}' opened the page", randomId);
//        Authentication a=getAuthenticatedUser();
//        LOG.info(a.getName());
        LOG.info("User with ID '{}' opened the page", request.getURI().getQuery());

       return new UserPrincipal(request.getURI().getQuery());
    }
}