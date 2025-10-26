package com.eazybytes.message.dto;

/**
 * @param accountNumber
 * @param name
 * @param email
 * @param mobileNumber
 */
// Because of record, container will automatically generate getters for fields and
// also make them final
// it means when we create object of this class, we can't change the values of fields
public record AccountsMsgDto(Long accountNumber, String name, String email, String mobileNumber) { // always mention fields inside ()

}
