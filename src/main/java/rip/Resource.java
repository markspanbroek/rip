package rip;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

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

    public void put(String contents) {
        HttpURLConnection connection = openConnection();
        setRequestMethod(connection, "PUT");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        send(connection, contents);
        reader.read(connection);
    }

    private URL createUrl(String relativePath) {
        try {
            return new URL(url, relativePath);
        } catch (MalformedURLException exception) {
            throw new InvalidUrl(exception);
        }
    }

    private HttpURLConnection openConnection() {
        try {
            return (HttpURLConnection) url.openConnection();
        } catch (IOException exception) {
            throw new ConnectionFailure(exception);
        }
    }

    private void setRequestMethod(HttpURLConnection connection, String method) {
        try {
            connection.setRequestMethod(method);
        } catch (ProtocolException shouldNotHappen) {
            throw new Error(shouldNotHappen);
        }
    }

    private void send(HttpURLConnection connection, String contents) {
        try (OutputStream out = connection.getOutputStream()) {
            out.write(contents.getBytes());
        } catch (IOException exception) {
            throw new ConnectionFailure(exception);
        }
    }
}
