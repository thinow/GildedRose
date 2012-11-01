import static java.lang.Math.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {

	private static final int MIN_QUALITY = 0;
	private static final int MAX_QUALITY = 50;

	private String name;
	private int sellIn;
	private int quality;

	public void decreaseSellIn() {
		sellIn -= 1;
	}

	public void increaseQuality() {
		quality = min(quality + 1, MAX_QUALITY);
	}

	public void decreaseQuality() {
		quality = max(quality - 1, MIN_QUALITY);
	}

	public void removeQuality() {
		quality = 0;
	}

}
