package rip;

import java.io.IOException;

public class ConnectionFailure extends RuntimeException {
    ConnectionFailure(IOException cause) {
        super(cause);
    }
}
