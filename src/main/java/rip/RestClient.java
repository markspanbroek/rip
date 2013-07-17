package rip;

import rip.url.Url;
import rip.url.UrlCreator;

import java.net.MalformedURLException;

public class RestClient {

    UrlCreator urlCreator = new UrlCreator();

    public Resource open(String url) {
        return new Resource(createUrl(url), urlCreator);
    }

    private Url createUrl(String url) {
        try {
            return urlCreator.create(url);
        } catch (MalformedURLException exception) {
            throw new MalformedUrl(exception);
        }
    }
}
