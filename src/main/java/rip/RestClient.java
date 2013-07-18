package rip;

import rip.url.Url;
import rip.url.UrlCreator;

import java.net.MalformedURLException;
import java.util.Arrays;

public class RestClient {

    UrlCreator urlCreator = new UrlCreator();

    public Resource open(String url) {
        return new Resource(createUrl(url), urlCreator);
    }

    private Url createUrl(String url) {
        try {
            return validateUrl(urlCreator.create(url));
        } catch (MalformedURLException exception) {
            throw new InvalidUrl(exception);
        }
    }

    private Url validateUrl(Url url) {
        if (!Arrays.asList("http", "https").contains(url.getProtocol())) {
            throw new InvalidUrl("only http(s) urls are supported");
        }
        return url;
    }
}
