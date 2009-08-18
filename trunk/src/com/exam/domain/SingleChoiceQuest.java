package com.exam.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/*
 *@auther heshencao 
 * 2009-8-14 上午03:53:01
 */
@Entity
@DiscriminatorValue("SC")
public class SingleChoiceQuest extends Quest {

	
}
