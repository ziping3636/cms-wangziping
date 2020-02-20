package com.wangziping.domain;

public class Complain {

	private Integer id;
	private String typename;
	private String url;
	private String content;
	private Integer articleId;
	private String picurl;
	private Integer user_id;
	private String created;
	private Article article;
	private User user;

	public Complain() {
		super();
	}

	public Complain(Integer id, String typename, String url, String content, Integer articleId, String picurl,
			Integer user_id, String created, Article article, User user) {
		super();
		this.id = id;
		this.typename = typename;
		this.url = url;
		this.content = content;
		this.articleId = articleId;
		this.picurl = picurl;
		this.user_id = user_id;
		this.created = created;
		this.article = article;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Complain [id=" + id + ", typename=" + typename + ", url=" + url + ", content=" + content
				+ ", articleId=" + articleId + ", picurl=" + picurl + ", user_id=" + user_id + ", created=" + created
				+ ", article=" + article + ", user=" + user + "]";
	}

}
