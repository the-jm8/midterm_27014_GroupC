@echo off
echo Testing Football Team Performance Tracking System...
echo.

echo 1. Testing Location endpoints...
curl -s -X GET "http://localhost:8080/api/locations" -H "Content-Type: application/json"
echo.

echo 2. Testing Team endpoints...
curl -s -X GET "http://localhost:8080/api/teams" -H "Content-Type: application/json"
echo.

echo 3. Testing Player endpoints...
curl -s -X GET "http://localhost:8080/api/players" -H "Content-Type: application/json"
echo.

echo 4. Testing existsBy functionality...
curl -s -X GET "http://localhost:8080/api/locations/exists/province/Gauteng" -H "Content-Type: application/json"
echo.

echo 5. Testing province-based player retrieval...
curl -s -X GET "http://localhost:8080/api/players/province/Gauteng" -H "Content-Type: application/json"
echo.

echo 6. Testing sorting and pagination...
curl -s -X GET "http://localhost:8080/api/teams/province/Gauteng/paged?page=0&size=5&sortField=name&sortDirection=ASC" -H "Content-Type: application/json"
echo.

echo Testing complete!
pause