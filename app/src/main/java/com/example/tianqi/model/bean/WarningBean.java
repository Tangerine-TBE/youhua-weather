package com.example.tianqi.model.bean;

import java.util.List;

/**
 * @author wujinming QQ:1245074510
 * @name WeatherOne
 * @class name：com.example.tianqi.model.bean
 * @class describe
 * @time 2020/9/28 20:06
 * @class describe
 */
public class WarningBean {


    /**
     * code : 0
     * data : {"alert":[{"content":"广州市气象局于09月01日17时28分发布雷雨大风蓝色预警信号，请注意防御。","infoid":70,"level":"蓝色","name":"雷雨大风","pub_time":"2016-09-01 17:46:06","title":"广东省广州市气象台发布蓝色雷雨大风预警","type":"雷雨大风蓝色"}],"city":{"cityId":285119,"counname":"中国","name":"越秀区","pname":"广东省"}}
     * msg : success
     * rc : {"c":0,"p":"success"}
     */

    private int code;
    private DataBean data;
    private String msg;
    private RcBean rc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RcBean getRc() {
        return rc;
    }

    public void setRc(RcBean rc) {
        this.rc = rc;
    }

    public static class DataBean {
        /**
         * alert : [{"content":"广州市气象局于09月01日17时28分发布雷雨大风蓝色预警信号，请注意防御。","infoid":70,"level":"蓝色","name":"雷雨大风","pub_time":"2016-09-01 17:46:06","title":"广东省广州市气象台发布蓝色雷雨大风预警","type":"雷雨大风蓝色"}]
         * city : {"cityId":285119,"counname":"中国","name":"越秀区","pname":"广东省"}
         */

        private CityBean city;
        private List<AlertBean> alert;

        public CityBean getCity() {
            return city;
        }

        public void setCity(CityBean city) {
            this.city = city;
        }

        public List<AlertBean> getAlert() {
            return alert;
        }

        public void setAlert(List<AlertBean> alert) {
            this.alert = alert;
        }

        public static class CityBean {
            /**
             * cityId : 285119
             * counname : 中国
             * name : 越秀区
             * pname : 广东省
             */

            private int cityId;
            private String counname;
            private String name;
            private String pname;

            public int getCityId() {
                return cityId;
            }

            public void setCityId(int cityId) {
                this.cityId = cityId;
            }

            public String getCounname() {
                return counname;
            }

            public void setCounname(String counname) {
                this.counname = counname;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPname() {
                return pname;
            }

            public void setPname(String pname) {
                this.pname = pname;
            }
        }

        public static class AlertBean {
            /**
             * content : 广州市气象局于09月01日17时28分发布雷雨大风蓝色预警信号，请注意防御。
             * infoid : 70
             * level : 蓝色
             * name : 雷雨大风
             * pub_time : 2016-09-01 17:46:06
             * title : 广东省广州市气象台发布蓝色雷雨大风预警
             * type : 雷雨大风蓝色
             */

            private String content;
            private int infoid;
            private String level;
            private String name;
            private String pub_time;
            private String title;
            private String type;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getInfoid() {
                return infoid;
            }

            public void setInfoid(int infoid) {
                this.infoid = infoid;
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

            public String getPub_time() {
                return pub_time;
            }

            public void setPub_time(String pub_time) {
                this.pub_time = pub_time;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }

    public static class RcBean {
        /**
         * c : 0
         * p : success
         */

        private int c;
        private String p;

        public int getC() {
            return c;
        }

        public void setC(int c) {
            this.c = c;
        }

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }
    }
}
