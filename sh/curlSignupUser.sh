#!/bin/sh
curl -X POST localhost:8080/api/auth/signup/ -d @userSignup.json -H "Content-Type:application/json"
