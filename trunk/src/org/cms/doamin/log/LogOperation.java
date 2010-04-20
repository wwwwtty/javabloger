package org.cms.doamin.log;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * LogOpt entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "log_opt", catalog = "tinycms")
public class LogOperation implements java.io.Serializable {

	// Fields
	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "logID", unique = true, nullable = false, length = 32)
	private String logId;
	
	@Column(name = "opt_user", nullable = false, length = 32)
	private String optUser;
	
	@Column(name = "opt_time", nullable = false, length = 19)
	private Date optTime;
	
	@Column(name = "opt_type", nullable = false, length = 20)
	private String optType;
	
	@Column(name = "opt_memo", length = 100)
	private String optMemo;

	// Constructors

	/** default constructor */
	public LogOperation() {
	}

	/** minimal constructor */
	public LogOperation(String optUser, Timestamp optTime, String optType) {
		this.optUser = optUser;
		this.optTime = optTime;
		this.optType = optType;
	}

	/** full constructor */
	public LogOperation(String optUser, Timestamp optTime, String optType,
			String optMemo) {
		this.optUser = optUser;
		this.optTime = optTime;
		this.optType = optType;
		this.optMemo = optMemo;
	}

	// Property accessors
		public String getLogId() {
		return this.logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

		public String getOptUser() {
		return this.optUser;
	}

	public void setOptUser(String optUser) {
		this.optUser = optUser;
	}

	public Date getOptTime() {
		return this.optTime;
	}

	public void setOptTime(Date optTime) {
		this.optTime = optTime;
	}

	public String getOptType() {
		return this.optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public String getOptMemo() {
		return this.optMemo;
	}

	public void setOptMemo(String optMemo) {
		this.optMemo = optMemo;
	}

}