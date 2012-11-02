import static com.google.inject.Guice.*;
import static java.lang.String.*;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Singleton;

public class Provider {

	private static final Class<?>[] POLICIES = new Class<?>[] { StandardPolicy.class,
			CheesePolicy.class, ConcertPassPolicy.class, LegendaryPolicy.class };

	private Injector injector;

	private static class Module extends AbstractModule {

		@Override
		protected void configure() {
			bindSingletonOf(Store.class);
			bindSingletonOf(POLICIES);
		}

		private void bindSingletonOf(Class<?>... types) {
			for (Class<?> type : types) {
				bind(type).in(Singleton.class);
			}
		}

	}

	public Provider() {
		injector = createInjector(new Module());
	}

	public Store provideStore() {
		return injector.getInstance(Store.class);
	}

	public <T extends Policy> T providePolicy(Class<T> policy) {
		mandatory("policy", policy);
		return injector.getInstance(policy);
	}

	private void mandatory(String name, Object value) {
		if (value == null) {
			throw new IllegalArgumentException(format("%s is mandatory", name));
		}
	}

}
