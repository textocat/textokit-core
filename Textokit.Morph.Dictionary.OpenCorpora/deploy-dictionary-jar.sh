#!/bin/bash
if [[ $# != 5 ]] ; then
  echo "Usage: <serialized-dict-jar-file> <textokit-version> <dict-date-in-YYYYMMDD-format> <variant> <counter>"
  exit 1
fi
file=$1
textokit_version=$2
dict_date=$3
variant=$4
counter=$5
mvn deploy:deploy-file -DgroupId=com.textocat.textokit.core \
  -DartifactId=textokit-dictionary-opencorpora-resource \
  -Dclassifier=$variant \
  -Dversion="$textokit_version-$dict_date-$counter" \
  -Dpackaging=jar \
  -Dfile=$file \
  -DrepositoryId=textocat.artifactory \
  -Durl="http://corp.textocat.com/artifactory/oss-libs-releases-local"