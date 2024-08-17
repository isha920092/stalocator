package com.stalocator.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "owners")
public class Owner extends User{

	@Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Role role;
    
    @Column(nullable = false)
    private boolean approved;
        
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hostel> hostels;
    
//    public void setRole() {
//        this.role = Role.OWNER;
//    }
    
//    public void addHostel(Hostel hostel) {
//    	hostels.add(hostel);
//    	hostel.setOwner(this);
//    }
//
//    public void removeHostel(Hostel hostel) {
//    	hostels.remove(hostel);
//    	hostel.setOwner(null);
//    }
    
}
