package ru.netology.sender;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MessageSenderImplTest {

    GeoService geoService;
    LocalizationService localizationService;
    MessageSender sender;



    @BeforeAll
    public void setup() {
        geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(ArgumentMatchers.startsWith("172."))).thenReturn(new Location("Sankt Peterburg", Country.RUSSIA, "Nevskiy", 1));
        Mockito.when(geoService.byIp(not(ArgumentMatchers.startsWith("172.")))).thenReturn(new Location("New York", Country.USA, "Wall Street", 1));


        localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");

        sender = new MessageSenderImpl(geoService, localizationService);
    }


    @Test
    public void testRussianMessage() {


        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19");
        String response = sender.send(headers);

        assertEquals("Добро пожаловать", response);


        verify(localizationService, times(2)).locale(Country.RUSSIA);
        verify(geoService).byIp("172.123.12.19");

    }


    @Test
    public void testEnglishMessage() {

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "111.123.12.19");
        String response = sender.send(headers);

        assertEquals("Welcome", response);

        verify(localizationService, times(2)).locale(Country.USA);
        verify(geoService).byIp("111.123.12.19");

    }
}