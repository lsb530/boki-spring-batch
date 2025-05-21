package com.boki.batch.killbatchsystem.tasklet;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DeleteOldFilesTasklet implements Tasklet {

    private final Path directory;
    private final long thresholdMillis;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * @param directoryPath 삭제 대상 디렉토리 경로
     * @param duration      경과 기준 값 (예: 3)
     * @param unit          TimeUnit 단위 (예: TimeUnit. DAYS, HOURS, MINUTES, SECONDS 등)
     */
    public DeleteOldFilesTasklet(String directoryPath, long duration, TimeUnit unit) {
        this.directory = Paths.get(directoryPath);
        this.thresholdMillis = unit.toMillis(duration);
    }

    @Override
    public RepeatStatus execute(@Nonnull StepContribution contribution, @Nonnull ChunkContext chunkContext) throws Exception {
        long cutoffTime = System.currentTimeMillis() - thresholdMillis;

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path filePath : stream) {
                // 파일이 아니거나 디렉토리면 스킵
                if (!Files.isRegularFile(filePath)) {
                    continue;
                }

                FileTime fileTime = Files.getLastModifiedTime(filePath);
                Instant instant = fileTime.toInstant();
                LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                String formatted = localDateTime.format(FORMATTER);

                log.debug("filePath = {}", filePath);
                log.debug("lastModified(로컬) = {}", formatted);

                if (fileTime.toMillis() < cutoffTime) {
                    try {
                        Files.delete(filePath);
                        log.info("🔥 파일 삭제: {}", filePath.getFileName());
                    } catch (IOException e) {
                        log.warn("⚠️ 파일 삭제 실패: {} – {}", filePath.getFileName(), e.getMessage());
                    }
                }
            }
        } catch (DirectoryIteratorException e) {
            log.error("디렉토리 순회 중 오류 발생: {}", e.getMessage());
        }

        return RepeatStatus.FINISHED;
    }

}