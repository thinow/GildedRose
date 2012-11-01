import java.util.List;

public class GildedRose {

	private static final StandardPolicy STANDARD_POLICY = new StandardPolicy();
	private static final CheesePolicy CHEESE_POLICY = new CheesePolicy();
	private static final ConcertPassPolicy PASS_POLICY = new ConcertPassPolicy();
	private static final LegendaryPolicy LEGENDARY_POLICY = new LegendaryPolicy();

	private static Store store;

	public static void main(String[] args) {

		store = new Store();

		store.add(new Item("+5 Dexterity Vest", 10, 20), STANDARD_POLICY);
		store.add(new Item("Aged Brie", 2, 0), CHEESE_POLICY);
		store.add(new Item("Elixir of the Mongoose", 5, 7), STANDARD_POLICY);
		store.add(new Item("Sulfuras, Hand of Ragnaros", 0, 80), LEGENDARY_POLICY);
		store.add(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20), PASS_POLICY);
		store.add(new Item("Conjured Mana Cake", 3, 6), STANDARD_POLICY);

		store.updateItems();
	}

	public static void update() {
		store.updateItems();
	}

	public static List<Item> getItems() {
		return store.getItems();
	}

}