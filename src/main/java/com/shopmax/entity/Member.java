package com.shopmax.entity;


import org.springframework.security.crypto.password.PasswordEncoder;
import com.querydsl.core.annotations.Generated;
import com.shopmax.constant.Role;
import com.shopmax.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="member")
@Setter
@Getter
@ToString
public class Member extends BaseEntity{
	
	@Id
	@Column(name="member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(unique = true, nullable = false, length = 255) //unique = true (중복된 값 입력 불가)
	private String email;
	
	@Column(nullable = false, length = 255)
	private String name;
	
	@Column(nullable = false, length = 255)
	private String password;
	
	@Column(nullable = false, length = 255)
	private String address;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	//
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		//
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		
		
		Member member = new Member();
		member.setName(memberFormDto.getName());
		member.setEmail(memberFormDto.getEmail());
		member.setAddress(memberFormDto.getAddress());
		member.setPassword(password);
		//member.setRole(Role.ADMIN); //관리자로 가입할때
		member.setRole(Role.USER); //일반 사용자로 가입할때
		
		return member;
	}
	
}
