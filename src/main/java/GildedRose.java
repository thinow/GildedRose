import java.util.List;

public class GildedRose {

	private static Provider provider;

	public static void main(String[] args) {

		provider = new Provider();

		supplyStore();
		updateStore();
	}

	public static void supplyStore() {
		Supplier supplier = provider.provideSupplier();
		supplier.supplyStoreWithSomeItems();
	}

	public static void updateStore() {
		getStore().updateItems();
	}

	public static List<Item> getItems() {
		return getStore().getItems();
	}

	private static Store getStore() {
		return provider.provideStore();
	}

}