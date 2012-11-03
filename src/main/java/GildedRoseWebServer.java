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

		bind("/hello", new HelloServlet());
		bind("/items", new ItemsServlet(provider));

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

	/* ********************************************************************* */

	private static class HelloServlet extends HttpServlet {

		private static final long serialVersionUID = -4588984111995703530L;

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {

			PrintWriter writer = resp.getWriter();
			writer.println("Hello Gilded Rose !");

		}
	}

	@AllArgsConstructor
	private static class ItemsServlet extends HttpServlet {

		private static final long serialVersionUID = 1265017917708318188L;

		private Provider provider;

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {

			Store store = provider.provideStore();
			List<Item> items = store.getItems();

			JsonWrapper jsonWrapper = new JsonWrapper(resp);
			jsonWrapper.write(items);
		}
	}

	/* ********************************************************************* */

	@RequiredArgsConstructor
	private static class JsonWrapper {
		private GsonBuilder builder = new GsonBuilder();

		@NonNull
		private HttpServletResponse response;

		public void write(Object value) {
			defineJsonHeaderFor(response);

			Gson gson = builder.create();
			gson.toJson(value, value.getClass(), writerOf(response));
		}

		private void defineJsonHeaderFor(HttpServletResponse response) {
			response.setHeader("Content-Type", "application/json");
			response.setCharacterEncoding("UTF-8");
		}

		private JsonWriter writerOf(HttpServletResponse response) {
			try {
				return new JsonWriter(response.getWriter());
			} catch (IOException e) {
				throw new IllegalStateException("Cannot get writer from response", e);
			}
		}

	}

}
