#!/bin/bash

# $1 = EXPERIMENT_VERSION="v1"
# $2 = MEMORY_REQUESTS="64Mi"
# $3 = MEMORY_LIMITS="256Mi"
# $4 = JVM_OPTS=""
create_k8s_pod () {
    cat ./k8s/memory-consumer.yaml | \
    sed 's#\${EXPERIMENT_VERSION}'"#$1#g" | \
    sed 's#\${MEMORY_REQUESTS}'"#$2#g" | \
    sed 's#\${MEMORY_LIMITS}'"#$3#g" | \
    sed 's#\${JVM_OPTS}'"#$4#g" | \
    kubectl apply -f -    
}

create_k8s_pod "v01" "64Mi" "256Mi" ""
create_k8s_pod "v02" "256Mi" "256Mi" ""

create_k8s_pod "v03" "64Mi" "256Mi" "-Xms64M -Xmx256M"
create_k8s_pod "v04" "256Mi" "256Mi" "-Xms64M -Xmx256M"
create_k8s_pod "v05" "64Mi" "320Mi" "-Xms64M -Xmx256M"
create_k8s_pod "v06" "320Mi" "320Mi" "-Xms64M -Xmx256M"

create_k8s_pod "v07" "64Mi" "256Mi" "-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -Xms64M"
create_k8s_pod "v08" "256Mi" "256Mi" "-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -Xms64M"
create_k8s_pod "v09" "64Mi" "320Mi" "-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -Xms64M"
create_k8s_pod "v10" "320Mi" "320Mi" "-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -Xms64M"
