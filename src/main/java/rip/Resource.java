package rip;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import static rip.RestClient.MalformedUrl;

public class Resource {

    private URL url;

    Resource(URL url) {
        this.url = url;
    }

    public Resource path(String relativePath) {
        try {
            return new Resource(createUrl(relativePath));
        } catch (MalformedURLException exception) {
            throw new MalformedUrl(exception);
        }
    }

    private URL createUrl(String relativePath) throws MalformedURLException {
        return new URL(url, relativePath);
    }

    public String get(){
        try {
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Accept", "application/json");

            return read(connection.getInputStream());
        } catch (IOException exception) {
            throw new ConnectionFailure(exception);
        }
    }

    private String read(InputStream stream) {
        Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        IOException exception = s.ioException();
        if (exception != null) {
            throw new ConnectionFailure(exception);
        }
        return result;
    }

    public class ConnectionFailure extends RuntimeException {
        public ConnectionFailure(IOException cause) {
            super(cause);
        }
    }
}
