import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StandardPolicyTest {

	private static final int NEGATIVE_SELL_IN = -1;

	private static final int POSITIVE_SELL_IN = 10;

	private StandardPolicy policy = new StandardPolicy();

	@Mock
	private Item item;

	@Test
	public void policyDecreaseSellIn() throws Exception {
		// when
		policy.updateSellIn(item);

		// then
		verify(item).decreaseSellIn();
	}

	@Test
	public void policyDecreaseQuality() throws Exception {
		// given
		defineSellIn(POSITIVE_SELL_IN);

		// when
		policy.updateQuality(item);

		// then
		verify(item).decreaseQuality();
	}

	@Test
	public void policyDecreaseQualityTwice() throws Exception {
		// given
		defineSellIn(NEGATIVE_SELL_IN);

		// when
		policy.updateQuality(item);

		// then
		verify(item, times(2)).decreaseQuality();
	}

	private void defineSellIn(int sellIn) {
		when(item.getSellIn()).thenReturn(sellIn);
	}

}
