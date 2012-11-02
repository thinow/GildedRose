import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConcertPassPolicyTest {

	private static final int BIG_SELL_IN = 50;
	private static final int INTERMEDIATE_SELL_IN = 9;
	private static final int SMALL_SELL_IN = 4;
	private static final int NEGATIVE_SELL_IN = -1;

	private ConcertPassPolicy policy = new ConcertPassPolicy();

	@Mock
	private Item item;

	@Test
	public void policyIncreaseQualityForBigSellIn() throws Exception {
		// given
		defineSellIn(BIG_SELL_IN);

		// when
		policy.updateQuality(item);

		// then
		verify(item).increaseQuality();
	}

	@Test
	public void policyIncreaseQualityForIntermediateSellIn() throws Exception {
		// given
		defineSellIn(INTERMEDIATE_SELL_IN);

		// when
		policy.updateQuality(item);

		// then
		verify(item, times(2)).increaseQuality();
	}

	@Test
	public void policyIncreaseQualityForSmallSellIn() throws Exception {
		// given
		defineSellIn(SMALL_SELL_IN);

		// when
		policy.updateQuality(item);

		// then
		verify(item, times(3)).increaseQuality();
	}

	@Test
	public void policyRemoveQuality() throws Exception {
		// given
		defineSellIn(NEGATIVE_SELL_IN);

		// when
		policy.updateQuality(item);

		// then
		verify(item).removeQuality();
	}

	private void defineSellIn(int sellIn) {
		when(item.getSellIn()).thenReturn(sellIn);
	}
}
