package com.eazybytes.accounts.dto;

/*
 * Record class:
 *   1. Introduced in Java 16
 *   2. Immutable i.e. once created can not be changed(final) can read only
 *   3. Can initialize only-once at runtime
 *   4. No need to write getter and setter
 *   5. No need to write constructor
 *   6. No need to write toString()
 * */

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "accounts")
public record AccountsContactInfoDto(String message, Map<String, String> contactDetails, List<String> onCallSupport) {
}
