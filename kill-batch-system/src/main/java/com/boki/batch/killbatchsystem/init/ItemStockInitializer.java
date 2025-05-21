package com.boki.batch.killbatchsystem.init;

import com.boki.batch.killbatchsystem.entity.ItemStock;
import com.boki.batch.killbatchsystem.repository.InventoryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

@RequiredArgsConstructor
@Slf4j
@Component
public class ItemStockInitializer {

    private final InventoryRepository inventoryRepository;

    @PostConstruct
    public void initItemStock() {
        var itemStocks = new ArrayList<ItemStock>();
        for (int i = 0; i < 100; i++) {
            itemStocks.add(new ItemStock(i, "아이템" + i, new Random().nextInt(100)));
        }
        inventoryRepository.saveAll(itemStocks);
        System.out.println(itemStocks);
        log.info("ItemStock 초기 데이터 저장 완료");
    }

}
