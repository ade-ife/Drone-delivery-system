package Musala.Soft.Assessment.Pojo;


public enum EnumResponseCodes {

    SUCCESS("00"),
    DUPLICATE_TRANSACTION_REFERENCE("01"),
    UNABLE_TO_CREATE_REQUEST("02"),
    ERROR_PROCESSING_REQUEST("11"),
    INTERNAL_BAD_REQUEST("15"),
    FAILED("96");
    private final String title;
    EnumResponseCodes(String title) {
        this.title = title;
    }

    public String getValue() {
        return this.title;
    }
}

