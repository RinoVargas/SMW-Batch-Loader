#! /bin/bash

# App Variables
APP_HOME="@APP_HOME@"
APP_PROPERTY_FILE="smw-batch-loader.properties"
APP_EXECUTABLE="smw-batch-loader.jar"

# Java Variables
JAVA_HOME="@JAVA_HOME@"
JAVA_OPTS=""

# Execution
DAEMON="$JAVA_HOME/bin/java"
DAEMON_ARGS="$JAVA_OPTS -Denvironment=$APP_HOME -jar $APP_HOME/$APP_EXECUTABLE"

run_upload(){
  $DAEMON $DAEMON_ARGS 2>> /dev/null >> /dev/null
}

run_upload