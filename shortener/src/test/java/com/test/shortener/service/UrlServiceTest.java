package com.test.shortener.service;

import com.test.shortener.dao.UrlRepository;
import com.test.shortener.entity.Url;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UrlServiceTest {
    private static final String HOST = "host";
    private static final String PORT = "80";
    private static final String LONG_URL = "https://www.apple.com/iphone-12/";

    @InjectMocks
    private UrlService service;

    @Mock
    private ConversionService conversionService;
    @Mock
    private UrlRepository urlRepository;

    @Before
    public void init() {
        ReflectionTestUtils.setField(service, "host", HOST);
        ReflectionTestUtils.setField(service, "port", PORT);
    }

    @Test
    public void makeShortURLTestCaseExist() {
        long id = 1L;
        Url url = new Url(id, LONG_URL);

        Optional<Url> optionalUrl = Optional.of(url);

        final String encoded = "ee";

        when(urlRepository.findByLongUrl(eq(LONG_URL))).thenReturn(optionalUrl);
        when(conversionService.encode(eq(id))).thenReturn(encoded);

        String expected = "host:80/ee";
        String result = service.makeShortURL(LONG_URL);

        verify(urlRepository).findByLongUrl(eq(LONG_URL));
        verify(conversionService).encode(eq(id));
        verifyNoMoreInteractions(conversionService, urlRepository);
        assertEquals(expected, result);
    }

    @Test
    public void makeShortURLTestCaseDoNotExist() {
        long id = 0L;
        Url url = new Url();
        url.setLongUrl(LONG_URL);

        Optional<Url> optionalUrl = Optional.empty();

        final String encoded = "ee";

        Url newUrl = new Url(id, LONG_URL);

        when(urlRepository.findByLongUrl(eq(LONG_URL))).thenReturn(optionalUrl);
        when(urlRepository.save(eq(url))).thenReturn(newUrl);
        when(conversionService.encode(eq(id))).thenReturn(encoded);

        String expected = "host:80/ee";
        String result = service.makeShortURL(LONG_URL);

        verify(urlRepository).findByLongUrl(eq(LONG_URL));
        verify(urlRepository).save(eq(newUrl));
        verify(conversionService).encode(eq(id));
        verifyNoMoreInteractions(conversionService, urlRepository);
        assertEquals(expected, result);
    }

    @Test
    public void getOriginalUrlTest() {
        String shortUrl = "ee";
        Long id = 1L;

        Url url = new Url(id, LONG_URL);
        Optional<Url> optionalUrl = Optional.of(url);

        when(conversionService.decode(eq(shortUrl))).thenReturn(id);
        when(urlRepository.findById(eq(id))).thenReturn(optionalUrl);

        String originalUrl = service.getOriginalUrl(shortUrl);

        verify(conversionService).decode(eq(shortUrl));
        verify(urlRepository).findById(eq(id));
        verifyNoMoreInteractions(conversionService, urlRepository);
        assertEquals(LONG_URL, originalUrl);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getOriginalUrlTestCaseException() {
        String shortUrl = "ee";
        Long id = 1L;

        Throwable throwable = new EntityNotFoundException("There is no entity with " + shortUrl);
        when(conversionService.decode(eq(shortUrl))).thenReturn(id);
        when(urlRepository.findById(eq(id))).thenThrow(throwable);

        String originalUrl = service.getOriginalUrl(shortUrl);

        verify(conversionService).decode(eq(shortUrl));
        verify(urlRepository).findById(eq(id));
    }
}