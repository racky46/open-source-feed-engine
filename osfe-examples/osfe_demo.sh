#!/bin/bash
##############################################
# Create the osfe directories under OSFE_HOME
##############################################
mkdir -p $OSFE_HOME/config/common
mkdir $OSFE_HOME/feed
mkdir $OSFE_HOME/logs
mkdir $OSFE_HOME/partnerConfig

##############################################
# Copy config.properties 
##############################################
cp ../install/database/mysql/config/common/config.properties $OSFE_HOME/config/common

##############################################
# Copy OSFE demo feed configuration files 
##############################################
cp -r partnerConfig/* $OSFE_HOME/partnerConfig/

##############################################
# Create Feed Directories
##############################################

# Java class to run
MAIN_CLASS=com.qagen.osfe.core.cmdlnUtils.CreateFeedDirectories

# Extra Java VM arguments
JAVA_OPTS="-Xms256m -Xmx512m"

# The java command to run
java_cmd="$MAIN_CLASS"

# Add main OSFE jars and dependency jars to the classpath
jars=`find ../ -follow -name '*.jar' -print`
for jar in $jars; do
  classpath=$classpath:$jar
done

# Construct the full command
full_cmd="java $JAVA_OPTS -cp $classpath $java_cmd"

# Run command
$full_cmd acme_qagen_testd_request
$full_cmd acme_qagen_testf_request

##############################################
# Copy demoData 
##############################################
echo 'Extracting demo feeds.  This may take a few seconds...'

cp demoData/testd/request/incoming/* $OSFE_HOME/feed/acme/qagen/testd/request/incoming
cp demoData/testf/request/incoming/* $OSFE_HOME/feed/acme/qagen/testf/request/incoming

gunzip $OSFE_HOME/feed/acme/qagen/testd/request/incoming/*
gunzip $OSFE_HOME/feed/acme/qagen/testf/request/incoming/*

echo 'All Done!'
