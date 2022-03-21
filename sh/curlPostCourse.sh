#!/bin/sh
curl -X POST localhost:8080/api/courses/ -d @course.json -H "Content-Type:application/json"
