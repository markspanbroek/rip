package rip;

import java.net.MalformedURLException;

public class InvalidUrl extends RuntimeException {
    InvalidUrl(MalformedURLException cause) {
        super(cause);
    }

    InvalidUrl(String message) {
        super(message);
    }
}
