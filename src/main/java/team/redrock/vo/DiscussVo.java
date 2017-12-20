package team.redrock.vo;

import team.redrock.bean.Discuss;

import java.util.Date;
import java.util.List;

public class DiscussVo {
    private int id;
    private String author_id;
    private String nick_name;
    private String head_url;
    private String title;
    private String content;
    private String pic_name;
    private String pic_thumb;
    private String tag;
    private int reply_count;
    private int like_count;
    private Date update_time;
    private Date create_time;
    private boolean mine;

    public DiscussVo(Discuss discuss) {
        id = discuss.getId();
        author_id = discuss.getAuthor_id();
        nick_name = discuss.getNick_name();
        head_url = discuss.getHead_url();
        title = discuss.getTitle();
        content = discuss.getContent();
        pic_name = discuss.getPic_name();
        pic_thumb = discuss.getPic_thumb();
        tag = discuss.getTag();
        reply_count = discuss.getReply_count();
        like_count = discuss.getLike_count();
        update_time = discuss.getUpdate_time();
        create_time = discuss.getCreate_time();
    }

    private List<Discuss> repliesList;

    public List<Discuss> getRepliesList() {
        return repliesList;
    }

    public void setRepliesList(List<Discuss> repliesList) {
        this.repliesList = repliesList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic_name() {
        return pic_name;
    }

    public void setPic_name(String pic_name) {
        this.pic_name = pic_name;
    }

    public String getPic_thumb() {
        return pic_thumb;
    }

    public void setPic_thumb(String pic_thumb) {
        this.pic_thumb = pic_thumb;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getReply_count() {
        return reply_count;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }
}
