#!/bin/bash

# AWS Secrets Manager에서 시크릿 가져오기
SECRETS=$(aws secretsmanager get-secret-value --secret-id project-dose-variables --query SecretString --output text)

# jq를 사용하여 환경변수 설정
export DB_USERNAME=$(echo $SECRETS | jq -r '.DB_USERNAME')
export DB_PASSWORD=$(echo $SECRETS | jq -r '.DB_PASSWORD')
export EMAIL_USERNAME=$(echo $SECRETS | jq -r '.EMAIL_USERNAME')
export EMAIL_PASSWORD=$(echo $SECRETS | jq -r '.EMAIL_PASSWORD')
export KAKAO_CLIENT_ID=$(echo $SECRETS | jq -r '.KAKAO_CLIENT_ID')
export KAKAO_CLIENT_SECRET=$(echo $SECRETS | jq -r '.KAKAO_CLIENT_SECRET')

BUILD_JAR=$(ls /home/ec2-user/action/build/libs/*SNAPSHOT.jar)
JAR_NAME=$(basename $BUILD_JAR)
echo "## build file name : $JAR_NAME" >> /home/ec2-user/action/spring-deploy.log

echo "## copy build file" >> /home/ec2-user/action/spring-deploy.log
DEPLOY_PATH=/home/ec2-user/action/
cp $BUILD_JAR $DEPLOY_PATH

echo "## current pid" >> /home/ec2-user/action/spring-deploy.log
CURRENT_PID=$(ps -ef | grep "java" | awk 'NR==1 {print $2}')

if [ -z $CURRENT_PID ]
then
  echo "## 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ec2-user/action/spring-deploy.log
else
  echo "## kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo "## deploy JAR file"   >> /home/ec2-user/action/spring-deploy.log
nohup java -jar $DEPLOY_JAR >> /home/ec2-user/action/spring-deploy.log 2> /home/ec2-user/action/spring-deploy_err.log &