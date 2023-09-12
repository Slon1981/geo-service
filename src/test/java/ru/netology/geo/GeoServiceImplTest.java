package ru.netology.geo;

import org.junit.Assert;
import org.junit.Test;
import ru.netology.entity.Country;

public class GeoServiceImplTest {

    @Test
    public void geoTest() {
        Assert.assertEquals(Country.RUSSIA,new GeoServiceImpl().byIp("172.1.1.1").getCountry());
    }

}
