import lombok.Delegate;

import com.google.inject.Inject;

public class Supplier {

	@Delegate
	private Provider provider;

	@Inject
	public Supplier(Provider provider) {
		this.provider = provider;
	}

	public void supplyStoreWithSomeItems() {
		supplyStoreWith("+5 Dexterity Vest", 10, 20, asStandard());
		supplyStoreWith("Aged Brie", 2, 0, asCheese());
		supplyStoreWith("Elixir of the Mongoose", 5, 7, asStandard());
		supplyStoreWith("Sulfuras, Hand of Ragnaros", 0, 80, asLegendary());
		supplyStoreWith("Backstage passes to a TAFKAL80ETC concert", 15, 20, asConcertPass());
		supplyStoreWith("Conjured Mana Cake", 3, 6, asStandard());
	}

	private void supplyStoreWith(String name, int sellIn, int quality, Policy policy) {
		Item item = new Item(name, sellIn, quality);
		provideStore().add(item, policy);
	}

	private StandardPolicy asStandard() {
		return providePolicy(StandardPolicy.class);
	}

	private CheesePolicy asCheese() {
		return providePolicy(CheesePolicy.class);
	}

	private ConcertPassPolicy asConcertPass() {
		return providePolicy(ConcertPassPolicy.class);
	}

	private LegendaryPolicy asLegendary() {
		return providePolicy(LegendaryPolicy.class);
	}
}
