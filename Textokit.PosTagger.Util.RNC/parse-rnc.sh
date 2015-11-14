#!/bin/bash
if [[ -z $1 || -z $2  ]] ; then
  echo "Usage: <ruscorpora-text-dir> <output-dir>"
  exit 1
fi
java_app_arguments="--enable-dictionary-aligning"
java_app_arguments="$java_app_arguments,--ruscorpora-text-dir,$1,-o,$2"
mvn exec:java -Puse-textocat-artifactory -Pwith-logging-impl -Dexec.mainClass=com.textocat.textokit.morph.ruscorpora.RusCorporaParserBootstrap \
  -Dexec.arguments="$java_app_arguments"
