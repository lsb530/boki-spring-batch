package com.boki.batch.killbatchsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class ItemStock {

    private Integer id;
    private String itemName;
    private Integer stock;

}
