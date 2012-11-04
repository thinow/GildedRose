import static java.lang.String.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

@RequiredArgsConstructor
public class GildedRoseWebServer {

	@NonNull
	private Provider provider;
	@NonNull
	private int port;

	private Server server;
	private ServletContextHandler context;

	public void start() {
		server = new Server(port);
		context = buildServerContext(server);

		bind("/*", new ItemsServlet(provider));

		startWithErrorsHandling(server);
	}

	private ServletContextHandler buildServerContext(Server server) {
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");

		server.setHandler(context);

		return context;
	}

	private void bind(String path, Servlet servlet) {
		context.addServlet(new ServletHolder(servlet), path);
	}

	private void startWithErrorsHandling(Server server) {
		try {
			System.out.println(format("Starting server on port %d ...", port));

			server.start();
			server.join();
		} catch (Exception e) {
			throw new IllegalStateException("Cannot start server", e);
		}
	}

	/* ********************************************************************* */

	public static void main(String[] args) {
		int port = findPort();

		Provider provider = new Provider();
		provider.provideSupplier().supplyStoreWithSomeItems();

		GildedRoseWebServer server = new GildedRoseWebServer(provider, port);
		server.start();
	}

	private static int findPort() {
		try {
			return Integer.valueOf(System.getenv("PORT"));
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot retrieve port value", e);
		}
	}

	@AllArgsConstructor
	private static class ItemsServlet extends HttpServlet {

		private static final long serialVersionUID = 1265017917708318188L;

		private Provider provider;

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {

			handleRequestedOrder(req);

			Store store = provider.provideStore();
			List<Item> items = store.getItems();

			JsonWrapper jsonWrapper = new JsonWrapper(req, resp);
			jsonWrapper.write(items);
		}

		private void handleRequestedOrder(HttpServletRequest request) {
			String uri = request.getRequestURI();

			if (uri.endsWith("update")) {
				Store store = provider.provideStore();
				store.updateItems();
			} else if (uri.endsWith("reset")) {
				Supplier supplier = provider.provideSupplier();
				supplier.supplyStoreWithSomeItems();
			}
		}
	}

	/* ********************************************************************* */

	@RequiredArgsConstructor
	private static class JsonWrapper {
		private GsonBuilder builder = new GsonBuilder();

		@NonNull
		private HttpServletRequest request;
		@NonNull
		private HttpServletResponse response;

		public void write(Object value) {
			Optional<String> callback = findCallbackInto(request);

			defineJsonHeaderFor(response);

			injectPrefixFor(callback);
			injectJsonBody(value);
			injectSuffixFor(callback);
		}

		private Optional<String> findCallbackInto(HttpServletRequest request) {
			String callback = request.getParameter("callback");
			return Optional.fromNullable(callback);
		}

		private void defineJsonHeaderFor(HttpServletResponse response) {
			response.setHeader("Content-Type", "application/json");
			response.setCharacterEncoding("UTF-8");
		}

		private void injectPrefixFor(Optional<String> callback) {
			if (callback.isPresent()) {
				String text = format("%s(", callback.get());
				printWriterOf(response).print(text);
			}
		}

		private void injectJsonBody(Object value) {
			Gson gson = builder.create();
			gson.toJson(value, value.getClass(), jsonWriterOf(response));
		}

		private void injectSuffixFor(Optional<String> callback) {
			if (callback.isPresent()) {
				String text = ")";
				printWriterOf(response).print(text);
			}
		}

		private JsonWriter jsonWriterOf(HttpServletResponse response) {
			return new JsonWriter(printWriterOf(response));
		}

		private PrintWriter printWriterOf(HttpServletResponse response) {
			try {
				return response.getWriter();
			} catch (IOException e) {
				throw new IllegalStateException("Cannot get writer from response", e);
			}
		}

	}

}
