import static java.lang.Math.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {

	private String name;
	private int sellIn;
	private int quality;

	public void decreaseSellIn() {
		setSellIn(getSellIn() - 1);
	}

	public void increaseQuality() {
		setQuality(min(getQuality() + 1, 50));
	}

	public void decreaseQuality() {
		setQuality(max(getQuality() - 1, 0));
	}

	public void removeQuality() {
		setQuality(0);
	}

}
