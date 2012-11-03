import static java.lang.String.*;
import static org.fest.assertions.Assertions.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

public class GildedRoseWebServerTest {

	private static final int DEFAULT_PORT = 8080;

	private GildedRoseWebServer server;
	private int port;

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	@Before
	public void setUp() throws Exception {
		port = findPort();
		server = new GildedRoseWebServer(port);

		startOnConcurrentThread(server);

		Thread.sleep(250);
	}

	private int findPort() {
		return findPortFromEnvironment().or(DEFAULT_PORT);
	}

	private Optional<Integer> findPortFromEnvironment() {
		try {
			Integer environmentPort = Integer.valueOf(System.getenv("PORT"));
			return Optional.fromNullable(environmentPort);
		} catch (Exception e) {
			return Optional.absent();
		}
	}

	private void startOnConcurrentThread(final GildedRoseWebServer server) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				server.start();
			}
		});
	}

	@Test
	public void serverSayHello() throws Exception {
		// when
		HttpMethod method = call("/");

		// then
		assertThat(method.getResponseBodyAsString()).startsWith("Hello");
	}

	private HttpMethod call(String context) throws IOException, HttpException {
		String adress = format("http://localhost:%d%s", port, context);

		HttpMethod method = new GetMethod(adress);
		new HttpClient().executeMethod(method);

		return method;
	}
}
