import static com.google.inject.Guice.*;
import lombok.AllArgsConstructor;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Singleton;

public class Provider {

	private static final Class<?>[] POLICIES = new Class<?>[] { StandardPolicy.class,
			CheesePolicy.class, ConcertPassPolicy.class, LegendaryPolicy.class };

	private Injector injector;

	@AllArgsConstructor
	private static class Module extends AbstractModule {
		private Provider provider;

		@Override
		protected void configure() {
			bind(Provider.class).toInstance(provider);

			bindSingletonOf(Store.class);
			bindSingletonOf(Supplier.class);
			bindSingletonOf(POLICIES);
		}

		private void bindSingletonOf(Class<?>... types) {
			for (Class<?> type : types) {
				bind(type).in(Singleton.class);
			}
		}

	}

	public Provider() {
		injector = createInjector(new Module(this));
	}

	public Store provideStore() {
		return injector.getInstance(Store.class);
	}

	public Supplier provideSupplier() {
		return injector.getInstance(Supplier.class);
	}

	public <T extends Policy> T providePolicy(Class<T> policy) {
		Methods.mandatory("policy", policy);
		return injector.getInstance(policy);
	}

}
