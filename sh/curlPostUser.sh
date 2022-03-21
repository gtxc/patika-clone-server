#!/bin/sh
curl -X POST localhost:8080/api/users/ -d @user.json -H "Content-Type:application/json"
