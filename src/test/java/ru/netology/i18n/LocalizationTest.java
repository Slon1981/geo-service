package ru.netology.i18n;

import org.junit.Assert;
import org.junit.Test;
import ru.netology.entity.Country;

public class LocalizationTest {

    @Test
    public void locTest() {
        Assert.assertEquals("Добро пожаловать!", new LocalizationServiceImpl().locale(Country.RUSSIA));
    }
}
