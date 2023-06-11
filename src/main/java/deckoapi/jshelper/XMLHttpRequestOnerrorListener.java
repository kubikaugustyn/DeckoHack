package deckoapi.jshelper;

public interface XMLHttpRequestOnerrorListener<T> {
    void onerror(Exception ex, XMLHttpRequest<T> req);
}
