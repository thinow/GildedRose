import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Store {

	@AllArgsConstructor
	private class ItemAndPolicy {
		@Getter
		private Item item;
		@Getter
		private Policy policy;
	}

	private Collection<ItemAndPolicy> itemsAndPolicies = new ArrayList<ItemAndPolicy>();

	public void add(Item item, Policy policy) {
		Methods.mandatory("item", item);
		Methods.mandatory("policy", policy);

		itemsAndPolicies.add(new ItemAndPolicy(item, policy));
	}

	public void updateItems() {
		for (ItemAndPolicy itemAndPolicy : itemsAndPolicies) {
			Policy policy = itemAndPolicy.getPolicy();
			Item item = itemAndPolicy.getItem();

			update(item, policy);
		}
	}

	private void update(Item item, Policy policy) {
		policy.updateSellIn(item);
		policy.updateQuality(item);
	}

	public List<Item> getItems() {
		List<Item> items = new ArrayList<Item>(itemsAndPolicies.size());
		for (ItemAndPolicy itemAndPolicy : itemsAndPolicies) {
			items.add(itemAndPolicy.getItem());
		}
		return items;
	}
}
