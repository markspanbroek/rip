RIP
===

RIP is a Java REST client with an extremely simple API:

    Resource resource = new RestClient().open("http://example.com");

    // HTTP GET
    String result = resource.get();

    // HTTP POST
    resource.post("{ \"some\" : \"json\" }");

    // HTTP PUT
    resource.put("[{ \"more\" : \"json\" }]");

    // HTTP DELETE
    resource.delete();
    
    // Relative paths
    resource.path("item42").delete();


It has been designed to allow for easy mocking. The following example uses [Mockito][1] to mock RestClient:

    @Test
    public void testExample() {
        RestClient client = mock(RestClient.class);
        client.open("http://example.com");
        verify(client).open("http://example.com");
    }

REST in peace!

[1]: http://code.google.com/p/mockito/
