package deckoapi;

import org.json.JSONObject;

import java.util.Vector;

// From: https://decko.ceskatelevize.cz/cms/icontainerjs/js/IntegrativeContainerJS.min.js?v=1.0.0.287
// Just part
public class DpCont {
    public ContManager dpCont;
    private final Vector<DpContReadyListener> readyListeners;

    DpCont() {
        readyListeners = new Vector<>();
    }

    public static class ContManager {
        DpCont dpCont;

        public ContManager(DpCont cont, JSONObject a) {
            dpCont = cont;
            System.out.println("new ContManager: " + a.toString());
            dpCont.onReady();
        }
    }

    private void onReady() {
        for (DpContReadyListener listener : readyListeners) listener.onReady();
    }

    public void addReadyListener(DpContReadyListener listener) {
        readyListeners.add(listener);
    }
}
