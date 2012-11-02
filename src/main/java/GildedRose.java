import java.util.List;

public class GildedRose {

	private static Provider provider;

	public static void main(String[] args) {

		provider = new Provider();

		Store store = getStore();

		store.add(new Item("+5 Dexterity Vest", 10, 20), asStandard());
		store.add(new Item("Aged Brie", 2, 0), asCheese());
		store.add(new Item("Elixir of the Mongoose", 5, 7), asStandard());
		store.add(new Item("Sulfuras, Hand of Ragnaros", 0, 80), asLegendary());
		store.add(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20), asConcertPass());
		store.add(new Item("Conjured Mana Cake", 3, 6), asStandard());

		store.updateItems();
	}

	private static Store getStore() {
		return provider.provideStore();
	}

	private static StandardPolicy asStandard() {
		return provider.providePolicy(StandardPolicy.class);
	}

	private static CheesePolicy asCheese() {
		return provider.providePolicy(CheesePolicy.class);
	}

	private static ConcertPassPolicy asConcertPass() {
		return provider.providePolicy(ConcertPassPolicy.class);
	}

	private static LegendaryPolicy asLegendary() {
		return provider.providePolicy(LegendaryPolicy.class);
	}

	public static void update() {
		getStore().updateItems();
	}

	public static List<Item> getItems() {
		return getStore().getItems();
	}

}