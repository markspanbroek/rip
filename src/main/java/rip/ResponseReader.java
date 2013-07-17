package rip;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Scanner;

class ResponseReader {

    String read(URLConnection connection) {
        try (InputStream stream = connection.getInputStream()) {
            return read(stream);
        } catch (IOException exception) {
            throw new ConnectionFailure(exception);
        }
    }

    String read(InputStream stream) {
        Scanner s = new Scanner(stream).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        IOException exception = s.ioException();
        if (exception != null) {
            throw new ConnectionFailure(exception);
        }
        return result;
    }
}
