package server.util;

import java.util.ArrayList;
import java.util.List;

import static server.util.Const.*;

public class HttpHandler {

    private static List<String> list = new ArrayList<>();
    public static final HttpHandler INSTANCE = new HttpHandler(list);

    private HttpHandler(List<String> list) {
        HttpHandler.list = list;
    }

    public String handle(String method, String requestUrl, StringBuilder requestBody) {
        switch (method) {
            case GET_METHOD:
                if (requestUrl.equals("/")) {
                    return executeGetMethod();
                }
                break;
            case POST_METHOD:
                if (requestUrl.equals("/")) {
                    executePostMethod(requestBody.toString());
                }
                break;
            case PUT_METHOD:
                Integer targetIndex = Integer.valueOf(requestUrl.split("/")[1]);
                if (targetIndex >= 0 && targetIndex < list.size()) {
                    executePutMethod(requestBody.toString(), targetIndex);
                }
                break;
            case DELETE_METHOD:
                Integer deleteIndex = Integer.valueOf(requestUrl.split("/")[1]);
                if (deleteIndex >= 0 && deleteIndex < list.size()) {
                    executeDeleteMethod(deleteIndex);
                }
                break;
        }
        return method + "처리 완료 ^^7";
    }

    private String executeGetMethod() {
        return list.toString();
    }

    private void executePostMethod(String target) {
        list.add(target);
    }

    private void executePutMethod(String target, int targetIndex) {
        list.set(targetIndex, target);
    }

    private void executeDeleteMethod(int targetIndex) {
        list.remove(targetIndex);
    }
}