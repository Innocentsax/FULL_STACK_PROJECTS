package com.decagon.adire.security.jwt;
import com.decagon.adire.exception.CustomException;
import com.decagon.adire.exception.UnAuthorizedException;
import com.decagon.adire.utils.SecurityConstants;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {


//    @SneakyThrows
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, CustomException {
//        if (request.getServletPath().equals("/auth/signup")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String authorizationHeader = request.getHeader(AUTHORIZATION);
//        if (authorizationHeader == null) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        if (authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
//            String token = authorizationHeader.substring(SecurityConstants.TOKEN_PREFIX.length());
//            try {
//                UsernamePasswordAuthenticationToken authenticationToken = TokenProvider.validateTokenAndGetJws(token);
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                filterChain.doFilter(request, response);
//                return;
//            } catch (UnAuthorizedException e) {
//                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
//                return;
//            }
//        }
//
//        if (authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
//            String token = authorizationHeader.substring(SecurityConstants.TOKEN_PREFIX.length());
//            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
//                    .setAudience(Collections.singletonList(SecurityConstants.clientId))
//                    .build();
//            GoogleIdToken idToken = verifier.verify(token);
//            if (idToken != null) {
//                GoogleIdToken.Payload payload = idToken.getPayload();
//                String email = payload.getEmail();
//
//                UserDetails userDetails = new User(email, "", Collections.emptyList());
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                filterChain.doFilter(request, response);
//                return;
//            }
//        }
//
//        sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
//    }
//
//    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
//        response.sendError(statusCode, message);
//        response.setHeader("error", message);
//        response.setContentType(APPLICATION_JSON_VALUE);
//    }






//    @SneakyThrows
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, CustomException {
//
//        String requestPathUrl = request.getRequestURL().toString();
//
//        log.info("endpoint request was sent to is : {}",requestPathUrl);
//
//        if (request.getServletPath().equals("/auth/signup")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String authorizationHeader = request.getHeader(AUTHORIZATION);
//        if (authorizationHeader == null) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        if (authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
//            String token = authorizationHeader.substring(SecurityConstants.TOKEN_PREFIX.length());
//            try {
//                UsernamePasswordAuthenticationToken authenticationToken = TokenProvider.validateTokenAndGetJws(token);
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                filterChain.doFilter(request, response);
//                return;
//            } catch (UnAuthorizedException e) {
//                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
//                return;
//            }
//        } else if (authorizationHeader.startsWith(SecurityConstants.JWT_TOKEN_PREFIX)) {
//            String token = authorizationHeader.substring(SecurityConstants.JWT_TOKEN_PREFIX.length());
//            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
//                    .setAudience(Collections.singletonList(SecurityConstants.clientId))
//                    .build();
//            GoogleIdToken idToken = verifier.verify(token);
//            if (idToken != null) {
//                GoogleIdToken.Payload payload = idToken.getPayload();
//                String email = payload.getEmail();
//                UserDetails userDetails = new User(email, "", Collections.emptyList());
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                filterChain.doFilter(request, response);
//                return;
//            }
//        }
//
//        sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
//    }
//
//    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
//        response.sendError(statusCode, message);
//        response.setHeader("error", message);
//        response.setContentType(APPLICATION_JSON_VALUE);
//    }



























//
    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, CustomException {
        String requestPathUrl = request.getRequestURL().toString();
        log.info("endpoint request was sent to is : {}",requestPathUrl);
        if (request.getServletPath().equals("/auth/signup") ) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            log.info(authorizationHeader);
            if (authorizationHeader != null && authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
                try {
                    String token = authorizationHeader.substring(SecurityConstants.TOKEN_PREFIX.length());
                    UsernamePasswordAuthenticationToken authenticationToken = TokenProvider.validateTokenAndGetJws(token);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (UnAuthorizedException exception) {
                    exception.printStackTrace();
                    log.error("Error occurred {}", exception.getMessage());
                    response.setHeader("error", exception.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                } catch (Exception exception) {
                    exception.printStackTrace();
                    log.error("Error occurred {}", exception.getMessage());
                    response.setHeader("error", exception.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "An error occurred while processing your request");
                }

            } else {
                filterChain.doFilter(request, response);
            }
        }
    }


}
