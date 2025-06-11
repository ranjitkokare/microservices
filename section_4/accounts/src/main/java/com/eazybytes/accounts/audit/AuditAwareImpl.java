package com.eazybytes.accounts.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl") // bean name
public class AuditAwareImpl implements AuditorAware<String> {
//    as createdAt and updatedAt are auto populated by spring

    /**
     * Returns the current auditor of the application
     *
     * @return the current auditor
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("ACCOUNTS_MS");
    }
}
