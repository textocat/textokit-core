#!/bin/bash
if [[ $# != 4 ]] ; then
  echo "Usage: <model-jar-file> <textokit-version> <variant> <counter>"
  exit 1
fi
if [[ ! -f $1 ]] ; then
  echo "File $1 does not exist"
  exit 1
fi
file=$1
textokit_version=$2
model_date=$(stat -c %y "$file")
model_date=$(date +%Y%m%d --date="$model_date")
variant=$3
counter=$4
mvn deploy:deploy-file -DgroupId=com.textocat.textokit.core \
  -DartifactId=textokit-pos-tagger-opennlp-model \
  -Dclassifier=$variant \
  -Dversion="$textokit_version-$model_date-$counter" \
  -Dpackaging=jar \
  -Dfile=$file \
  -DrepositoryId=textocat.artifactory \
  -Durl="http://corp.textocat.com/artifactory/oss-libs-releases-local"