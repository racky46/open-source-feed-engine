#!/bin/bash
##############################################
# Create the osfe directories under OSFE_WORK
##############################################
mkdir -p $OSFE_WORK/config/common
mkdir $OSFE_WORK/feed
mkdir $OSFE_WORK/logs
mkdir $OSFE_WORK/partnerConfig

##############################################
# Copy config.properties 
##############################################
cp ../install/database/mysql/config/common/config.properties $OSFE_WORK/config/common

##############################################
# Copy OSFE demo feed configuration files 
##############################################
cp -r partnerConfig/* $OSFE_WORK/partnerConfig/

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

cp demoData/testd/request/incoming/* $OSFE_WORK/feed/acme/qagen/testd/request/incoming
cp demoData/testf/request/incoming/* $OSFE_WORK/feed/acme/qagen/testf/request/incoming

gunzip $OSFE_WORK/feed/acme/qagen/testd/request/incoming/*
gunzip $OSFE_WORK/feed/acme/qagen/testf/request/incoming/*

echo 'All Done!'
