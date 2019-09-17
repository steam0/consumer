package io.stene.consumer.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Component
public class NationalRegistryClient {

    private NationalRegistryConfig nationalRegistryConfig;

    @Autowired
    public NationalRegistryClient(NationalRegistryConfig nationalRegistryConfig) {
        this.nationalRegistryConfig = nationalRegistryConfig;
    }


    public Person createPerson(Person person) {
        RestTemplate restTemplate = new RestTemplate();
        String url = nationalRegistryConfig.getUrl()+"/v1/person";
        Person response = restTemplate.postForObject(url, person, Person.class);
        log.info("Created new Person(id={}, name={}, ssn={})", response.getId(), response.getName(), response.getSsn());
        return response;
    }

    public Person getPersonBySsn(String ssn) {
        RestTemplate restTemplate = new RestTemplate();
        String url = nationalRegistryConfig.getUrl()+"/v1/person?ssn="+ssn;
        Person response = restTemplate.getForObject(url, Person.class);
        log.info("Fetched Person(id={}, name={}, ssn={})", response.getId(), response.getName(), response.getSsn());
        return response;
    }

    public Person getPersonById(String id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = nationalRegistryConfig.getUrl()+"/v1/person?id="+id;
        Person response = restTemplate.getForObject(url, Person.class);
        log.info("Fetched Person(id={}, name={}, ssn={})", response.getId(), response.getName(), response.getSsn());
        return response;
    }
}
