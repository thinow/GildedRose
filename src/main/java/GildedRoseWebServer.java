import static java.lang.String.*;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

@RequiredArgsConstructor
public class GildedRoseWebServer {

	private Server server;
	private ServletContextHandler context;

	@NonNull
	private int port;

	public void start() {
		server = new Server(port);
		context = buildServerContext(server);

		bind("/*", new HelloServlet());

		startWithErrorsHandling(server);
	}

	private ServletContextHandler buildServerContext(Server server) {
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");

		server.setHandler(context);

		return context;
	}

	private void bind(String path, HelloServlet servlet) {
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

		GildedRoseWebServer server = new GildedRoseWebServer(port);
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

}
