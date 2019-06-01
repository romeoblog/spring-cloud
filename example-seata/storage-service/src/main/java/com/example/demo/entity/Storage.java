package com.example.demo.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "storage_tbl")
@DynamicUpdate
@DynamicInsert
public class Storage {

    @Id
    private Long id;
    private String commodityCode;
    private Integer count;

}
