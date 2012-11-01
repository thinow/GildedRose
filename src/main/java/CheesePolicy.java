public class CheesePolicy extends StandardPolicy {

	@Override
	public void updateQuality(Item item) {

		item.increaseQuality();

		if (item.getSellIn() < 0)
			item.increaseQuality();

	}

}
