--> Liste des amis de l'utilisateur <user>
-------------------------------------------
<user>
(SELECT u.* FROM User u WHERE u.mail IN
    (SELECT f1.mail_sender FROM Friendship f1 WHERE f1.mail_receiver = <user>.mail AND f1.status = TRUE)
OR u.mail IN
    (SELECT f2.mail_receiver FROM Friendship f2 WHERE f2.mail_sender = <user>.mail AND f2.status = TRUE))
    
--> Liste des flux des amis de l'utilisateur <user>
----------------------------------------------------
<user>
SELECT DISTINCT sub.stream_url
FROM Subscribe sub
WHERE sub.user_mail IN
    (SELECT u.mail FROM User u WHERE u.mail IN
        (SELECT f1.mail_sender FROM Friendship f1 WHERE f1.mail_receiver = <user>.mail AND f1.status = TRUE)
    OR u.mail IN
        (SELECT f2.mail_receiver FROM Friendship f2 WHERE f2.mail_sender = <user>.mail AND f2.status = TRUE))
    
--> Liste des flux pour lesquels aucun ami de l'utilisateur <user> n'a souscrit
--------------------------------------------------------------------------------
<user>
SELECT *
FROM Stream s
WHERE s.url NOT IN
    (SELECT DISTINCT sub.stream_url
    FROM Subscribe sub
    WHERE sub.user_mail IN
        (SELECT u.mail FROM User u WHERE u.mail IN
            (SELECT f1.mail_sender FROM Friendship f1 WHERE f1.mail_receiver = <user>.mail AND f1.status = TRUE)
        OR u.mail IN
            (SELECT f2.mail_receiver FROM Friendship f2 WHERE f2.mail_sender = <user>.mail AND f2.status = TRUE)))

--> Liste des flux auxquels <user> a souscrit
----------------------------------------------
<user>
SELECT *
FROM Stream s
WHERE s.url IN
    (SELECT sub.stream_url
    FROM Subscribe sub
    WHERE sub.user_mail = <user>.mail)
    
--> Liste des flux pour lesquels l'utilisateur <user> n'a partage aucune publication
-------------------------------------------------------------------------------------
<user>
SELECT *
FROM Stream s
WHERE s.url NOT IN
    (SELECT DISTINCT prop2.stream_url
    FROM Propose prop2
    WHERE prop2.publication_url IN
        (SELECT prop1.publication_url
        FROM Publication pub, Propose prop1
        WHERE pub.url = prop1.publication_url AND prop1.stream_url = <user>.personal_stream_url))
    
    

--> R3 : Liste des flux auxquels <user> a souscrit, auxquels aucun de ses amis n'a souscrit
--------------------------------------------------------------------------------------------
SELECT *
FROM Stream s
WHERE s.url IN
    (SELECT sub.stream_url
    FROM Subscribe sub1
    WHERE sub1.user_mail = <user>.mail)
AND s.url NOT IN
    (SELECT DISTINCT sub.stream_url
    FROM Subscribe sub2
    WHERE sub2.user_mail IN
        (SELECT u.mail FROM User u WHERE u.mail IN
            (SELECT f1.mail_sender FROM Friendship f1 WHERE f1.mail_receiver = <user>.mail AND f1.status = TRUE)
        OR u.mail IN
            (SELECT f2.mail_receiver FROM Friendship f2 WHERE f2.mail_sender = <user>.mail AND f2.status = TRUE)))
AND s.url NOT IN
    (SELECT DISTINCT prop2.stream_url
    FROM Propose prop2
    WHERE prop2.publication_url IN
        (SELECT prop1.publication_url
        FROM Publication pub, Propose prop1
        WHERE pub.url = prop1.publication_url AND prop1.stream_url = <user>.personal_stream_url))
