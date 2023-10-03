SELECT Nom From Ville Where insee = '1293';

alter table ville add primary key(insee);

SELECT d.nom 
FROM departement d
JOIN Ville v ON d.id = v.dep 
WHERE insee = '1293';


SELECT d.nom 
FROM departement d
JOIN Ville v ON d.id = v.dep;


SELECT /*+ use_nl(d v) */ d.nom 
FROM departement d
JOIN ville v ON d.id = v.dep;

create index idx_dep_ville on ville (dep);