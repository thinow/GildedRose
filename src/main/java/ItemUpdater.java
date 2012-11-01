import static java.lang.Math.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ItemUpdater {
	private Item item;

	public void update() {
		if ("Sulfuras, Hand of Ragnaros".equals(item.getName())) {
			return;
		}

		decreaseSellIn();

		if ("Aged Brie".equals(item.getName())) {
			increaseQuality();
			if (item.getSellIn() < 0)
				increaseQuality();
		} else if ("Backstage passes to a TAFKAL80ETC concert".equals(item.getName())) {
			increaseQuality();
			if (item.getSellIn() < 10)
				increaseQuality();
			if (item.getSellIn() < 5)
				increaseQuality();
			if (item.getSellIn() < 0)
				removeQuality();
		} else {
			decreaseQuality();
			if (item.getSellIn() < 0)
				decreaseQuality();
		}
	}

	private void decreaseSellIn() {
		item.setSellIn(item.getSellIn() - 1);
	}

	private void increaseQuality() {
		item.setQuality(min(item.getQuality() + 1, 50));
	}

	private void decreaseQuality() {
		item.setQuality(max(item.getQuality() - 1, 0));
	}

	private void removeQuality() {
		item.setQuality(0);
	}

}
