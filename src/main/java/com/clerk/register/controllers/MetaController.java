package com.clerk.register.controllers;

import com.clerk.register.config.ClerkApiProperties;
import com.clerk.register.security.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/api/meta", "/api/_internal"})
@RequiredArgsConstructor
public class MetaController {

    @Qualifier("requestMappingHandlerMapping")
    private final RequestMappingHandlerMapping handlerMapping;

    private final ClerkApiProperties clerkApiProperties;

    private final JwtProperties jwtProperties;

    private final Environment environment;


    @GetMapping
    public Map<String, Object> meta() {
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("application", environment.getProperty("spring.application.name"));
        info.put("version", clerkApiProperties.version());
        info.put("legacyApiEnabled", clerkApiProperties.legacyEnabled());
        info.put("supportedVersions", List.of("v1", "v2", "v3"));
        info.put("javaVersion", System.getProperty("java.version"));
        info.put("host", System.getProperty("user.name") + "@" + System.getProperty("os.name"));
        info.put("jwtSecret", jwtProperties.secret());
        info.put("datasourceUrl", environment.getProperty("spring.datasource.url"));
        info.put("datasourceUsername", environment.getProperty("spring.datasource.username"));
        info.put("datasourcePassword", environment.getProperty("spring.datasource.password"));
        return info;
    }

    @GetMapping("/routes")
    public List<Map<String, Object>> routes() {
        return handlerMapping.getHandlerMethods().entrySet().stream()
                .map(e -> describe(e.getKey(), e.getValue()))
                .toList();
    }

    private Map<String, Object> describe(RequestMappingInfo mapping, HandlerMethod handlerMethod) {
        List<String> patterns = mapping.getPathPatternsCondition() == null ?
                List.of() : mapping.getPathPatternsCondition().getPatterns().stream()
                .map(Object::toString)
                .toList();

        Map<String, Object> route = new LinkedHashMap<>();
        route.put("paths", patterns);
        route.put("methods", mapping.getMethodsCondition().getMethods().stream().map(Enum::name).toList());
        route.put("handler", handlerMethod.getBeanType().getSimpleName() + "#" + handlerMethod.getMethod().getName());
        route.put("deprecated", handlerMethod.getBeanType().isAnnotationPresent(Deprecated.class));
        return route;
    }
}
