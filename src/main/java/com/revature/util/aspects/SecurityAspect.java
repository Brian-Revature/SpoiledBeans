package com.revature.util.aspects;

import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.AuthorizationException;
import com.revature.services.AuthService;
import com.revature.web.annotations.Secured;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * This aspect secures endpoints within our controllers with a specific annotation
 */
@Aspect
@Component
public class SecurityAspect {

    private final HttpServletRequest request;
    private final AuthService authService;

    /**
     * Constructor that SpringBoot handles with auto wiring
     * @param request the httpServletRequest from the user
     * @param authService the auth service
     */
    @Autowired
    public SecurityAspect(final HttpServletRequest request,final AuthService authService){
        this.request = request;
        this.authService = authService;
    }

    /**
     * Advice that is injected on any method with the secure annotation
     * @param pjp the proceeding join point, which is the annotated method
     * @return the annotated method
     * @throws Throwable an error
     */
    @Around("@annotation(com.revature.web.annotations.Secured)")
    public Object secureEndpoint(final ProceedingJoinPoint pjp) throws Throwable {
        final Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        final Secured securedAnno = method.getAnnotation(Secured.class);

        final List<String> allowedRoles = Arrays.asList(securedAnno.allowedRoles());

        final String token = request.getHeader("spoiledBeans-token");

        if(token.trim().equals("")){
            throw new AuthenticationException("You are not an authenticated account");
        }

        final String authority = authService.getAuthorities(token);

        if(!allowedRoles.contains(authority)){
            throw new AuthorizationException();
        }

        return pjp.proceed();
    }
}
