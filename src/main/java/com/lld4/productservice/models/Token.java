package com.lld4.productservice.models;


import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class Token extends BaseModel {
    private String value;
    private User user;
    private Date expiryDate;
    private Date issuedDate;
}
