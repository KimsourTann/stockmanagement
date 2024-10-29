#!/bin/bash

# build maven

mvn clean install

# Define the file containing your docker command
file="build.sh"

# Extract the current version number
current_version=$(sed -n 's/.*:v\([0-9]*\).*/\1/p' "$file" | tr -d '\n')

# Increment the version number by 1
new_version=$((current_version + 1))

# Replace the old version with the new version in the file
sed -i "" "s/:v$current_version/:v$new_version/g" "$file"
echo "Updated version to v$new_version in $file"

docker build --platform linux/amd64 --push -t kimsourtann/stock-management:v5 .