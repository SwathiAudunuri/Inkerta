package com.tecnics.einvoice.config;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tecnics.einvoice.exceptions.InternalException;
import com.tecnics.einvoice.exceptions.TokenExpiryException;
import com.tecnics.einvoice.util.JWTUtil;

public class JWTAuthorizationFilter

		extends OncePerRequestFilter {

	private JWTUtil jwtUtil = new JWTUtil();

	public JWTAuthorizationFilter() {
		super();
	}

	/**
	 *
	 * {@inheritDoc}
	 * <p>
	 * This methods do following business loigic
	 * </p>
	 * <ul>
	 * <li>Extract the Authorization from header</li>
	 * <li>Parse the Bearer token</li>
	 * <li>Call JWtUtil verify token method {@link JWTUtil}</li>
	 * <li>If token valid we will allow api request end point to access else we will
	 * deny access</li>
	 * </ul>
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException, InternalException {
		String header = httpServletRequest.getHeader("Authorization");
		String jwtToken = null;
		boolean canIgnore = false;
		boolean isInvalidToken = false;
		// ignore this token check if request for refresh token
		String endPointURL = httpServletRequest.getRequestURL().toString();
		if (endPointURL.indexOf("refreshToken") >= 0) {
			canIgnore = true;
		}
		if (header != null && header.indexOf("Bearer") >= 0 && !canIgnore) { // header and not ignore
			jwtToken = header.substring(7);
			String userName = null;
			try {
				userName = jwtUtil.getUserIdFromToken(jwtToken);
				jwtUtil.verifyJWTToken(jwtToken, userName); // verify token
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userName, null, new ArrayList());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} catch (Exception e) {
				isInvalidToken = true;
				// throw new TokenExpiryException("Invalid Token","Token expired",e);
				httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
				httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
				httpServletResponse.sendError(HttpStatus.FORBIDDEN.value(), "Invalid Token");
			}
		}
		if (!isInvalidToken)
			filterChain.doFilter(httpServletRequest, httpServletResponse); // go next filter
	}
}
