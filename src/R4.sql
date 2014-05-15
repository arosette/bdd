--> R4 : La liste des utilisateurs qui ont 
-- partagé au moins 3 publications que X a partagé
---------------------------------------------------
<userX>
SELECT DISTINCT u.*
FROM User u, Propose prop1, Propose prop2
WHERE prop1.stream_url = <user>.personal_stream_url
AND prop2.stream_url != <user>.personal_stream_url
AND prop2.stream_url = u.personal_stream_url
AND prop1.publication_url = prop2.publication_url
GROUP BY u.mail
HAVING count(*) >= 3;