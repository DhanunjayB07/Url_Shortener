package com.urlshortener.service;

import org.springframework.stereotype.Service;

import com.urlshortener.model.Url;
import com.urlshortener.model.UrlDto;

@Service
public interface UrlService
{
    public Url generateShortUrl(UrlDto urlDto);
    public Url persistShortUrl(Url url);
    public Url getEncodedUrl(String url);
}
