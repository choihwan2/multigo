package multicampus.project.multigo.ui.basket.dummy;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import multicampus.project.multigo.ui.basket.data.ItemsVO;
import multicampus.project.multigo.ui.basket.data.LaisVO;

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

    public static final List<LaisVO> LAIS_ITEMS = new ArrayList<>();

    private static final int COUNT = 5;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
            addList(createDummyListItem(i));
        }
    }

    private static void addItem(ItemsVO item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getItem_id(), item);
    }

    private static void addList(LaisVO item) {
        LAIS_ITEMS.add(item);
    }

    private static ItemsVO createDummyItem(int position) {
        return new ItemsVO("ITEM00" + position, "Item " + position, 1000, 1);
    }

    private static LaisVO createDummyListItem(int position) {
        return new LaisVO(0, ITEMS.get(position - 1).getItem_id(), ITEMS.get(position - 1).getCnt());
    }

    public static int getSum() {
        int sum = 0;
        for (ItemsVO item : ITEMS) {
            sum += item.getPrice();
        }
        return sum;
    }

    public static String getJsonItems() {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(LAIS_ITEMS);
//            Log.d("DummyContent",jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }


}
