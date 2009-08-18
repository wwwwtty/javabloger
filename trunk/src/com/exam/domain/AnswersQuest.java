package com.exam.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/*
 *@auther heshencao 
 * 2009-8-14 上午03:59:57
 */
@Entity
@DiscriminatorValue("AN")
public class AnswersQuest extends Quest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
