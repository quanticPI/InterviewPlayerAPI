# InterviewPlayerAPI

Required Java version: 11

To run the server:
```
java -jar PlayersTask-0.0.1.jar
```

### Functionality

1. List all players (without pagination):
```
curl -v http://localhost:8080/players
```

2. Create new player:
- NOTE: If name/country are empty returns BadRequest response.
```
curl -v -X POST localhost:8080/players -H 'Content-type:application/json' -d '{"name":"Leo Messi", "country":"Argentina","position":"forward","birth_date":"2022-01-01"}'
```


3. Update existing player. In this particular case `position` is updated. The name is kept as the original resource as it is empty and ignored:
```
curl -v -X PUT localhost:8080/players/<id_of_player_created_in_step_2> -H 'Content-type:application/json' -d\
'{"name":"","country":"Argentina","position":"goalkeeper","birth_date":"2022-01-11"}'
```

4. Filter players by `country`

```
http://localhost:8080/players/country/England
```

5. Filter players by `position`

```
http://localhost:8080/players/position/forward
```
