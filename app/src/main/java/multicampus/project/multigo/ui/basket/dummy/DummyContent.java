package multicampus.project.multigo.ui.basket.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import multicampus.project.multigo.ui.basket.data.ItemsVO;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<ItemsVO> ITEMS = new ArrayList<ItemsVO>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, ItemsVO> ITEM_MAP = new HashMap<String, ItemsVO>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(ItemsVO item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getItem_id(), item);
    }

    private static ItemsVO createDummyItem(int position) {
        return new ItemsVO("ITEM00" + position, "Item " + position, 1000);
    }


}
