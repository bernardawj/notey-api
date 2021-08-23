package com.bernardawj.notey.utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    private static final Log LOGGER = LogFactory.getLog(LoggingAspect.class);

    @AfterThrowing(pointcut = "execution(* com.bernardawj.notey.service.*Impl.*(..))", throwing = "exception")
    public void logExceptions(Exception exception) {
        LOGGER.error(exception.getMessage(), exception);
    }

    @AfterThrowing(pointcut = "execution(* com.bernardawj.notey.security.UserDetailsServiceImpl.*(..))", throwing = "exception")
    public void logAuthExceptions(Exception exception) {
        LOGGER.error(exception.getMessage(), exception);
    }

    @AfterThrowing(pointcut = "execution(* org.springframework.security.authentication.dao.DaoAuthenticationProvider.*(..))", throwing = "exception")
    public void logCredentialsExceptions(Exception exception) {
        LOGGER.error(exception.getMessage(), exception);
    }
}