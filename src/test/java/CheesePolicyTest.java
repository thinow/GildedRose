import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CheesePolicyTest {

	private static final int POSITIVE_SELL_IN = 10;
	private static final int NEGATIVE_SELL_IN = -1;

	private CheesePolicy policy = new CheesePolicy();

	@Mock
	private Item item;

	@Test
	public void policyIncreaseQuality() throws Exception {
		// given
		defineSellIn(POSITIVE_SELL_IN);

		// when
		policy.updateQuality(item);

		// then
		verify(item).increaseQuality();
	}

	@Test
	public void policyIncreaseQualityTwice() throws Exception {
		// given
		defineSellIn(NEGATIVE_SELL_IN);

		// when
		policy.updateQuality(item);

		// then
		verify(item, times(2)).increaseQuality();
	}

	private void defineSellIn(int sellIn) {
		when(item.getSellIn()).thenReturn(sellIn);
	}
}
