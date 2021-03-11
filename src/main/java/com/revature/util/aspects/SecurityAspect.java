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

@Aspect
@Component
public class SecurityAspect {

    private HttpServletRequest request;
    private AuthService authService;

    @Autowired
    public SecurityAspect(HttpServletRequest request, AuthService authService){
        this.request = request;
        this.authService = authService;
    }

    @Around("@annotation(com.revature.web.annotations.Secured)")
    public Object secureEndpoint(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Secured securedAnno = method.getAnnotation(Secured.class);

        List<String> allowedRoles = Arrays.asList(securedAnno.allowedRoles());

        Cookie[] reqCookies = request.getCookies();

        if (reqCookies == null) {
            throw new AuthenticationException("An unauthenticated request was made to a protected endpoint!");
        }

        String token = Stream.of(reqCookies)
                            .filter(c -> c.getName().equals("spoiledBeans-token"))
                            .findFirst()
                            .orElseThrow(AuthenticationException::new)
                            .getValue();

        String authority = authService.getAuthorities(token);

        System.out.println(authority);

        if(!allowedRoles.contains(authority)){
            throw new AuthorizationException();
        }

        return pjp.proceed();
    }
}
