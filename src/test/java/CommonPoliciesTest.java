import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CommonPoliciesTest {

	private static final Policy[] DECREASING_SELL_IN_POLICIES = new Policy[] {
			new StandardPolicy(), new CheesePolicy(), new ConcertPassPolicy() };
	@Mock
	private Item item;

	@Test
	public void policiesDecreaseSellIn() throws Exception {
		for (Policy policy : DECREASING_SELL_IN_POLICIES) {
			assertThatPolicyDecreaseSellIn(policy);
		}
	}

	private void assertThatPolicyDecreaseSellIn(Policy policy) {
		// when
		policy.updateSellIn(item);

		// then
		verify(item).decreaseSellIn();
		reset(item);
	}

}
