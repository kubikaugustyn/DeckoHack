package deckoapi.amf;

public class FaultInfo {
    int faultCode;
    String faultDetail;
    String faultString;

    public FaultInfo(int faultCode, String faultDetail, String faultString) {
        this.faultCode = faultCode;
        this.faultDetail = faultDetail;
        this.faultString = faultString;
    }
}
