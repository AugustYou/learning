#!/usr/bin/env bash
rm -rf gen/*
java -jar mybatis-generator-core-1.3.2.jar -configfile mybatis_generator_config.xml
