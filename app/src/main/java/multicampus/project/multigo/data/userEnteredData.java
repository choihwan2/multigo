package multicampus.project.multigo.data;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class userEnteredData {
    private String id;
    private boolean enterState;

    public userEnteredData() {

    }

    public userEnteredData(String id, boolean enterState) {
        this.id = id;
        this.enterState = enterState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isEnterState() {
        return enterState;
    }

    public void setEnterState(boolean enterState) {
        this.enterState = enterState;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("isEntered", enterState);
        return result;
    }

}
