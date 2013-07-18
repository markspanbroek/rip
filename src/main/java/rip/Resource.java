package rip;

import rip.url.Url;
import rip.url.UrlCreator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLConnection;

public class Resource {

    private Url url;
    private UrlCreator urlCreator;
    private ResponseReader reader = new ResponseReader();

    Resource(Url url, UrlCreator urlCreator) {
        this.url = url;
        this.urlCreator = urlCreator;
    }

    public Resource path(String relativePath) {
        return new Resource(createUrl(relativePath), urlCreator);
    }

    public String get(){
        URLConnection connection = openConnection();
        connection.setRequestProperty("Accept", "application/json");
        return reader.read(connection);
    }

    private Url createUrl(String relativePath) {
        try {
            return urlCreator.create(url, relativePath);
        } catch (MalformedURLException exception) {
            throw new InvalidUrl(exception);
        }
    }

    private URLConnection openConnection() {
        try {
            return url.openConnection();
        } catch (IOException exception) {
            throw new ConnectionFailure(exception);
        }
    }
}
