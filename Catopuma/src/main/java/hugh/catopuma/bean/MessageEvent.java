package hugh.catopuma.bean;

/**
 * 事件总线
 * action 为事件名称
 * message 可以传递字符内容
 * code 事件状态码
 * Created by on 2016/5/28.
 */
public class MessageEvent {

    private String action;
    private Object message;
    private int code;

    public MessageEvent(String act) {
        this.action = act;
    }

    public MessageEvent(String act, Object msg) {
        this.action = act;
        this.message = msg;
    }

    public MessageEvent(String act, Object msg, int code) {
        this.action = act;
        this.message = msg;
        this.code = code;
    }

    public MessageEvent(String act, int code) {
        this.action = act;
        this.code = code;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String act) {
        action = act;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getMessage() {
        return message;
    }

    public void setCode(int c) {
        this.code = c;
    }

    public int getCode() {
        return code;
    }

}
