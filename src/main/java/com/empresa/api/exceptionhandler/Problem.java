package com.empresa.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Problem {
	
	private Integer status;
	private String type;
	private String title;
	private String detail;
	private String userMessage;
	private LocalDateTime timeStamp = LocalDateTime.now();
	private List<Object> objects;
	
	private Problem() {
		super();
	}

	public static ProblemBuilder builder() {
		
		ProblemBuilder problemBuilder = new Problem().new ProblemBuilder();
		 
		return problemBuilder;
	}
	
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}



	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}



	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}



	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * @return the userMessage
	 */
	public String getUserMessage() {
		return userMessage;
	}


	/**
	 * @return the timeStamp
	 */
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	
	
	/**
	 * @return the fields
	 */
	public List<Object> getObjects() {
		return objects;
	}



	static class Object {
		
		private String name;
		private String userMessage;
		
		public static Object builder() {
			
			Object object = new Object();
			 
			return object;
		}
		
		public Object name(String name) {
			this.name = name;
			return this;
		}
		
		public Object userMessage(String userMessage) {
			this.userMessage = userMessage;
			return this;
		}
		
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @return the userMessage
		 */
		public String getUserMessage() {
			return userMessage;
		}
		
		public Object build() {
			return this;
		}
	}
	

	class ProblemBuilder {
		
		private Problem problem = new Problem();
		
		public ProblemBuilder status(Integer status) {
			problem.status = status;
			return this;
		}
		
		public ProblemBuilder type(String type) {
			problem.type = type;
			return this;
		}
		
		public ProblemBuilder title(String title) {
			problem.title = title;
			return this;
		}
		
		public ProblemBuilder detail(String detail) {
			problem.detail = detail;
			return this;
		}
		
		public ProblemBuilder userMessage(String userMessage) {
			problem.userMessage = userMessage;
			return this;
		}
		
		public ProblemBuilder fields(List<Object> objects) {
			problem.objects = objects;
			return this;
		}
		
		public Problem build() {
			return problem;
		}
	}
}
