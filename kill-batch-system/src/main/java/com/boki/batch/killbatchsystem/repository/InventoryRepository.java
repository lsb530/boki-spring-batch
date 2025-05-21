package com.boki.batch.killbatchsystem.repository;

import com.boki.batch.killbatchsystem.entity.ItemStock;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InventoryRepository {

    private List<ItemStock> items;

    @PostConstruct
    public void init() {
        this.items = new ArrayList<>();
    }

    public void save(ItemStock itemStock) {
        items.add(itemStock);
    }

    public void saveAll(List<ItemStock> itemStocks) {
        items.addAll(itemStocks);
    }

    public List<ItemStock> findLowStockItems(int requireStock) {
        return items.stream().filter(it -> it.getStock() < requireStock).toList();
    }

}
