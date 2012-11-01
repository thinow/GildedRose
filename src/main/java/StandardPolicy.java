public class StandardPolicy implements Policy {

	@Override
	public void updateSellIn(Item item) {
		item.decreaseSellIn();
	}

	@Override
	public void updateQuality(Item item) {

		item.decreaseQuality();

		if (item.getSellIn() < 0)
			item.decreaseQuality();

	}

}
