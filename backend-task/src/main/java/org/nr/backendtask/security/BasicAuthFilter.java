package org.nr.backendtask.security;

import java.lang.annotation.Annotation;
import java.util.Base64;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.nr.backendtask.Constants;
import org.nr.backendtask.api.exceptions.UnAuthorizedException;
import org.nr.backendtask.model.ApplicationUser;
import org.nr.backendtask.repository.ApplicationUserRepository;
import org.springdoc.webmvc.api.OpenApiResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class BasicAuthFilter extends HandlerInterceptorAdapter {


    private final BCryptPasswordEncoder passwordEncoder;
    private final ApplicationUserRepository applicationUserRepository;

    @Autowired
    public BasicAuthFilter(BCryptPasswordEncoder passwordEncoder, ApplicationUserRepository applicationUserRepository) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (((HandlerMethod) handler).getBeanType() == OpenApiResource.class) {
                return super.preHandle(request, response, handler);
            }
            if (shouldSecure(handlerMethod, IgnoreAuth.class)) {
                String header = request.getHeader(Constants.AUTHORIZATION_HEADER);
                String headerValue = parseAuthorizationHeader(header);
                ApplicationUser applicationUser = findUserByHeaderValue(headerValue);
                request.setAttribute(Constants.APPLICATION_USER_ATTRIBUTE, applicationUser);
            } else {
                try {
                    String header = request.getHeader(Constants.AUTHORIZATION_HEADER);
                    String headerValue = parseAuthorizationHeader(header);
                    ApplicationUser applicationUser = findUserByHeaderValue(headerValue);
                    request.setAttribute(Constants.APPLICATION_USER_ATTRIBUTE, applicationUser);
                } catch (Exception exception) {
                    return super.preHandle(request, response, handler);
                }
            }


            return super.preHandle(request, response, handler);
        }
        return super.preHandle(request, response, handler);
    }


    private boolean shouldSecure(HandlerMethod handlerMethod, Class annotationClass) {
        Annotation methodPresent = handlerMethod.getMethodAnnotation(annotationClass);
        Annotation forRest = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RestController.class);
        return forRest != null && methodPresent == null;
    }

    private ApplicationUser findUserByHeaderValue(String encodedValue) throws UnAuthorizedException {
        try {
            byte[] decoded = Base64.getDecoder().decode(encodedValue);
            String[] decodedValue = new String(decoded).split(":");
            if (decodedValue.length != 2) {
                throw new UnAuthorizedException();

            }
            String username = decodedValue[0];
            String password = decodedValue[1];


            Optional<ApplicationUser> optionalApplicationUser = applicationUserRepository.findByUsername(username);
            if (optionalApplicationUser.isPresent()) {
                boolean passwordMatch = passwordEncoder.matches(password, optionalApplicationUser.get().getPassword());
                if (passwordMatch) {
                    return optionalApplicationUser.get();
                } else {
                    throw new UnAuthorizedException();
                }

            } else {
                throw new UnAuthorizedException();
            }
        } catch (Exception ex) {
            throw new UnAuthorizedException();
        }


    }

    private String parseAuthorizationHeader(String header) throws Exception {
        if (header != null) {
            String[] spliced = header.split(" ");
            if (spliced.length != 2 || !spliced[0].equals("Basic")) {
                throw new UnAuthorizedException();
            }
            return spliced[1];
        }
        throw new UnAuthorizedException();

    }
}
