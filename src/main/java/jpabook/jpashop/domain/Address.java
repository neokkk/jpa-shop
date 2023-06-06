package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable // 내장 가능 타입
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String city;

    private String street;

    private String zipcode;
}
