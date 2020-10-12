package com.example.tianqi.model.bean;

import java.util.List;

/**
 * @author wujinming QQ:1245074510
 * @name WeatherOne
 * @class name：com.example.tianqi.model.bean
 * @class describe
 * @time 2020/9/24 10:31
 * @class describe
 */
public class RainWeatherBean {

    /**
     * status : ok
     * api_version : v2.5
     * api_status : active
     * lang : en_US
     * unit : imperial
     * tzshift : -14400
     * timezone : America/New_York
     * server_time : 1600914696
     * location : [40.7128,-74.006]
     * result : {"minutely":{"status":"ok","datasource":"gfs","precipitation_2h":[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],"precipitation":[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],"probability":[0,0,0,0],"description":"No rain in the next hour"},"primary":0,"forecast_keypoint":"No rain in the next hour"}
     */

    private String status;
    private String api_version;
    private String api_status;
    private String lang;
    private String unit;
    private int tzshift;
    private String timezone;
    private int server_time;
    private ResultBean result;
    private List<Double> location;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApi_version() {
        return api_version;
    }

    public void setApi_version(String api_version) {
        this.api_version = api_version;
    }

    public String getApi_status() {
        return api_status;
    }

    public void setApi_status(String api_status) {
        this.api_status = api_status;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getTzshift() {
        return tzshift;
    }

    public void setTzshift(int tzshift) {
        this.tzshift = tzshift;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getServer_time() {
        return server_time;
    }

    public void setServer_time(int server_time) {
        this.server_time = server_time;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public static class ResultBean {
        /**
         * minutely : {"status":"ok","datasource":"gfs","precipitation_2h":[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],"precipitation":[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],"probability":[0,0,0,0],"description":"No rain in the next hour"}
         * primary : 0
         * forecast_keypoint : No rain in the next hour
         */

        private MinutelyBean minutely;
        private int primary;
        private String forecast_keypoint;

        public MinutelyBean getMinutely() {
            return minutely;
        }

        public void setMinutely(MinutelyBean minutely) {
            this.minutely = minutely;
        }

        public int getPrimary() {
            return primary;
        }

        public void setPrimary(int primary) {
            this.primary = primary;
        }

        public String getForecast_keypoint() {
            return forecast_keypoint;
        }

        public void setForecast_keypoint(String forecast_keypoint) {
            this.forecast_keypoint = forecast_keypoint;
        }

        public static class MinutelyBean {
            /**
             * status : ok
             * datasource : gfs
             * precipitation_2h : [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
             * precipitation : [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
             * probability : [0,0,0,0]
             * description : No rain in the next hour
             */

            private String status;
            private String datasource;
            private String description;
            private List<Double> precipitation_2h;
            private List<Double> precipitation;
            private List<Double> probability;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getDatasource() {
                return datasource;
            }

            public void setDatasource(String datasource) {
                this.datasource = datasource;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public List<Double> getPrecipitation_2h() {
                return precipitation_2h;
            }

            public void setPrecipitation_2h(List<Double> precipitation_2h) {
                this.precipitation_2h = precipitation_2h;
            }

            public List<Double> getPrecipitation() {
                return precipitation;
            }

            public void setPrecipitation(List<Double> precipitation) {
                this.precipitation = precipitation;
            }

            public List<Double> getProbability() {
                return probability;
            }

            public void setProbability(List<Double> probability) {
                this.probability = probability;
            }
        }
    }
}
