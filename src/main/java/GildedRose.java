import static java.lang.Math.*;

import java.util.ArrayList;
import java.util.List;

public class GildedRose {

	private static List<Item> items = null;

	public static void main(String[] args) {

		items = new ArrayList<Item>();
		items.add(new Item("+5 Dexterity Vest", 10, 20));
		items.add(new Item("Aged Brie", 2, 0));
		items.add(new Item("Elixir of the Mongoose", 5, 7));
		items.add(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
		items.add(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
		items.add(new Item("Conjured Mana Cake", 3, 6));

		update();
	}

	public static void update() {
		for (Item item : items) {
			update(item);
		}
	}

	private static void update(Item item) {
		if ("Sulfuras, Hand of Ragnaros".equals(item.getName())) {
			return;
		}

		decreaseSellIn(item);

		if ("Aged Brie".equals(item.getName())) {
			increaseQuality(item);
			if (item.getSellIn() < 0)
				increaseQuality(item);
		} else if ("Backstage passes to a TAFKAL80ETC concert".equals(item.getName())) {
			increaseQuality(item);
			if (item.getSellIn() < 10)
				increaseQuality(item);
			if (item.getSellIn() < 5)
				increaseQuality(item);
			if (item.getSellIn() < 0)
				removeQuality(item);
		} else {
			decreaseQuality(item);
			if (item.getSellIn() < 0)
				decreaseQuality(item);
		}
	}

	private static void decreaseSellIn(Item item) {
		item.setSellIn(item.getSellIn() - 1);
	}

	private static void increaseQuality(Item item) {
		item.setQuality(min(item.getQuality() + 1, 50));
	}

	private static void decreaseQuality(Item item) {
		item.setQuality(max(item.getQuality() - 1, 0));
	}

	private static void removeQuality(Item item) {
		item.setQuality(0);
	}

	public static List<Item> getItems() {
		return items;
	}

}