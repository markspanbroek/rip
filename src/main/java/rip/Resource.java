package rip;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Resource {

    private URL url;
    private ResponseReader reader = new ResponseReader();

    Resource(URL url) {
        this.url = url;
    }

    public Resource path(String relativePath) {
        return new Resource(createUrl(relativePath));
    }

    public String get(){
            URLConnection connection = openConnection();
            connection.setRequestProperty("Accept", "application/json");
            return reader.read(connection);
    }

    private URL createUrl(String relativePath) {
        try {
            return new URL(url, relativePath);
        } catch (MalformedURLException exception) {
            throw new MalformedUrl(exception);
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
