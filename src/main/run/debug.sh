#!/usr/bin/env bash
cd ..
java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -jar -Dloader.path=.,resources,lib ./imms-aisan-1.0.jar