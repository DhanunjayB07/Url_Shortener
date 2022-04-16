package com.urlshortener.service;

import java.nio.charset.StandardCharsets;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.hash.Hashing;
import com.urlshortener.model.Url;
import com.urlshortener.model.UrlDto;
import com.urlshortener.repository.UrlRepository;
import com.urlshortener.service.UrlServiceImpl;

@Component
public class UrlServiceImpl implements UrlService {

    
    @Autowired
    private UrlRepository urlRepository;

    @Override
    public Url generateShortUrl(UrlDto urlDto) {

        if(StringUtils.isNotEmpty(urlDto.getUrl()))
        {
            String encodedUrl = encodeUrl(urlDto.getUrl());
            Url urlToPersist = new Url();
            urlToPersist.setOriginalUrl(urlDto.getUrl());
            urlToPersist.setShortUrl(encodedUrl);
            
            Url urlToRet = persistShortUrl(urlToPersist);

            if(urlToRet != null)
                return urlToRet;

            return null;
        }
        return null;
    }

    

    private String encodeUrl(String url)
    {
        String encodedUrl = "";
        LocalDateTime time = LocalDateTime.now();
        encodedUrl = Hashing.murmur3_32()
                .hashString(url.concat(time.toString()), StandardCharsets.UTF_8)
                .toString();
        return  encodedUrl;
    }

    @Override
    public Url persistShortUrl(Url url) {
        Url urlToRet = urlRepository.save(url);
        return urlToRet;
    }

    @Override
    public Url getEncodedUrl(String url) {
        Url urlToRet = urlRepository.findByShortUrl(url);
        return urlToRet;
    }
}
