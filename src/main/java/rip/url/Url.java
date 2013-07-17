package rip.url;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Url {

    private URL javaURL;

    Url(String url) throws MalformedURLException {
        javaURL = new URL(url);
    }

    Url(Url baseUrl, String relativePath) throws MalformedURLException {
        javaURL = new URL(baseUrl.javaURL, relativePath);
    }

    public URLConnection openConnection() throws IOException {
        return javaURL.openConnection();
    }
}
