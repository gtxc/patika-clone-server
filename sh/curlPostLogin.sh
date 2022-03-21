#!/bin/sh
curl -X POST localhost:8080/api/login/ -d @login.json -H "Content-Type:application/json"
