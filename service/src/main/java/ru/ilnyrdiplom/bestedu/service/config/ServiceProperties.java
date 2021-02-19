package ru.ilnyrdiplom.bestedu.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
@ConfigurationProperties("ru.ilnyr.sticker-replacer.service")
@Getter
@Setter
public class ServiceProperties {
    @Getter
    @Setter
    public static class UserProperties {
        /**
         * Personal salt size.
         */
        private int saltSize;
        /**
         * Global salt. Only ascii symbols available.
         */
        private String globalSalt;
        /**
         * Password hash algorithm. One of java.security.MessageDigest algorithms.
         */
        private String hashAlgorithm;
        /**
         * Length password during restore.
         */
        private int lengthPassword;
    }

    /**
     * Users properties.
     */
    private UserProperties user;
}
