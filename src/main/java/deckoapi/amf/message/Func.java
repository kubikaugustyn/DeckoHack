package deckoapi.amf.message;


public interface Func<T, K> {
    K get(T arg);
}
