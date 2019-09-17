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
        Person person = Person.builder()
                .name("John Appleseed")
                .ssn("11039012345")
                .build();

        RequestResponsePact pact = ConsumerPactBuilder
                .consumer("consumer").hasPactWith("national-registry")
                .uponReceiving("Create new person request")
                    .path("/v1/person")
                    .method("POST")
                    .body(objectMapper.writeValueAsString(person))
                .willRespondWith()
                    .status(HttpStatus.CREATED.value())
                    .body(new PactDslJsonBody()
                            .stringValue("name", person.getName())
                            .stringValue("ssn", person.getSsn())
                            .integerType("id", 0)
                    )
                .toPact();

        MockProviderConfig config = MockProviderConfig.createDefault();

        PactVerificationResult result = runConsumerTest(pact, config, (mockServer, context) -> {
            NationalRegistryClient nationalRegistryClient = new NationalRegistryClient(new NationalRegistryConfig(mockServer.getUrl()));
            nationalRegistryClient.createPerson(person);
        });

        assertEquals(PactVerificationResult.Ok.INSTANCE, result);
    }

    @Test
    public void createPersonWithDNumber() throws JsonProcessingException {
        Person person = Person.builder()
                .name("John Appleseed")
                .ssn("71039012345")
                .build();

        RequestResponsePact pact = ConsumerPactBuilder
                .consumer("consumer").hasPactWith("national-registry")
                .uponReceiving("Create new person with D number request")
                    .path("/v1/person")
                    .method("POST")
                    .body(objectMapper.writeValueAsString(person))
                .willRespondWith()
                    .status(HttpStatus.CREATED.value())
                    .body(new PactDslJsonBody()
                            .stringValue("name", person.getName())
                            .stringValue("ssn", person.getSsn())
                            .integerType("id", 0)
                    )
                .toPact();

        MockProviderConfig config = MockProviderConfig.createDefault();

        PactVerificationResult result = runConsumerTest(pact, config, (mockServer, context) -> {
            NationalRegistryClient nationalRegistryClient = new NationalRegistryClient(new NationalRegistryConfig(mockServer.getUrl()));
            nationalRegistryClient.createPerson(person);
        });

        assertEquals(PactVerificationResult.Ok.INSTANCE, result);
    }

    @Test
    public void getPersonBySsn() {
        String ssn = "71129011111";

        RequestResponsePact pact = ConsumerPactBuilder
                .consumer("consumer").hasPactWith("national-registry")
                .given("Person with ssn ("+ssn+") exist")
                .uponReceiving("Get person by SSN")
                    .path("/v1/person")
                    .method("GET")
                    .query("ssn="+ssn)
                .willRespondWith()
                    .status(HttpStatus.OK.value())
                    .body(new PactDslJsonBody()
                            .stringValue("ssn", ssn)
                            .integerType("id", 0)
                    )
                .toPact();

        MockProviderConfig config = MockProviderConfig.createDefault();

        PactVerificationResult result = runConsumerTest(pact, config, (mockServer, context) -> {
            NationalRegistryClient nationalRegistryClient = new NationalRegistryClient(new NationalRegistryConfig(mockServer.getUrl()));
            nationalRegistryClient.getPersonBySsn(ssn);
        });

        assertEquals(PactVerificationResult.Ok.INSTANCE, result);
    }

    @Test
    public void getPersonById() {
        Integer id = 100;

        RequestResponsePact pact = ConsumerPactBuilder
                .consumer("consumer").hasPactWith("national-registry")
                .given("A person exist")
                .uponReceiving("Get person by id")
                    .path("/v1/person")
                    .method("GET")
                    .queryParameterFromProviderState("id", "${id}:integer", id.toString())
                .willRespondWith()
                    .status(HttpStatus.OK.value())
                    .body(new PactDslJsonBody()
                           .stringType("ssn")
                            .valueFromProviderState("id", "${id}", id)
                    )
                .toPact();

        MockProviderConfig config = MockProviderConfig.createDefault();

        PactVerificationResult result = runConsumerTest(pact, config, (mockServer, context) -> {
            NationalRegistryClient nationalRegistryClient = new NationalRegistryClient(new NationalRegistryConfig(mockServer.getUrl()));
            nationalRegistryClient.getPersonById(id.toString());
        });

        assertEquals(PactVerificationResult.Ok.INSTANCE, result);
    }
}
