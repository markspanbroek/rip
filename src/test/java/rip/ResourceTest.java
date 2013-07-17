package rip;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ RestClient.class, Resource.class, FakeWebsite.class, Scanner.class })
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

    @Test
    public void handlesRelativePaths() {
        fake = new FakeWebsite(BASE_URL, "bar");
        fake.setContents("baz");

        assertEquals("baz", new RestClient().open(BASE_URL).path("bar").get());
    }

    @Test(expected = MalformedUrl.class)
    public void throwsOnInvalidRelativePath() throws Exception {
        whenNew(URL.class)
                .withArguments(any(URL.class), any(String.class))
                .thenThrow(new MalformedURLException());

        new RestClient().open(BASE_URL).path("foo");
    }
}
