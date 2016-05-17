package com.example.myweb.bean;

public class UserChatCommand {
	private String name;
	  private String chatContent;
	  private String coordinationId;
	  private String sendTime;

	  public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getName() {
	    return name;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }

	  public String getChatContent() {
	    return chatContent;
	  }

	  public void setChatContent(String chatContent) {
	    this.chatContent = chatContent;
	  }

	  public String getCoordinationId() {
	    return coordinationId;
	  }

	  public void setCoordinationId(String coordinationId) {
	    this.coordinationId = coordinationId;
	  }

	  @Override
	  public String toString() {
	    return "UserChatCommand{" +
	        "name='" + name + '\'' +
	        ", chatContent='" + chatContent + '\'' +
	        ", coordinationId='" + coordinationId + '\'' +
	        '}';
	  }
}
