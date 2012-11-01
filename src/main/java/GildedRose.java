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
		ItemUpdater updater = new ItemUpdater(item);
		updater.update();
	}

	public static List<Item> getItems() {
		return items;
	}

}