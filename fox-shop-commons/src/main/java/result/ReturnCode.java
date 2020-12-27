package result;

public enum ReturnCode {
        SUCCESS(200,"操作成功"),
        ERROR(500,"操作失败"),
        USERNAME_PASSWORD_NULL(5001,"用户名密码不能为空"),
        USERNAME_NOEXIST(5002,"用户名不存在"),
        PASSWORD_ERROR(5003,"密码错误"),
        SYSTEM_ERROR(5004,"代码执行异常，请联系管理员"),
        USERNAMEDIE_ERROR(5006,"账号被禁用"),
        Leave_TIME_ERROR(5005,"请假时间选择有误"),
        LOGIN_DISABLED(6666,"登录失效,请重新登录"),
    ;

    ReturnCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

        private Integer code;
        private String msg;

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
}
