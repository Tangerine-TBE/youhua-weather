package com.example.tianqi.model.bean;

public class LoginBean {

    /**
     * ret : 200
     * msg : OK
     * data : {"id":103145,"type":0,"mobile":"13207748886","openid":"","unique":"","password":"e10adc3949ba59abbe56e057f20f883e","isforbid":0,"forbidtime":"2020-07-07 09:55:28","forbidreason":"0","logincount":0,"addver":"","addplat":0,"adduuid":"","addtime":"2020-07-07 09:55:28","addip":"116.24.67.69","lastver":"","lastplat":"","lastuuid":"","lasttime":"2020-07-07 09:55:28","lastip":"","activetime":"2020-07-07 09:55:28","activedays":0,"channelid":"","noticeid":0,"offlinemsg":0,"uuidfirst":"","nickname":"","checkpass":"","amountmoney":"","vip":0,"vipexpiretime":"2020-07-07 09:55:28","goldcoin":0,"credit":0,"star":0,"rich":0,"charm":0,"invitation":0,"medals":"","gifts":"","callprice":0,"tv":0,"report":0,"concern":0,"concerned":0,"cdrcount":0,"cdrtime":0,"usertype":0,"partner":"","partnertime":"2016-09-09 01:00:00","partnerlevel":0,"chargecount":0,"tvreceivecall":0,"smallcash":1,"chargeold":0,"virginid":0,"videoprice":10,"view":0}
     */

    private int ret;
    private String msg;
    private DataBean data;

