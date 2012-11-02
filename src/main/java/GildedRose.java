import java.util.List;

public class GildedRose {

	private static Provider provider;

	public static void main(String[] args) {

		provider = new Provider();

		supplyStoreWithSomeItems();
		updateStore();
	}

	public static void updateStore() {
		getStore().updateItems();
	}

	public static List<Item> getItems() {
		return getStore().getItems();
	}

	private static void supplyStoreWithSomeItems() {
		supplyStoreWith("+5 Dexterity Vest", 10, 20, asStandard());
		supplyStoreWith("Aged Brie", 2, 0, asCheese());
		supplyStoreWith("Elixir of the Mongoose", 5, 7, asStandard());
		supplyStoreWith("Sulfuras, Hand of Ragnaros", 0, 80, asLegendary());
		supplyStoreWith("Backstage passes to a TAFKAL80ETC concert", 15, 20, asConcertPass());
		supplyStoreWith("Conjured Mana Cake", 3, 6, asStandard());
	}

	private static void supplyStoreWith(String name, int sellIn, int quality, Policy policy) {
		Item item = new Item(name, sellIn, quality);
		getStore().add(item, policy);
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

}