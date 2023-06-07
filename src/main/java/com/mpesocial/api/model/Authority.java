package com.mpesocial.api.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "authorities")
@SequenceGenerator(name = "authority_seq_gen", sequenceName = "authority_seq", initialValue = 10, allocationSize = 1)
public class Authority implements GrantedAuthority {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_seq_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "authority", unique = true, nullable = false)
    private String authority;

    @ManyToMany(mappedBy = "authorities", cascade = CascadeType.ALL)
    private Collection<MpeUser> users;

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                '}';
    }
    
}
