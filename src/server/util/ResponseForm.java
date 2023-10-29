package server.util;

public class ResponseForm {

    private Integer status;
    private String message;

    public ResponseForm() {}

    public ResponseForm convertFromStringBuilder(Integer status, String message) {
        this.status = status;
        this.message = message;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
