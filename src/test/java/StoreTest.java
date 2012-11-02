import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;
import org.mockito.InOrder;

public class StoreTest {

	private static final Item ITEM = mock(Item.class);
	private static final Item ANOTHER_ITEM = mock(Item.class);

	private static final Policy POLICY = mock(Policy.class);

	private Store store = new Store();

	@Test(expected = IllegalArgumentException.class)
	public void cannotAddNullItem() throws Exception {
		store.add(null, POLICY);
	}

	@Test(expected = IllegalArgumentException.class)
	public void cannotAddItemWithoutPolicy() throws Exception {
		store.add(ITEM, null);
	}

	@Test
	public void retrieveItems() throws Exception {
		// given
		store.add(ITEM, POLICY);
		store.add(ANOTHER_ITEM, POLICY);

		// when
		List<Item> items = store.getItems();

		// then
		assertThat(items).containsExactly(ITEM, ANOTHER_ITEM);
	}

	@Test
	public void updateItemsUsingPolicy() throws Exception {
		// given
		store.add(ITEM, POLICY);

		// when
		store.updateItems();

		// then
		InOrder inOrder = inOrder(POLICY, ITEM);
		inOrder.verify(POLICY).updateSellIn(ITEM);
		inOrder.verify(POLICY).updateQuality(ITEM);

		verifyNoMoreInteractions(ITEM, POLICY);
	}

}
