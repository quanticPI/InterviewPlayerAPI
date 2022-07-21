# InterviewPlayerAPI

Required Java version: 11

To run the server:
```
java -jar PlayersTask.jar
```

### Functionality

1. List all players (without pagination):
```
http://localhost:8080/players
```

2. Create new player: 
```
curl -v -X POST localhost:8080/players -H 'Content-type:application/json' -d '{"name":"Leo Messi", "country":"Argentina","position":"forward","birth_date":"2022-01-01"}'
```

3. Update existing player:
```
curl -v -X PUT localhost:8080/players/<id of player created in step 2> -H 'Content-type:application/json'-d\
'{"name":"","country":"England","position":"goalkeeper","birth_date":"2022-01-11"}'
```

4. Filter players by `country`

```
http://localhost:8080/players/country/England
```

5. Filter players by `position`

```
http://localhost:8080/players/position/forward
```
