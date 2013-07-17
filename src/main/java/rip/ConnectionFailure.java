package rip;

import java.io.IOException;

public class ConnectionFailure extends RuntimeException {
    public ConnectionFailure(IOException cause) {
        super(cause);
    }
}
