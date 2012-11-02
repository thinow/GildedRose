import static org.fest.assertions.Assertions.*;

import java.util.List;

import org.junit.Test;

public class HarnessTest {

	private static final int WITHOUT_UPDATES = 0;
	private static final int ONLY_ONE_UPDATE = 1;
	private static final int TWO_UPDATES = 2;
	private static final int SEVERAL_UPDATES = 5;
	private static final int MASSIVE_UPDATES = 50;

	private static final Object[] EXPECTED_NAMES = new String[] { "+5 Dexterity Vest", "Aged Brie",
			"Elixir of the Mongoose", "Sulfuras, Hand of Ragnaros",
			"Backstage passes to a TAFKAL80ETC concert", "Conjured Mana Cake" };

	private static final String[] NO_ARGUMENTS = null;

	@Test
	public void evaluateItemsAfterRunExecutable() {
		// when
		runAndUpdateItems(WITHOUT_UPDATES);

		// then
		List<Item> items = GildedRose.getItems();
		assertThat(items).onProperty("name").containsExactly(EXPECTED_NAMES);
		assertThat(items).onProperty("sellIn").containsExactly(9, 1, 4, 0, 14, 2);
		assertThat(items).onProperty("quality").containsExactly(19, 1, 6, 80, 21, 5);
	}

	@Test
	public void evaluateItemsAfterRunAndUpdate() throws Exception {
		// when
		runAndUpdateItems(ONLY_ONE_UPDATE);

		// then
		List<Item> items = GildedRose.getItems();
		assertThat(items).onProperty("name").containsExactly(EXPECTED_NAMES);
		assertThat(items).onProperty("sellIn").containsExactly(8, 0, 3, 0, 13, 1);
		assertThat(items).onProperty("quality").containsExactly(18, 2, 5, 80, 22, 4);
	}

	@Test
	public void evaluateItemsWithTwoUpdates() throws Exception {
		// when
		runAndUpdateItems(TWO_UPDATES);

		// then
		List<Item> items = GildedRose.getItems();
		assertThat(items).onProperty("name").containsExactly(EXPECTED_NAMES);
		assertThat(items).onProperty("sellIn").containsExactly(7, -1, 2, 0, 12, 0);
		assertThat(items).onProperty("quality").containsExactly(17, 4, 4, 80, 23, 3);
	}

	@Test
	public void evaluateItemsWithSeveralUpdates() throws Exception {
		// when
		runAndUpdateItems(SEVERAL_UPDATES);

		// then
		List<Item> items = GildedRose.getItems();
		assertThat(items).onProperty("name").containsExactly(EXPECTED_NAMES);
		assertThat(items).onProperty("sellIn").containsExactly(4, -4, -1, 0, 9, -3);
		assertThat(items).onProperty("quality").containsExactly(14, 10, 0, 80, 27, 0);
	}

	@Test
	public void evaluateMassivelyUpdatedItems() throws Exception {
		// when
		runAndUpdateItems(MASSIVE_UPDATES);

		// then
		List<Item> items = GildedRose.getItems();
		assertThat(items).onProperty("name").containsExactly(EXPECTED_NAMES);
		assertThat(items).onProperty("sellIn").containsExactly(-41, -49, -46, 0, -36, -48);
		assertThat(items).onProperty("quality").containsExactly(0, 50, 0, 80, 0, 0);
	}

	private void runAndUpdateItems(int updatesCount) {
		GildedRose.main(NO_ARGUMENTS);
		for (int index = 0; index < updatesCount; index++) {
			GildedRose.updateStore();
		}
	}
}
