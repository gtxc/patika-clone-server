#!/bin/sh
curl -X POST localhost:8080/api/auth/signin/ -d @userSignin.json -H "Content-Type:application/json"
