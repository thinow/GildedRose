import static org.fest.assertions.Assertions.*;

import org.junit.Test;

public class ProviderTest {

	private Provider provider = new Provider();

	@Test
	public void provideSingleStore() throws Exception {
		// when
		Store store = provider.provideStore();
		Store anotherStoreInstance = provider.provideStore();

		// then
		assertThatInstancesAreUnique(store, anotherStoreInstance);
	}

	@Test(expected = IllegalArgumentException.class)
	public void cannotProvidePolicyWithoutType() throws Exception {
		// when
		provider.providePolicy(null);
	}

	@Test
	public void provideSinglePolicy() throws Exception {
		// when
		StandardPolicy policy = provider.providePolicy(StandardPolicy.class);
		StandardPolicy anotherPolicyInstance = provider.providePolicy(StandardPolicy.class);

		// then
		assertThatInstancesAreUnique(policy, anotherPolicyInstance);
	}

	private void assertThatInstancesAreUnique(Object firstInstance, Object secondInstance) {
		assertThat(firstInstance).isNotNull().isSameAs(secondInstance);
	}

}
