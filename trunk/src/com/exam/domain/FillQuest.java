package com.exam.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/*
 *@auther heshencao 
 * 2009-8-14 上午03:58:35
 */
@Entity
@DiscriminatorValue("FL")
public class FillQuest extends Quest {

	
}
