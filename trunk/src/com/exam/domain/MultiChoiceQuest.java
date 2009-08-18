package com.exam.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/*
 *@auther heshencao 
 * 2009-8-14 上午03:56:49
 */
@Entity
@DiscriminatorValue("MC")
public class MultiChoiceQuest extends Quest {


	
}
