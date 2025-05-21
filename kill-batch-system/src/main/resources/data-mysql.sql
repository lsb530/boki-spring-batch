TRUNCATE TABLE logs;

INSERT INTO logs (message, created_at)
VALUES ('로그 메시지 1', NOW() - INTERVAL 1 DAY),
       ('로그 메시지 2', NOW() - INTERVAL 2 DAY),
       ('로그 메시지 3', NOW() - INTERVAL 3 DAY),
       ('로그 메시지 4', NOW() - INTERVAL 4 DAY),
       ('로그 메시지 5', NOW() - INTERVAL 5 DAY),
       ('로그 메시지 6', NOW() - INTERVAL 6 DAY),
       ('로그 메시지 7', NOW() - INTERVAL 7 DAY),
       ('로그 메시지 8', NOW() - INTERVAL 8 DAY),
       ('로그 메시지 9', NOW() - INTERVAL 9 DAY),
       ('로그 메시지 10', NOW() - INTERVAL 10 DAY),
       ('로그 메시지 11', NOW() - INTERVAL 11 DAY),
       ('로그 메시지 12', NOW() - INTERVAL 12 DAY),
       ('로그 메시지 13', NOW() - INTERVAL 13 DAY),
       ('로그 메시지 14', NOW() - INTERVAL 14 DAY),
       ('로그 메시지 15', NOW() - INTERVAL 15 DAY),
       ('로그 메시지 16', NOW() - INTERVAL 16 DAY),
       ('로그 메시지 17', NOW() - INTERVAL 17 DAY),
       ('로그 메시지 18', NOW() - INTERVAL 18 DAY),
       ('로그 메시지 19', NOW() - INTERVAL 19 DAY),
       ('로그 메시지 20', NOW() - INTERVAL 20 DAY);
