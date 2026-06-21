#!/bin/sh
# Gradle wrapper script for Unix-like systems

# Resolve JAVA_HOME
if [ -z "$JAVA_HOME" ]; then
    JAVA_CMD=java
else
    JAVA_CMD="$JAVA_HOME/bin/java"
fi

# Find the script dir
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
GRADLE_USER_HOME="$SCRIPT_DIR/.gradle"

# Execute gradle
exec "$JAVA_CMD" -Dorg.gradle.appname=gradlew -classpath "$SCRIPT_DIR/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
