#!/bin/bash

/usr/bin/xray -t 0.0.0.0:2000 -b 0.0.0.0:2000 > /dev/null &
java -jar app.jar
