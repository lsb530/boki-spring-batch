TRUNCATE TABLE logs;

INSERT INTO logs (message, created_at)
VALUES ('로그 메시지 1', dateadd('DAY', -1, NOW())),
       ('로그 메시지 2', dateadd('DAY', -2, NOW())),
       ('로그 메시지 3', dateadd('DAY', -3, NOW())),
       ('로그 메시지 4', dateadd('DAY', -4, NOW())),
       ('로그 메시지 5', dateadd('DAY', -5, NOW())),
       ('로그 메시지 6', dateadd('DAY', -6, NOW())),
       ('로그 메시지 7', dateadd('DAY', -7, NOW())),
       ('로그 메시지 8', dateadd('DAY', -8, NOW())),
       ('로그 메시지 9', dateadd('DAY', -9, NOW())),
       ('로그 메시지 10', dateadd('DAY', -10, NOW())),
       ('로그 메시지 11', dateadd('DAY', -11, NOW())),
       ('로그 메시지 12', dateadd('DAY', -12, NOW())),
       ('로그 메시지 13', dateadd('DAY', -13, NOW())),
       ('로그 메시지 14', dateadd('DAY', -14, NOW())),
       ('로그 메시지 15', dateadd('DAY', -15, NOW())),
       ('로그 메시지 16', dateadd('DAY', -16, NOW())),
       ('로그 메시지 17', dateadd('DAY', -17, NOW())),
       ('로그 메시지 18', dateadd('DAY', -18, NOW())),
       ('로그 메시지 19', dateadd('DAY', -19, NOW())),
       ('로그 메시지 20', dateadd('DAY', -20, NOW()));
