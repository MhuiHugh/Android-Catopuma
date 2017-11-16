package hugh.catopuma.bean;

/**
 * Created by Menghui on 2017/10/25.
 */
public class BaseBean<T1> {
    /**
     * status : 1
     * data : {}
     * msg : 请求成功
     */
    private String status;
    private T1 data;
    private String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T1 getData() {
        return data;
    }

    public void setData(T1 data) {
        this.data = data;
    }
}
