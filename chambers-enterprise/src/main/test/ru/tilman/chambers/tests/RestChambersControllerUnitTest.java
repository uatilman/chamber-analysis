package ru.tilman.chambers.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.tilman.chambers.enterprise.controllers.RestChambersController;
import ru.tilman.chambers.enterprise.entity.Chamber;
import ru.tilman.chambers.enterprise.services.ChamberFeignService;
import ru.tilman.chambers.enterprise.services.ChamberService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.tilman.chambers.enterprise.controllers.RestChambersController.ASC;
import static ru.tilman.chambers.enterprise.controllers.RestChambersController.ID;

public class RestChambersControllerUnitTest {

    private Chamber mockChamber = new Chamber();
    private List<Chamber> mockChambers;
    private RestChambersController restChambersController;

    @BeforeTest
    public void init() {
        mockChambers = new ArrayList<>(Arrays.asList(
                new Chamber(),
                new Chamber(),
                new Chamber(),
                new Chamber(),
                new Chamber()
        ));
        mockChambers.forEach(chamber -> chamber.setId(new Random().nextLong()));

        ChamberService chamberService = mock(ChamberService.class);
        ChamberFeignService chamberFeignService = mock(ChamberFeignService.class);

        when(chamberService.getChamberById(mockChamber.getId())).thenReturn(mockChamber);
        when(chamberFeignService.getChambers()).thenReturn(mockChambers);

        restChambersController = new RestChambersController(chamberFeignService, chamberService);
    }

    @Test
    public void getChamberMethodTest() {
        Chamber chamber = restChambersController.getChamber(this.mockChamber.getId());
        Assert.assertNotNull(chamber);
        Assert.assertEquals(this.mockChamber.getId(), chamber.getId());
    }

    @Test
    public void getChambersMethodTest() {
        List<Chamber> chambers = restChambersController.getChambersList(null, mockChambers.size(), ASC, ID, null);

        Assert.assertNotNull(chambers);
        Assert.assertEquals(mockChambers.size(), chambers.size());

        mockChambers.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));
        Assert.assertEquals(mockChambers, chambers);

    }
}
