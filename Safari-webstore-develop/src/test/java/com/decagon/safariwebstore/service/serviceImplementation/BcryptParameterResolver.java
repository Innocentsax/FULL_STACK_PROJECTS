package com.decagon.safariwebstore.service.serviceImplementation;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptParameterResolver implements ParameterResolver {

        @Override
        public boolean supportsParameter(ParameterContext parameterContext,
                                         ExtensionContext extensionContext) throws ParameterResolutionException {
            return parameterContext.getParameter().getType() == BCryptPasswordEncoder.class;
        }

        @Override
        public Object resolveParameter(ParameterContext parameterContext,
                                       ExtensionContext extensionContext) throws ParameterResolutionException {
            return new BCryptPasswordEncoder();
        }

}
