package rip;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import rip.Resource.ConnectionFailure;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ RestClient.class, Resource.class, FakeWebsite.class })
public class ResourceTest {

    private static final String BASE_URL = "http://example.com";

    private FakeWebsite fake;

    @Before
    public void setUp() throws Exception {
        fake = new FakeWebsite(BASE_URL);
    }

    @Test
    public void retrievesResource() {
        fake.setContents("foo");
        assertEquals("foo", new RestClient().open(BASE_URL).get());
    }

    @Test
    public void sendsJsonHeader() {
        new RestClient().open(BASE_URL).get();

        verify(fake.connection).setRequestProperty("Accept", "application/json");
    }

    @Test(expected = ConnectionFailure.class)
    public void throwsOnFailureToOpenConnection() throws IOException {
        when(fake.url.openConnection()).thenThrow(new IOException());

        new RestClient().open(BASE_URL).get();
    }

    @Test(expected = ConnectionFailure.class)
    public void throwsOnFailureReadingResponse() throws IOException {
        fake.setContents(mock(InputStream.class));

        when(fake.stream.read()).thenThrow(new IOException());
        when(fake.stream.read(any(byte[].class))).thenThrow(new IOException());
        when(fake.stream.read(any(byte[].class), anyInt(), anyInt())).thenThrow(new IOException());

        new RestClient().open(BASE_URL).get();
    }
}
