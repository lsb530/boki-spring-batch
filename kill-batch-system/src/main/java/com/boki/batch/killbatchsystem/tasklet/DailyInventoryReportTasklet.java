package com.boki.batch.killbatchsystem.tasklet;

import com.boki.batch.killbatchsystem.entity.ItemStock;
import com.boki.batch.killbatchsystem.repository.InventoryRepository;
import com.boki.batch.killbatchsystem.service.AlarmService;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.List;

/**
 * 일일 재고 현황 알림 Tasklet
 * - 매일 오전 8시에 주요 품목 재고 상태를 점검하고 알림 발송
 */
@Slf4j
public class DailyInventoryReportTasklet implements Tasklet {

    private final AlarmService alarmService;
    private final InventoryRepository inventoryRepository;

    public DailyInventoryReportTasklet(AlarmService alarmService, InventoryRepository inventoryRepository) {
        this.alarmService = alarmService;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public RepeatStatus execute(@Nonnull StepContribution contribution, @Nonnull ChunkContext chunkContext) {
        List<ItemStock> lowStockItems = inventoryRepository.findLowStockItems(10);  // 재고 10개 이하 조회

        if (lowStockItems.isEmpty()) {
            log.info("✅ 모든 품목 재고 안정");
            return RepeatStatus.FINISHED;
        }

        StringBuilder message = new StringBuilder("⚠️ [재고 부족 품목 알림]\n");
        for (ItemStock item : lowStockItems) {
            message.append(String.format("- %s: 재고 %d개\n", item.getItemName(), item.getStock()));
        }

        log.info("📦 재고 부족 리포트 발송");
        alarmService.send(message.toString());
        return RepeatStatus.FINISHED;
    }

}
