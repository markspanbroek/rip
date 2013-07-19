package rip;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;

class RequestWriter {

    private ResponseReader reader = new ResponseReader();

    void write(URLConnection connection, String contents) {
        connection.setDoOutput(true);
        try (OutputStream out = connection.getOutputStream()) {
            out.write(contents.getBytes());
        } catch (IOException exception) {
            throw new ConnectionFailure(exception);
        }
        reader.read(connection);
    }

}
