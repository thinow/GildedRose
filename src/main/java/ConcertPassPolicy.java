public class ConcertPassPolicy extends StandardPolicy {

	@Override
	public void updateQuality(Item item) {

		item.increaseQuality();

		if (item.getSellIn() < 10)
			item.increaseQuality();

		if (item.getSellIn() < 5)
			item.increaseQuality();

		if (item.getSellIn() < 0)
			item.removeQuality();

	}

}
