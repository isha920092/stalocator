package com.stalocator.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp 
	@Column(name="creation_date")
	private LocalDate creationDate;
	
	@UpdateTimestamp 
	@Column(name="updation_ts")
	private LocalDateTime updationTimeStamp;
	
	@Enumerated(EnumType.STRING)
    @Column
    //(nullable = false)
    private Role role;
	
	@Column(name="token")
	private String token;
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public void setUpdationTimeStamp(LocalDateTime updationTimeStamp) {
		this.updationTimeStamp = updationTimeStamp;
	}

	@Override
	public String toString() {
		return "BaseEntity [id=" + id + ", creationDate=" + creationDate + ", updationTimeStamp=" + updationTimeStamp
				+ "]";
	}
	
}
