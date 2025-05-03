```shell
#!/bin/bash

APP_NAME="myapp.jar"
JAVA_OPTS=""

# === JVM Memory ===
JAVA_OPTS+=" -Xms12G -Xmx12G"                    # Fixed heap size
JAVA_OPTS+=" -XX:NewRatio=3"                     # Young:Old = 1:3

# === GC: G1GC for low pause ===
JAVA_OPTS+=" -XX:+UseG1GC"
JAVA_OPTS+=" -XX:InitiatingHeapOccupancyPercent=45" # Start concurrent GC earlier
JAVA_OPTS+=" -XX:MaxGCPauseMillis=500"              # Target max GC pause
JAVA_OPTS+=" -XX:+ParallelRefProcEnabled"

# === Logs & Monitoring ===
JAVA_OPTS+=" -Xlog:gc*:file=/var/log/gc.log:time,uptime,level,tags:filecount=10,filesize=10M"
JAVA_OPTS+=" -Djava.awt.headless=true"              # Headless mode (no UI)
JAVA_OPTS+=" -Djava.io.tmpdir=/dist/tmp"            # Temp file directory

# === Debugging (optional) ===
# JAVA_OPTS+=" -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

# === Run the application ===
echo "Starting $APP_NAME with JVM options: $JAVA_OPTS"
java $JAVA_OPTS -jar $APP_NAME




-Xms4G                         # Initial heap size (adjust based on your machine)
-Xmx8G                         # Maximum heap size (increase if you hit OOM)
-XX:+UseG1GC                   # Use G1 Garbage Collector (good for low-latency, multi-core apps)
-XX:MaxGCPauseMillis=200       # Target GC pause time in ms
-XX:+UseStringDeduplication    # Reduce memory for duplicate strings
-XX:+ParallelRefProcEnabled    # Parallelize reference processing in G1 GC
-XX:+UnlockExperimentalVMOptions
-XX:+AlwaysPreTouch            # Ensure all memory is allocated up front (performance-friendly)
-XX:+DisableExplicitGC         # Prevent System.gc() from triggering full GCs
-XX:+UseContainerSupport       # Ensures JVM respects container memory limits (especially in Docker)

--
-XX:+HeapDumpOnOutOfMemoryError              # Dump heap on OOM (for debugging)
-XX:HeapDumpPath=/path/to/heapdump.hprof     # Dump location
-XX:+PrintGCDetails -XX:+PrintGCDateStamps   # GC logs
-Xloggc:/path/to/gc.log                      # GC log file
--
-XX:ParallelGCThreads=<num>           # Number of GC threads (usually equals CPU cores)
-XX:ConcGCThreads=<num>               # Concurrent GC threads
--
-Djava.awt.headless=true              # Required for server-side apps
-XX:+OptimizeStringConcat             # Better string concatenation
-XX:+UseCompressedOops                # Use 32-bit references (enabled by default for <32GB heaps)
--
# Memory Diagnostics (for OutOfMemoryError) 
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=/path/to/dump
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps
-Xlog:gc*:file=/path/to/gc.log:time,uptime,level,tags
--
# Threading / CPU Configuration
-XX:ActiveProcessorCount=8                     # Set the number of available processors (or rely on system default)
-Djava.util.concurrent.ForkJoinPool.common.parallelism=8  # Tune thread pool parallelism
# (Adjust these according to your machine's core count. Usually N = #cores - 1 is a good starting point.)
--
-XX:InitiatingHeapOccupancyPercent=80  # 45% default, It is used with the G1 Garbage Collector to control when the concurrent marking cycle of the GC should begin.
Tuning Tips:
------------
Lower values (e.g., 100ms):
   - Better responsiveness, especially for low-latency apps.
   - May cause GC to run more frequently and collect less memory each time → higher CPU usage.

Higher values (e.g., 500ms or 1000ms):
   - Reduces frequency of GC, but might cause longer pause times.
   - Useful if your app is batch-oriented or doesn't mind occasional pauses.
-------'

VisualVM
JMC (Java Mission Control)
GC logs + GCViewer (Not relible)
JFR (Java Flight Recorder)
YourKit,  (Paid)
JProfiler, (Paid)
Eclipse MAT.


jcmd <PID> JFR.start name=MyRecording settings=profile duration=60s filename=recording.jfr
jcmd <PID> GC.heap_dump /Users/ram/heapdump/heapdump-1.hprof
jps         # Find the PID of your Java application
jmap -dump:live,format=b,file=heapdump.hprof <PID>
jmap -dump:live,format=b,file=heapdump.hprof 65568



java -Xms2G -Xmx2G -XX:NewRatio=3 -Djava.awt.headless=true -XX:+UseG1GC -jar 

java -Xms512m -Xmx512m -XX:NewRatio=3 -Djava.awt.headless=true -XX:+UseG1GC -XX:StartFlightRecording=name=MyRecording,filename=/Users/ram/heapdump/myrecording.jfr,duration=60s,settings=all -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9000 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -jar app.jar

// profile, default, all
java -XX:StartFlightRecording=name=MyRecording,filename=myrecording.jfr,duration=30m,maxsize=100MB,settings=profile -jar myapplication.jar

java -Xms512m -Xmx512m -XX:NewRatio=3 -XX:InitiatingHeapOccupancyPercent=80 -Djava.awt.headless=true -XX:+UseG1GC -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9000 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false  -jar app.jar

java -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=heapdump.hprof -jar your-application.jar

java -Xms5Gm -Xmx5G -XX:NewRatio=3 -Djava.awt.headless=true -XX:+UseG1GC -XX:InitiatingHeapOccupancyPercent=80  -XX:+UseStringDeduplication -XX:+OptimizeStringConcat -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=heapdump.hprof -XX:StartFlightRecording=name=MyRecording,filename=/Users/v-rmolabanti/myrecording.jfr,duration=30m,settings=profile -jar app.jar

```
