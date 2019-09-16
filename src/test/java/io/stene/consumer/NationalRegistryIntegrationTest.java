package io.stene.consumer;

import io.stene.consumer.client.NationalRegistryClient;
import io.stene.consumer.client.Person;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class NationalRegistryIntegrationTest {

	@Autowired
	private NationalRegistryClient nationalRegistryClient;

	@Test
	@Disabled
	void createNewPerson() {
		Person person = Person.builder().name("Harald Hårfagre").ssn("01039012345").build();

		Person response = nationalRegistryClient.createPerson(person);

		System.out.println(response);

		assertEquals(response.getName(), person.getName());
		assertEquals(response.getSsn(), person.getSsn());
		assertNotNull(response.getId());
	}
}
