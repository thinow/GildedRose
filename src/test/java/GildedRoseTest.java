import static org.fest.assertions.Assertions.*;

import java.util.List;

import org.junit.Test;

public class GildedRoseTest {

	private static final String[] NO_ARGUMENTS = null;

	private static final Object[] EXPECTED_NAMES = new String[] { "+5 Dexterity Vest", "Aged Brie",
			"Elixir of the Mongoose", "Sulfuras, Hand of Ragnaros",
			"Backstage passes to a TAFKAL80ETC concert", "Conjured Mana Cake" };

	@Test
	public void evaluateItemsAfterRunExecutable() {
		// when
		GildedRose.main(NO_ARGUMENTS);

		// then
		List<Item> items = GildedRose.getItems();
		assertThat(items).onProperty("name").containsExactly(EXPECTED_NAMES);
		assertThat(items).onProperty("sellIn").containsExactly(9, 1, 4, 0, 14, 2);
		assertThat(items).onProperty("quality").containsExactly(19, 1, 6, 80, 21, 5);
	}
}
