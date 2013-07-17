package rip.url;

import java.net.MalformedURLException;

public class UrlCreator {

    public Url create(String url) throws MalformedURLException {
        return new Url(url);
    }

    public Url create(Url baseUrl, String relativePath) throws MalformedURLException {
        return new Url(baseUrl, relativePath);
    }
}
