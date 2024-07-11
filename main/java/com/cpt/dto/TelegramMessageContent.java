package com.cpt.dto;

import com.google.gson.annotations.SerializedName;

/**
 * 단순 한 줄 메세지 뿐만 아니라 복잡한 메세지도 받기위해 따로 작성해놓음
 */
public class TelegramMessageContent {
	
	@SerializedName("chat_id")
	  private String chatId;
	  private String text;

	  public TelegramMessageContent() {
	  }

	  public TelegramMessageContent(String chatId, String text) {
	    this.chatId = chatId;
	    this.text = text;
	  }

	  public String getChatId() {
	    return chatId;
	  }

	  public void setChatId(String chatId) {
	    this.chatId = chatId;
	  }

	  public String getText() {
	    return text;
	  }

	  public void setText(String text) {
	    this.text = text;
	  }
}
