#!/bin/bash

if [ "$1" = install ] ; then
    brew install postgresql maven openjdk eclipse-java
    exit
fi

if [ "$1" = stop ] ; then
    pkill postgres
    exit
fi

if [ -z "$(pgrep postgres)" ] ; then
    echo "Starting PosgreSQL..."
    /usr/local/opt/postgresql/bin/postgres -D /usr/local/var/postgres &
fi

if [ "$1" = dbinit ] ; then
    set -e
    #createuser --createdb hope
    createdb hope 'database for hope application'
    psql --dbname=hope -f src/main/resources/data/data.sql
    exit
fi

if [ "$1" = run ] ; then
    mvn spring-boot:run
    exit
fi

if [ "$1" = clean ] ; then
    mvn clean
    find . -name '*~' -delete
    exit
fi
