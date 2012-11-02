import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LegendaryPolicyTest {

	private LegendaryPolicy policy = new LegendaryPolicy();

	@Mock
	private Item item;

	@Test
	public void doesNotUpdateSellIn() throws Exception {
		// when
		policy.updateSellIn(item);

		// then
		verifyNoMoreInteractions(item);
	}

	@Test
	public void doesNotUpdateQuality() throws Exception {
		// when
		policy.updateQuality(item);

		// then
		verifyNoMoreInteractions(item);
	}

}
