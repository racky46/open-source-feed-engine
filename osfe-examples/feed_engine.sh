########################################################
# The script to shutdown other applications through JMS
########################################################
# make sure OSFE_WORK directory exists
if [ ! -d $OSFE_WORK ]; then
  echo "Error: The ${OSFE_WORK} directory does not exists."
  exit
fi

# Java class to run
MAIN_CLASS=com.qagen.osfe.engine.InboundFeedEngine

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
$full_cmd $@ $@

