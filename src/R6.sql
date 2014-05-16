
<user>
SELECT u1.mail,
    ((SELECT COUNT(*) FROM `Read` r1 WHERE r1.user_mail = u1.mail) / (SELECT DATEDIFF(CURDATE(), u1.date))) AS publications_read_per_day,
    (SELECT COUNT(*) FROM User u2 WHERE u2.mail IN (SELECT friend3.mail_receiver FROM Friendship friend3 WHERE friend3.mail_sender = u1.mail AND friend3.status = TRUE) OR u2.mail IN (SELECT friend4.mail_sender FROM Friendship friend4 WHERE friend4.mail_receiver = u1.mail AND friend4.status = TRUE)) AS nb_friends
FROM User u1
WHERE 
u1.mail IN (SELECT friend1.mail_receiver FROM Friendship friend1 WHERE friend1.mail_sender = <user>.mail AND friend1.status = TRUE)
OR u1.mail IN (SELECT friend2.mail_sender FROM Friendship friend2 WHERE friend2.mail_receiver = <user>.mail AND friend2.status = TRUE)
ORDER BY publications_read_per_day