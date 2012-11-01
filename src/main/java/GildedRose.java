import java.util.ArrayList;
import java.util.List;

public class GildedRose {

	private static List<Item> items = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		items = new ArrayList<Item>();
		items.add(new Item("+5 Dexterity Vest", 10, 20));
		items.add(new Item("Aged Brie", 2, 0));
		items.add(new Item("Elixir of the Mongoose", 5, 7));
		items.add(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
		items.add(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
		items.add(new Item("Conjured Mana Cake", 3, 6));

		updateQuality();
	}

	public static void updateQuality() {
		for (Item item : items) {
			updateQuality(item);
		}
	}

	private static void updateQuality(Item item) {
		if ("Sulfuras, Hand of Ragnaros".equals(item.getName())) {
			return;
		}

		decreaseSellIn(item);

		if ("Aged Brie".equals(item.getName())) {
			if (item.getQuality() < 50) {
				increaseQuality(item);
			}
		} else if ("Backstage passes to a TAFKAL80ETC concert".equals(item.getName())) {
			if (item.getQuality() < 50) {
				increaseQuality(item);

				if (item.getSellIn() < 10) {
					if (item.getQuality() < 50) {
						increaseQuality(item);
					}
				}

				if (item.getSellIn() < 5) {
					if (item.getQuality() < 50) {
						increaseQuality(item);
					}
				}
			}
		} else {
			if (item.getQuality() > 0) {
				decreaseQuality(item);
			}
		}

		if ("Aged Brie".equals(item.getName())) {
			if (item.getSellIn() < 0) {
				if (item.getQuality() < 50) {
					increaseQuality(item);
				}
			}
		} else if ("Backstage passes to a TAFKAL80ETC concert".equals(item.getName())) {
			if (item.getSellIn() < 0) {
				removeQuality(item);
			}
		} else {
			if (item.getSellIn() < 0) {
				if (item.getQuality() > 0) {
					decreaseQuality(item);
				}
			}
		}
	}

	private static void decreaseSellIn(Item item) {
		item.setSellIn(item.getSellIn() - 1);
	}

	private static void increaseQuality(Item item) {
		item.setQuality(item.getQuality() + 1);
	}

	private static void decreaseQuality(Item item) {
		item.setQuality(item.getQuality() - 1);
	}

	private static void removeQuality(Item item) {
		item.setQuality(0);
	}

	public static List<Item> getItems() {
		return items;
	}

}