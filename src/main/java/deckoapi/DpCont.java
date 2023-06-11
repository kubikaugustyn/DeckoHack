package deckoapi;

import org.json.JSONObject;

import java.util.Vector;

// From: https://decko.ceskatelevize.cz/cms/icontainerjs/js/IntegrativeContainerJS.min.js?v=1.0.0.287
// Just part
public class DpCont {
    public static ContManager dpCont;
    private static final Vector<DpContReadyListener> readyListeners;

    public static class ContManager {
        public ContManager() {
        }

        public ContManager(JSONObject a) {
            System.out.println("new ContManager: " + a.toString());
            DpCont.onReady();
        }
    }

    private static void onReady() {
        for (DpContReadyListener listener : readyListeners) listener.onReady();
    }

    public static void addReadyListener(DpContReadyListener listener) {
        readyListeners.add(listener);
    }

    static {
        readyListeners = new Vector<>();
    }
}