    @Override
    public String toString() {
        return "LoginBean{" +
                "ret=" + ret +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 103145
         * type : 0
         * mobile : 13207748886
         * openid :
         * unique :
         * password : e10adc3949ba59abbe56e057f20f883e
         * isforbid : 0
         * forbidtime : 2020-07-07 09:55:28
         * forbidreason : 0
         * logincount : 0
         * addver :
         * addplat : 0
         * adduuid :
         * addtime : 2020-07-07 09:55:28
         * addip : 116.24.67.69
         * lastver :
         * lastplat :
         * lastuuid :
         * lasttime : 2020-07-07 09:55:28
         * lastip :
         * activetime : 2020-07-07 09:55:28
         * activedays : 0
         * channelid :
         * noticeid : 0
         * offlinemsg : 0
         * uuidfirst :
         * nickname :
         * checkpass :
         * amountmoney :
         * vip : 0
         * vipexpiretime : 2020-07-07 09:55:28
         * goldcoin : 0
         * credit : 0
         * star : 0
         * rich : 0
         * charm : 0
         * invitation : 0
         * medals :
         * gifts :
         * callprice : 0
         * tv : 0
         * report : 0
         * concern : 0
         * concerned : 0
         * cdrcount : 0
         * cdrtime : 0
         * usertype : 0
         * partner :
         * partnertime : 2016-09-09 01:00:00
         * partnerlevel : 0
         * chargecount : 0
         * tvreceivecall : 0
         * smallcash : 1
         * chargeold : 0
         * virginid : 0
         * videoprice : 10
         * view : 0
         */

        private int id;
        private int type;
        private String mobile;
        private String openid;
        private String unique;
        private String password;
        private int isforbid;
        private String forbidtime;
        private String forbidreason;
        private int logincount;
        private String addver;
        private int addplat;
        private String adduuid;
        private String addtime;
        private String addip;
        private String lastver;
        private String lastplat;
        private String lastuuid;
        private String lasttime;
        private String lastip;
        private String activetime;
        private int activedays;
        private String channelid;
        private int noticeid;
        private int offlinemsg;
        private String uuidfirst;
        private String nickname;
        private String checkpass;
        private String amountmoney;
        private int vip;
        private String vipexpiretime;
        private int goldcoin;
        private int credit;
        private int star;
        private int rich;
        private int charm;
        private int invitation;
        private String medals;
        private String gifts;
        private int callprice;
        private int tv;
        private int report;
        private int concern;
        private int concerned;
        private int cdrcount;
        private int cdrtime;
        private int usertype;
        private String partner;
        private String partnertime;
        private int partnerlevel;
        private int chargecount;
        private int tvreceivecall;
        private int smallcash;
        private int chargeold;
        private int virginid;
        private int videoprice;
        private int view;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getUnique() {
            return unique;
        }

        public void setUnique(String unique) {
            this.unique = unique;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getIsforbid() {
            return isforbid;
        }

        public void setIsforbid(int isforbid) {
            this.isforbid = isforbid;
        }

        public String getForbidtime() {
            return forbidtime;
        }

        public void setForbidtime(String forbidtime) {
            this.forbidtime = forbidtime;
        }

        public String getForbidreason() {
            return forbidreason;
        }

        public void setForbidreason(String forbidreason) {
            this.forbidreason = forbidreason;
        }

        public int getLogincount() {
            return logincount;
        }

        public void setLogincount(int logincount) {
            this.logincount = logincount;
        }

        public String getAddver() {
            return addver;
        }

        public void setAddver(String addver) {
            this.addver = addver;
        }

        public int getAddplat() {
            return addplat;
        }

        public void setAddplat(int addplat) {
            this.addplat = addplat;
        }

        public String getAdduuid() {
            return adduuid;
        }

        public void setAdduuid(String adduuid) {
            this.adduuid = adduuid;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getAddip() {
            return addip;
        }

        public void setAddip(String addip) {
            this.addip = addip;
        }

        public String getLastver() {
            return lastver;
        }

        public void setLastver(String lastver) {
            this.lastver = lastver;
        }

        public String getLastplat() {
            return lastplat;
        }

        public void setLastplat(String lastplat) {
            this.lastplat = lastplat;
        }

        public String getLastuuid() {
            return lastuuid;
        }

        public void setLastuuid(String lastuuid) {
            this.lastuuid = lastuuid;
        }

        public String getLasttime() {
            return lasttime;
        }

        public void setLasttime(String lasttime) {
            this.lasttime = lasttime;
        }

        public String getLastip() {
            return lastip;
        }

        public void setLastip(String lastip) {
            this.lastip = lastip;
        }

        public String getActivetime() {
            return activetime;
        }

        public void setActivetime(String activetime) {
            this.activetime = activetime;
        }

        public int getActivedays() {
            return activedays;
        }

        public void setActivedays(int activedays) {
            this.activedays = activedays;
        }

        public String getChannelid() {
            return channelid;
        }

        public void setChannelid(String channelid) {
            this.channelid = channelid;
        }

        public int getNoticeid() {
            return noticeid;
        }

        public void setNoticeid(int noticeid) {
            this.noticeid = noticeid;
        }

        public int getOfflinemsg() {
            return offlinemsg;
        }

        public void setOfflinemsg(int offlinemsg) {
            this.offlinemsg = offlinemsg;
        }

        public String getUuidfirst() {
            return uuidfirst;
        }

        public void setUuidfirst(String uuidfirst) {
            this.uuidfirst = uuidfirst;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getCheckpass() {
            return checkpass;
        }

        public void setCheckpass(String checkpass) {
            this.checkpass = checkpass;
        }

        public String getAmountmoney() {
            return amountmoney;
        }

        public void setAmountmoney(String amountmoney) {
            this.amountmoney = amountmoney;
        }

        public int getVip() {
            return vip;
        }

        public void setVip(int vip) {
            this.vip = vip;
        }

        public String getVipexpiretime() {
            return vipexpiretime;
        }

        public void setVipexpiretime(String vipexpiretime) {
            this.vipexpiretime = vipexpiretime;
        }

        public int getGoldcoin() {
            return goldcoin;
        }

        public void setGoldcoin(int goldcoin) {
            this.goldcoin = goldcoin;
        }

        public int getCredit() {
            return credit;
        }

        public void setCredit(int credit) {
            this.credit = credit;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public int getRich() {
            return rich;
        }

        public void setRich(int rich) {
            this.rich = rich;
        }

        public int getCharm() {
            return charm;
        }

        public void setCharm(int charm) {
            this.charm = charm;
        }

        public int getInvitation() {
            return invitation;
        }

        public void setInvitation(int invitation) {
            this.invitation = invitation;
        }

        public String getMedals() {
            return medals;
        }

        public void setMedals(String medals) {
            this.medals = medals;
        }

        public String getGifts() {
            return gifts;
        }

        public void setGifts(String gifts) {
            this.gifts = gifts;
        }

        public int getCallprice() {
            return callprice;
        }

        public void setCallprice(int callprice) {
            this.callprice = callprice;
        }

        public int getTv() {
            return tv;
        }

        public void setTv(int tv) {
            this.tv = tv;
        }

        public int getReport() {
            return report;
        }

        public void setReport(int report) {
            this.report = report;
        }

        public int getConcern() {
            return concern;
        }

        public void setConcern(int concern) {
            this.concern = concern;
        }

        public int getConcerned() {
            return concerned;
        }

        public void setConcerned(int concerned) {
            this.concerned = concerned;
        }

        public int getCdrcount() {
            return cdrcount;
        }

        public void setCdrcount(int cdrcount) {
            this.cdrcount = cdrcount;
        }

        public int getCdrtime() {
            return cdrtime;
        }

        public void setCdrtime(int cdrtime) {
            this.cdrtime = cdrtime;
        }

        public int getUsertype() {
            return usertype;
        }

        public void setUsertype(int usertype) {
            this.usertype = usertype;
        }

        public String getPartner() {
            return partner;
        }

        public void setPartner(String partner) {
            this.partner = partner;
        }

        public String getPartnertime() {
            return partnertime;
        }

        public void setPartnertime(String partnertime) {
            this.partnertime = partnertime;
        }

        public int getPartnerlevel() {
            return partnerlevel;
        }

        public void setPartnerlevel(int partnerlevel) {
            this.partnerlevel = partnerlevel;
        }

        public int getChargecount() {
            return chargecount;
        }

        public void setChargecount(int chargecount) {
            this.chargecount = chargecount;
        }

        public int getTvreceivecall() {
            return tvreceivecall;
        }

        public void setTvreceivecall(int tvreceivecall) {
            this.tvreceivecall = tvreceivecall;
        }

        public int getSmallcash() {
            return smallcash;
        }

        public void setSmallcash(int smallcash) {
            this.smallcash = smallcash;
        }

        public int getChargeold() {
            return chargeold;
        }

        public void setChargeold(int chargeold) {
            this.chargeold = chargeold;
        }

        public int getVirginid() {
            return virginid;
        }

        public void setVirginid(int virginid) {
            this.virginid = virginid;
        }

        public int getVideoprice() {
            return videoprice;
        }

        public void setVideoprice(int videoprice) {
            this.videoprice = videoprice;
        }

        public int getView() {
            return view;
        }

        public void setView(int view) {
            this.view = view;
        }
    }
}
