package com.shanqb.myTool.bean.video;

import com.shanqb.myTool.bean.LoginResponse;

public class VideoBean {

    /**
     * id : 245
     * videoCode : 20201012230009088
     * type : 20200209145534301
     * typeName : 推荐
     * title : 太原汇达摩登小区：推迟交房近一年，为何迟迟不能交房？都市110
     * description : 1
     * image : /images/upload/video/202010/sm/20201012230003003664.jpg
     * filePath : /userfiles/images/upload/video/202010/20201012225958238622.mp4
     * keywords : null
     * weight : 0
     * weightDate : null
     * thumbsUp : 0
     * hits : 976
     * relay : 0
     * bgMusic : null
     * lengthTime : 03:56
     * comment : null
     * state : 0
     * createBy : 1000001
     * createTime : 2020-10-12 15:00:09
     * updateTime : 1602514876000
     * updateBy : null
     * mer : {"id":null,"loginCode":null,"loginPwd":null,"merCode":null,"appId":null,"openId":null,"description":null,"merName":"视频收集厂","merPhoto":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJiaz1BzWBUicdNWoWSbRDuB9zB1sZfpwcFUl6xicC98x5osKTwFMPUtvyJR62UDr3scAygH8hFc03wQ/132","merLevel":null,"merSex":null,"merPhone":null,"merEmail":null,"realName":null,"idCard":null,"addrCode":null,"merAddr":null,"loginIp":null,"loginIpInfo":null,"merDouzi":0,"merTimes":0,"state":0,"isSubscribe":0,"createTime":null,"updateTime":null,"lastLook":null,"imei":null,"mobileModel":null,"allAmt":null,"totalAmt":null,"txAmt":null,"aliPay":null,"aliPayName":null,"isOld":null,"oldUse":null,"parentCode":null,"newLevel":0,"seti":null}
     * adv : null
     */

    private int id;
    private String videoCode;
    private String type;
    private String typeName;
    private String title;
    private String description;
    private String image;
    private String filePath;
    private Object keywords;
    private int weight;
    private Object weightDate;
    private int thumbsUp;
    private int hits;
    private int relay;
    private Object bgMusic;
    private String lengthTime;
    private Object comment;
    private int state;
    private String createBy;
    private String createTime;
    private long updateTime;
    private Object updateBy;
    private LoginResponse.DataBean mer;
    private Object adv;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideoCode() {
        return videoCode;
    }

    public void setVideoCode(String videoCode) {
        this.videoCode = videoCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Object getKeywords() {
        return keywords;
    }

    public void setKeywords(Object keywords) {
        this.keywords = keywords;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Object getWeightDate() {
        return weightDate;
    }

    public void setWeightDate(Object weightDate) {
        this.weightDate = weightDate;
    }

    public int getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(int thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getRelay() {
        return relay;
    }

    public void setRelay(int relay) {
        this.relay = relay;
    }

    public Object getBgMusic() {
        return bgMusic;
    }

    public void setBgMusic(Object bgMusic) {
        this.bgMusic = bgMusic;
    }

    public String getLengthTime() {
        return lengthTime;
    }

    public void setLengthTime(String lengthTime) {
        this.lengthTime = lengthTime;
    }

    public Object getComment() {
        return comment;
    }

    public void setComment(Object comment) {
        this.comment = comment;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public Object getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Object updateBy) {
        this.updateBy = updateBy;
    }

    public LoginResponse.DataBean getMer() {
        return mer;
    }

    public void setMer(LoginResponse.DataBean mer) {
        this.mer = mer;
    }

    public Object getAdv() {
        return adv;
    }

    public void setAdv(Object adv) {
        this.adv = adv;
    }

}
