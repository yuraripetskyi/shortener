package com.test.shortener.service;

import com.test.shortener.dao.UrlRepository;
import com.test.shortener.entity.Url;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UrlService {
    @Value("${application-host}")
    private String host;
    @Value("${server.port}")
    private String port;

    private final ConversionService conversionService;
    private final UrlRepository urlRepository;

    public String makeShortURL(String longUrl) {
        Optional<Url> byLongUrl = urlRepository.findByLongUrl(longUrl);
        Url url;
        if (byLongUrl.isEmpty()) {
            url = new Url();
            url.setLongUrl(longUrl);
            url = urlRepository.save(url);
        } else {
            url = byLongUrl.get();
        }
        String encodedUrl = conversionService.encode(url.getId());
        return this.host + ":" + this.port + "/"+encodedUrl;
    }

    @Cacheable(value = "urls", key = "#shortUrl", sync = true)
    public String getOriginalUrl(String shortUrl) {
        long id = conversionService.decode(shortUrl);
        Url entity = urlRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no entity with " + shortUrl));
        return entity.getLongUrl();
    }
}
