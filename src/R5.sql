<user>
SELECT s1.url, 
    @a:=(SELECT COUNT(*) FROM `Read` r2, Propose prop1 WHERE r2.publication_url = prop1.publication_url AND r2.user_mail = <user>.mail AND prop1.stream_url = s1.url AND DATE_SUB(CURDATE(),INTERVAL 30 DAY) <= r2.date) AS publications_read,
    @b:=(SELECT COUNT(*) FROM Propose prop2, Propose prop3, Publication pub1 WHERE  pub1.url = prop2.publication_url AND pub1.url = prop3.publication_url AND prop2.publication_url = prop3.publication_url AND prop2.stream_url = <user>.personal_stream_url AND prop3.stream_url = s1.url AND DATE_SUB(CURDATE(),INTERVAL 30 DAY) <= pub1.date) AS publications_shared,
    (@b / @a * 100)
FROM Stream s1, Subscribe sub1
WHERE sub1.stream_url = s1.url AND sub1.user_mail = <user>.mail
ORDER BY publications_shared

