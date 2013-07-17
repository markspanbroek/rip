package rip;

import java.net.MalformedURLException;

public class MalformedUrl extends RuntimeException {
    MalformedUrl(MalformedURLException cause) {
        super(cause);
    }
}
