import lombok.AllArgsConstructor;
import lombok.Delegate;

@AllArgsConstructor
public class ItemUpdater {

	@Delegate
	private Item item;

	public void update() {
		Policy policy = findPolicyOf(item);
		policy.updateSellIn(item);
		policy.updateQuality(item);
	}

	private Policy findPolicyOf(Item item) {
		if ("Sulfuras, Hand of Ragnaros".equals(getName())) {
			return new LegendaryPolicy();
		} else if ("Aged Brie".equals(getName())) {
			return new CheesePolicy();
		} else if ("Backstage passes to a TAFKAL80ETC concert".equals(getName())) {
			return new ConcertPassPolicy();
		} else {
			return new StandardPolicy();
		}
	}

}
