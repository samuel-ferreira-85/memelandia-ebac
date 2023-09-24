package com.samuel.meme.exceptions.handler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ApiError {
	
	private Integer status;
	private String type;
	private String title;
	private String detail;
	private LocalDateTime timestamp;
	private List<Field> fields;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
	public static class Field {
		private String name;
		private String userMessage;
		
		public Field() {
		}	
		
		public Field(String name, String userMessage) {
			super();
			this.name = name;
			this.userMessage = userMessage;
		}

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getUserMessage() {
			return userMessage;
		}
		public void setUserMessage(String userMessage) {
			this.userMessage = userMessage;
		}	
		
	}
	
}
