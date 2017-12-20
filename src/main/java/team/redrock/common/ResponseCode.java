package team.redrock.common;

public enum ResponseCode {
    SUCCESS(0, "success"),
    ERROR(1, "error"),
    NEED_LOGIN(10, "need_login"),
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
