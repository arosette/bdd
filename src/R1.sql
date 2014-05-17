--> Liste toutes les amitiés existantes 
----------------------------------------
SELECT DISTINCT f.mail_sender, f.mail_receiver FROM User u, Friendship f 
WHERE f.Status = TRUE AND (mail_sender = u.mail OR mail_receiver = u.mail)

--> R1 Liste tous les utilisateurs qui ont au plus 2 amis
------------------------------------------------------------
SELECT u.surname, COUNT(*)
FROM User u LEFT JOIN (SELECT * FROM Friendship f1 WHERE f1.status = TRUE) AS f2 
ON f2.mail_sender = u.mail OR f2.mail_receiver = u.mail
GROUP BY u.mail
HAVING COUNT(*) < 3;