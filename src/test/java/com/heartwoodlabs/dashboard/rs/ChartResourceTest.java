package com.heartwoodlabs.dashboard.rs;

import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.spi.Dispatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ChartResourceTest {

	private static Dispatcher dispatcher;

	@BeforeAll
	public static void init() {
		dispatcher = MockDispatcherFactory.createDispatcher();
		dispatcher.getProviderFactory().registerProvider(JacksonConfiguration.class);
		dispatcher.getRegistry().addSingletonResource(new ChartResource(), "/api");
	}

	@Test
	public void chartTest() throws Exception {
		MockHttpRequest request = MockHttpRequest.get("/api/charts/test");
		MockHttpResponse response = new MockHttpResponse();
		dispatcher.invoke(request, response);
		Assertions.assertEquals(200, response.getStatus());
		Assertions.assertEquals("true", response.getContentAsString());
	}
}
