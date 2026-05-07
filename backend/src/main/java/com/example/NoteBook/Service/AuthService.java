package com.example.NoteBook.Service;

import java.util.List;

import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class AuthService {
    // Method to establish a session for the authenticated user
    public void establishSession(String email, HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = new UsernamePasswordAuthenticationToken(
                email, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContext ctx = SecurityContextHolder.createEmptyContext();
        ctx.setAuthentication(auth);
        SecurityContextHolder.setContext(ctx);

        new HttpSessionSecurityContextRepository().saveContext(ctx, request, response);
        request.getSession(true);
        // print to console for debugging
        System.out.println("#########################################################");
        System.out.println("Session established for user: " + email);
    }

    // Method to close the session (logout)
    public void closeSession(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate the session and clear the security context
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        // clear the cookie as well by setting the max age to 0
        ResponseCookie cookie = ResponseCookie.from("JSESSIONID", "")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .build();
        response.addHeader("Set-Cookie", cookie.toString());

        System.out.println("Session closed and user logged out");
    }
}