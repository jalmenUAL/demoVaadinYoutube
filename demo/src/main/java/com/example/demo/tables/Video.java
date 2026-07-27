/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: jalmen(University of Almeria)
 * License Type: Academic
 */
package com.example.demo.tables;

import java.io.Serializable;
import jakarta.persistence.*;
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="Video")
public class Video implements Serializable {
	public Video() {
	}
	
	@Column(name="Id", nullable=false, length=10)	
	@Id	
	@GeneratedValue(generator="COM_EXAMPLE_DEMO_DOMAIN_VIDEO_ID_GENERATOR")	
	@org.hibernate.annotations.GenericGenerator(name="COM_EXAMPLE_DEMO_DOMAIN_VIDEO_ID_GENERATOR", strategy="native")	
	private int id;
	
	@ManyToOne(targetEntity=com.example.demo.tables.Youtuber.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns(value={ @JoinColumn(name="RegistradoLogin", referencedColumnName="Login", nullable=false) }, foreignKey=@ForeignKey(name="FKVideo862578"))	
	private com.example.demo.tables.Youtuber es_de;
	
	@Column(name="Titulo", nullable=true, length=255)	
	private String titulo;
	
	@Column(name="Url", nullable=true, length=255)	
	private String url;
	
	@Column(name="Fecha", nullable=true)	
	@Temporal(TemporalType.DATE)	
	private java.util.Date fecha;
	
	@OneToMany(mappedBy="sobre", targetEntity=com.example.demo.tables.Comentario.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.FALSE)	
	private java.util.Set tiene_comentarios = new java.util.HashSet();
	
	@ManyToMany(mappedBy="le_gusta", targetEntity=com.example.demo.tables.Youtuber.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.FALSE)	
	private java.util.Set le_gusta_a = new java.util.HashSet();
	
	public void setTitulo(String value) {
		this.titulo = value;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setUrl(String value) {
		this.url = value;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setFecha(java.util.Date value) {
		this.fecha = value;
	}
	
	public java.util.Date getFecha() {
		return fecha;
	}
	
	private void setId(int value) {
		this.id = value;
	}
	
	public int getId() {
		return id;
	}
	
	public int getORMID() {
		return getId();
	}
	
	public void setEs_de(com.example.demo.tables.Youtuber value) {
		this.es_de = value;
	}
	
	public com.example.demo.tables.Youtuber getEs_de() {
		return es_de;
	}
	
	public void setTiene_comentarios(java.util.Set value) {
		this.tiene_comentarios = value;
	}
	
	public java.util.Set getTiene_comentarios() {
		return tiene_comentarios;
	}
	
	
	public void setLe_gusta_a(java.util.Set value) {
		this.le_gusta_a = value;
	}
	
	public java.util.Set getLe_gusta_a() {
		return le_gusta_a;
	}
	
	
	public String toString() {
		return String.valueOf(getId());
	}
	
}
