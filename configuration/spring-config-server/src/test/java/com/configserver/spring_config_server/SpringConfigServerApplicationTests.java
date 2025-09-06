package com.configserver.spring_config_server;

/*
  Test stack: JUnit 5 (Jupiter) + Spring Boot Test (spring-boot-starter-test).
  Notes:
  - We purposefully use JUnit assertions to avoid new dependencies.
  - Optional Actuator checks are performed via reflection and skipped if Actuator is absent.
  - Focus is on validating the public surface of the Spring Config Server bootstrapping.
*/

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assumptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SpringConfigServerApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @DisplayName("Context loads and ApplicationContext is initialized")
    void contextLoads() {
        assertNotNull(applicationContext, "ApplicationContext should be initialized");
    }

    @Test
    @DisplayName("Main application class is registered as a bean")
    void applicationClassBeanPresent() {
        // The main @SpringBootApplication class should be a configuration bean in the context
        assertNotNull(applicationContext.getBean(SpringConfigServerApplication.class),
                "SpringConfigServerApplication bean should be present in the context");
    }

    @Test
    @DisplayName("@EnableConfigServer is present on the main application class")
    void enableConfigServerAnnotationPresent() throws Exception {
        Class<?> appClass = SpringConfigServerApplication.class;

        // Resolve the annotation type via reflection to avoid compile issues if dependency is misconfigured
        Class<? extends Annotation> enableConfigServerClass;
        try {
            @SuppressWarnings("unchecked")
            Class<? extends Annotation> tmp =
                    (Class<? extends Annotation>) Class.forName("org.springframework.cloud.config.server.EnableConfigServer");
            enableConfigServerClass = tmp;
        } catch (ClassNotFoundException e) {
            fail("EnableConfigServer annotation not found on classpath; ensure spring-cloud-config-server dependency is present.", e);
            return;
        }

        Annotation anno = AnnotationUtils.findAnnotation(appClass, enableConfigServerClass);
        assertNotNull(anno, "EnableConfigServer annotation must be present on the main application class");
    }

    @Test
    @DisplayName("EnvironmentRepository bean is available (Config Server backend present)")
    void environmentRepositoryBeanIsAvailable() throws Exception {
        Class<?> envRepoClass;
        try {
            envRepoClass = Class.forName("org.springframework.cloud.config.server.environment.EnvironmentRepository");
        } catch (ClassNotFoundException e) {
            fail("EnvironmentRepository type not found on classpath; ensure spring-cloud-config-server dependency is present.", e);
            return;
        }

        @SuppressWarnings({"rawtypes","unchecked"})
        Map<String, ?> beans = applicationContext.getBeansOfType((Class) envRepoClass);
        assertFalse(beans.isEmpty(), "At least one EnvironmentRepository bean should be present");
    }

    @Test
    @DisplayName("Health endpoint reports UP when Actuator is present")
    void healthEndpointUpWhenActuatorPresent() throws Exception {
        // Skip this test if Actuator is not on the classpath
        final Class<?> healthEndpointClass;
        try {
            healthEndpointClass = Class.forName("org.springframework.boot.actuate.health.HealthEndpoint");
        } catch (ClassNotFoundException e) {
            Assumptions.assumeTrue(false, "Actuator not on classpath; skipping health endpoint test");
            return;
        }

        Object healthEndpointBean = applicationContext.getBean(healthEndpointClass);
        Method healthMethod = healthEndpointClass.getMethod("health");
        Object health = healthMethod.invoke(healthEndpointBean);

        // Reflectively access Status and assert it's UP
        Method getStatus = health.getClass().getMethod("getStatus");
        Object status = getStatus.invoke(health);
        assertEquals("UP", status.toString(), "Health status should be UP when the context is healthy");
    }

    @Test
    @DisplayName("main(String[]) executes without exceptions (no web)")
    void mainRunsWithoutExceptions() {
        assertDoesNotThrow(() ->
                SpringConfigServerApplication.main(new String[] {
                        "--spring.main.web-application-type=none",
                        "--spring.application.name=test-spring-config-server"
                }), "Main method should execute without throwing exceptions");
    }

    @Test
    @DisplayName("spring.application.name is defined and non-empty")
    void applicationNamePropertyDefined() {
        String name = applicationContext.getEnvironment().getProperty("spring.application.name");
        assertNotNull(name, "spring.application.name should be defined");
        assertFalse(name.trim().isEmpty(), "spring.application.name should not be empty");
    }
}
