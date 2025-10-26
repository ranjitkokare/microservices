package com.eazybytes.message.functions;

import com.eazybytes.message.dto.AccountsMsgDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    private static final Logger log = LoggerFactory.getLogger(MessageFunctions.class);

    // <input data type, output data type>
    @Bean
    public Function<AccountsMsgDto, AccountsMsgDto> email(){
        return accountsMsgDto -> {
            log.info("Sending email with the details:"+ accountsMsgDto.toString());
            return accountsMsgDto;
        };
    }

    // here we pass AccountsMsgDto as input which was output of email function
    @Bean
    public Function<AccountsMsgDto, Long> sms(){
        return accountsMsgDto -> {
            log.info("Sending SMS with the details:"+ accountsMsgDto.toString());
            // AccountsMsgDto is not class, it is record we have getter method as accountNumber()
            return accountsMsgDto.accountNumber();
            // here we return account number because once sending communication details is completed,
            // accounts MS will let know this processing is completed
            // that's why we are sending message to account MS as response asynchronously as account number
            // as input message value.
            // using this account number accounts MS can fetch existing record from DB
            // we can add new one column in accounts table to update communication details successfully or not
        };
    }
}
