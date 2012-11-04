import static java.lang.String.*;
import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

public class GildedRoseWebServerTest {

	private static final String ITEMS_AS_JSON = "[{\"name\":\"Any item\",\"sellIn\":7,\"quality\":13}]";

	private static final int DEFAULT_PORT = 8080;

	private static final Item ITEM = new Item("Any item", 7, 13);

	private static GildedRoseWebServer server;
	private static int port;

	private static ExecutorService executor = Executors.newSingleThreadExecutor();

	private static Provider provider = mock(Provider.class);
	private static Store store = mock(Store.class);

	@BeforeClass
	public static void setUp() throws Exception {
		port = findPort();
		server = new GildedRoseWebServer(provider, port);

		startOnConcurrentThread(server);

		supplyStoreWith(ITEM);

		Thread.sleep(500);
	}

	private static int findPort() {
		return findPortFromEnvironment().or(DEFAULT_PORT);
	}

	private static Optional<Integer> findPortFromEnvironment() {
		try {
			Integer environmentPort = Integer.valueOf(System.getenv("PORT"));
			return Optional.fromNullable(environmentPort);
		} catch (Exception e) {
			return Optional.absent();
		}
	}

	private static void startOnConcurrentThread(final GildedRoseWebServer server) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				server.start();
			}
		});
	}

	private static void supplyStoreWith(Item item) {
		when(provider.provideStore()).thenReturn(store);
		when(store.getItems()).thenReturn(Lists.newArrayList(item));
	}

	@Test
	public void serverSayHello() throws Exception {
		// when
		HttpMethod method = call("/hello");

		// then
		assertThat(method.getResponseBodyAsString()).startsWith("Hello");
	}

	@Test
	public void serverProvideItems() throws Exception {
		// when
		HttpMethod method = call("/items");

		// then
		assertJsonResponseHeaders(method);
		assertJsonResponseBody(method, ITEMS_AS_JSON);
	}

	@Test
	public void serverProvideItemsWithCallbackPadding() throws Exception {
		// when
		HttpMethod method = call("/items?callback=callbackName");

		// then
		assertJsonResponseHeaders(method);
		assertJsonResponseBody(method, format("callbackName(%s)", ITEMS_AS_JSON));
	}

	private void assertJsonResponseHeaders(HttpMethod method) {
		Header contentType = method.getResponseHeader("Content-Type");
		assertThat(contentType.getValue()).startsWith("application/json");
	}

	private void assertJsonResponseBody(HttpMethod method, String expected) throws IOException {
		String json = method.getResponseBodyAsString();
		assertThat(json).isEqualTo(expected);
	}

	private HttpMethod call(String context) throws IOException, HttpException {
		String adress = format("http://localhost:%d%s", port, context);

		HttpMethod method = new GetMethod(adress);
		new HttpClient().executeMethod(method);

		return method;
	}
}
