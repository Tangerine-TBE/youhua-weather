package com.example.tianqi.model.bean;

/**
 * @author wujinming QQ:1245074510
 * @name WeatherOne
 * @class name：com.example.tianqi.model.bean
 * @class describe
 * @time 2020/9/8 15:33
 * @class describe
 */
public class MjLifeBean {

    /**
     * code : 26
     * day : 2020-09-27
     * desc : 紫外线太强了，不宜长时间在户外运动。
     * level : 13
     * name : 运动指数
     * status : 不适宜
     * updatetime : 2020-09-27 11:24:04
     */

    private int code;
    private String day;
    private String desc;
    private String level;
    private String name;
    private String status;
    private String updatetime;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
