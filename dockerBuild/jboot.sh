#!/bin/bash

java -jar -Dwhatap.oname=freeblog_admin -javaagent:/home/ec2-user/whatap/whatap_agent/freeblog_admin_whatap/whatap.agent-2.2.24.jar freeblog_admin-1.0.war &
