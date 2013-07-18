package rip;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class RestClient {

    public Resource open(String url) {
        return new Resource(validateUrl(createUrl(url)));
    }

    private URL createUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException exception) {
            throw new InvalidUrl(exception);
        }
    }

    private URL validateUrl(URL url) {
        if (!Arrays.asList("http", "https").contains(url.getProtocol())) {
            throw new InvalidUrl("only http(s) urls are supported");
        }
        return url;
    }
}
