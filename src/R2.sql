--> Liste des flux auxquels <user> a souscrit
-----------------------------------------------
<user>
SELECT *
FROM Stream s1
WHERE s1.url IN
    (SELECT sub1.stream_url FROM Subscribe sub1 WHERE sub1.user_mail = <user>.mail)
    
--> Liste des utilisateurs qui ont souscrit à au moins deux flux auxquels <user> a souscrit
--------------------------------------------------------------------------------------------
<user>
SELECT u1.*
FROM User u1, Stream s2, Subscribe sub2
WHERE sub2.user_mail != <user>.mail AND sub2.user_mail = u1.mail AND sub2.stream_url = s2.url AND sub2.stream_url IN    
    (SELECT sub1.stream_url FROM Subscribe sub1 WHERE sub1.user_mail = <user>.mail)
GROUP BY sub2.user_mail
HAVING COUNT(*) >= 2

--> R2 : La liste des flux auxquels a souscrit au moins un utilisateur qui a souscrit `a au moins deux flux auxquel X a souscrit

<user>
SELECT DISTINCT s3.*
FROM Subscribe sub3, Stream s3
WHERE sub3.stream_url = s3.url AND sub3.user_mail IN
    (SELECT u1.mail
    FROM User u1, Stream s2, Subscribe sub2
    WHERE sub2.user_mail != <user>.mail AND sub2.user_mail = u1.mail AND sub2.stream_url = s2.url AND sub2.stream_url IN    
        (SELECT sub1.stream_url FROM Subscribe sub1 WHERE sub1.user_mail = <user>.mail)
    GROUP BY sub2.user_mail
    HAVING COUNT(*) >= 2)
