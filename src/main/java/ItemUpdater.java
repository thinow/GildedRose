import static java.lang.Math.*;
import lombok.AllArgsConstructor;
import lombok.Delegate;

@AllArgsConstructor
public class ItemUpdater {

	@Delegate
	private Item item;

	public void update() {
		if ("Sulfuras, Hand of Ragnaros".equals(getName())) {
			return;
		}

		decreaseSellIn();

		if ("Aged Brie".equals(getName())) {
			increaseQuality();
			if (getSellIn() < 0)
				increaseQuality();
		} else if ("Backstage passes to a TAFKAL80ETC concert".equals(getName())) {
			increaseQuality();
			if (getSellIn() < 10)
				increaseQuality();
			if (getSellIn() < 5)
				increaseQuality();
			if (getSellIn() < 0)
				removeQuality();
		} else {
			decreaseQuality();
			if (getSellIn() < 0)
				decreaseQuality();
		}
	}

	private void decreaseSellIn() {
		setSellIn(getSellIn() - 1);
	}

	private void increaseQuality() {
		setQuality(min(getQuality() + 1, 50));
	}

	private void decreaseQuality() {
		setQuality(max(getQuality() - 1, 0));
	}

	private void removeQuality() {
		setQuality(0);
	}

}
