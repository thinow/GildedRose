import static org.fest.assertions.Assertions.*;

import org.junit.Test;

public class ItemTest {

	private static final String ANY_NAME = "NAME";

	private static final int SELL_IN = 5;
	private static final int QUALITY = 10;

	private static final int MINIMAL_QUALITY = 0;
	private static final int MAXIMAL_QUALITY = 50;

	private Item item = new Item(ANY_NAME, SELL_IN, QUALITY);

	@Test
	public void decreaseSellIn() throws Exception {
		// when
		item.decreaseSellIn();

		// then
		asserThatSellInEquals(SELL_IN - 1);
	}

	private void asserThatSellInEquals(int expected) {
		assertThat(item.getSellIn()).isEqualTo(expected);
	}

	@Test
	public void decreaseQuality() throws Exception {
		// when
		item.decreaseQuality();

		// then
		assertThatQualityEquals(QUALITY - 1);
	}

	@Test
	public void attemptToDecreaseMinimalQuality() throws Exception {
		// given
		item.setQuality(MINIMAL_QUALITY);

		// when
		item.decreaseQuality();

		// then
		assertThatQualityEquals(MINIMAL_QUALITY);
	}

	@Test
	public void increaseQuality() throws Exception {
		// when
		item.increaseQuality();

		// then
		assertThatQualityEquals(QUALITY + 1);
	}

	@Test
	public void attemptToIncreaseMaximalQuality() throws Exception {
		// given
		item.setQuality(MAXIMAL_QUALITY);

		// when
		item.increaseQuality();

		// then
		assertThatQualityEquals(MAXIMAL_QUALITY);
	}

	@Test
	public void removeQuality() throws Exception {
		// when
		item.removeQuality();

		// then
		assertThatQualityEquals(0);
	}

	private void assertThatQualityEquals(int expected) {
		assertThat(item.getQuality()).isEqualTo(expected);
	}

	@Test
	public void transformItemIntoJson() throws Exception {
		// when
		String json = item.asJson();

		// then
		assertThat(json).isEqualTo("{\"name\":\"NAME\",\"sellIn\":5,\"quality\":10}");
	}

}
