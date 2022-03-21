#!/bin/sh
curl -X POST localhost:8080/api/patikas/ -d @patika.json -H "Content-Type:application/json"
