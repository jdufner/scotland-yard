Cyper-Queries

````
match p=shortestPath((n:MRX)-[*1..8]-(m:DETEKTIV)) return distinct n.number, m.number, length(p)
````

