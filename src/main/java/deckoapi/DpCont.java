package deckoapi;

import org.json.JSONObject;

// From: https://decko.ceskatelevize.cz/cms/icontainerjs/js/IntegrativeContainerJS.min.js?v=1.0.0.287
// Just part
public class DpCont {
    public static ContManager dpCont;

    public static class ContManager {
        public ContManager() {
        }

        public ContManager(JSONObject a) {
            System.out.println("new ContManager: " + a.toString());
        }
    }
}
