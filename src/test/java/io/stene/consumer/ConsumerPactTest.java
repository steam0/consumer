package io.stene.consumer;

import au.com.dius.pact.consumer.ConsumerPactBuilder;
import au.com.dius.pact.consumer.PactVerificationResult;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.model.MockProviderConfig;
import au.com.dius.pact.model.RequestResponsePact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.stene.consumer.client.NationalRegistryClient;
import io.stene.consumer.client.NationalRegistryConfig;
import io.stene.consumer.client.Person;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static au.com.dius.pact.consumer.ConsumerPactRunnerKt.runConsumerTest;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ConsumerPactTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void createPerson() throws JsonProcessingException {
        // Create a Person object and give it a name and a social security number
        Person person


        // Create a "Create person" pact using the ConsumerPactBuilder
        // A pact should at least have:
        // A consumer - consumer
        // A provider to have a pact with - hasPactWith
        // A description - uponReceiving
            // A path - path
            // A http method for the request - method
            // A body - body (Hint: use the objectMapper to write the person object as a String)
        // A response behaviour - willRespondWith
            // A response status - status
            // A body - body
                // A JSON Body - new PactDslJsonBody
        // A toPact statement - toPact
        RequestResponsePact pact = ConsumerPactBuilder


        MockProviderConfig config = MockProviderConfig.createDefault();

        PactVerificationResult result = runConsumerTest(pact, config, (mockServer, context) -> {
            // Create a mock client by using the url from the mock server
            // Call the endpoint that fit the pact you want to create
        });

        assertEquals(PactVerificationResult.Ok.INSTANCE, result);
    }
}
