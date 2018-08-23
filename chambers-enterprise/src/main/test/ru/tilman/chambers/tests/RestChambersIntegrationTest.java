package ru.tilman.chambers.tests;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.tilman.chambers.enterprise.ChamberAnalysisApplication;
import ru.tilman.chambers.enterprise.entity.Chamber;
import ru.tilman.chambers.enterprise.services.ChamberService;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChamberAnalysisApplication.class, webEnvironment = RANDOM_PORT)
public class RestChambersIntegrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ChamberService chamberService;

    @Test
    public void getChamberTest() {
        ResponseEntity<Chamber> chamberResponseEntity = testRestTemplate.getForEntity("/rest/getChamber?id=5", Chamber.class, "1");
        assertSame(chamberResponseEntity.getStatusCode(), HttpStatus.OK);
        Chamber serviceUser = chamberService.getChamberById(5L);
        assertEquals(serviceUser, chamberResponseEntity.getBody());
    }

}
