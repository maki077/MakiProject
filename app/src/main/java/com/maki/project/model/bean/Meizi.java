package com.maki.project.model.bean;

/**
 * 妹子Bean
 * 数据格式见：http://gank.io/api/data/all/20/2
 */
public class Meizi {

    /**
     * _id : 57a15d05421aa91e2606476e
     * createdAt : 2016-08-03T10:55:01.123Z
     * desc : 一组漂亮的 TextField 交互特效
     * publishedAt : 2016-08-03T11:12:47.159Z
     * source : chrome
     * type : iOS
     * url : https://github.com/raulriera/TextFieldEffects
     * used : true
     * who : 代码家
     */
    private String _id;
    private String createdAt;
    private String desc; //干货内容的描述
    private String publishedAt;
    private String source;
    private String type; //干货类型，如Android，iOS，福利等
    private String url; //链接地址
    private boolean used;
    private String who; //作者

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
