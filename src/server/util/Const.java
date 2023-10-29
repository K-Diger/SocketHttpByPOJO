package server.util;

public class Const {
    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";
    public static final String PUT_METHOD = "PUT";
    public static final String DELETE_METHOD = "DELETE";
    public static final String CONTENT_LENGTH = "Content-Length";

    public static final String DEFAULT_RESPONSE_FORM_TYPE = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n";
    public static final String E501_FAILED_RESPONSE_FORM = "HTTP/1.1 501 Not Implemented\r\nContent-Type: application/json\r\n\r\n아직 구현되지 않은 메서드입니둥";

}
