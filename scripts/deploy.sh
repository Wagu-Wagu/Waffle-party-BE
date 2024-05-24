#!/bin/bash
REPOSITORY=/home/ubuntu/app
BUILD_PATH=$(ls /home/ubuntu/app/*SNAPSHOT.jar)
JAR_NAME=$(basename $BUILD_PATH)
IDLE_APPLICATION_PATH=/home/ubuntu/app/${JAR_NAME}
TARGET_PORT=8080

if [ "$DEPLOYMENT_GROUP_NAME" == "waguwagu-prod-codedeploy-group" ]
then
  echo "> Target port is  ${TARGET_PORT}."
  
  TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')
  echo "> Target PID is  ${TARGET_PID}."

  if [ ! -z ${TARGET_PID} ]; then
    echo "> Kill WAS running at ${TARGET_PORT}."
    sudo kill ${TARGET_PID}
  fi
  
  nohup java -jar -Duser.timezone=Asia/Seoul -Dserver.port=${TARGET_PORT} -Dspring.profiles.active=prod $IDLE_APPLICATION_PATH >> /home/ubuntu/app/nohup.out 2>&1 &
fi

if [ "$DEPLOYMENT_GROUP_NAME" == "waguwagu-dev-codedeploy-group" ]
then
  echo "> Target port is  ${TARGET_PORT}."
  
  TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')
  echo "> Target PID is  ${TARGET_PID}."

  if [ ! -z ${TARGET_PID} ]; then
    echo "> Kill WAS running at ${TARGET_PORT}."
    sudo kill ${TARGET_PID}
  fi
  
  nohup java -jar -Duser.timezone=Asia/Seoul -Dserver.port=${TARGET_PORT} -Dspring.profiles.active=dev $IDLE_APPLICATION_PATH >> /home/ubuntu/app/nohup.out 2>&1 &
fi
