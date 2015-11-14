#!/bin/bash
if [[ -z $1 || -z $2  ]] ; then
  echo "Usage: <opencorpora-dict-xml> <output-file>"
  exit 1
fi
java_app_arguments="--dict-extension-class,com.textocat.textokit.morph.ruscorpora.RNCDictionaryExtension,--output-variant,rnc"
java_app_arguments="$java_app_arguments,-i,$1,-o,$2"
mvn exec:java -Pwith-logging-impl -Dexec.mainClass=com.textocat.textokit.morph.opencorpora.resource.XmlDictionaryPSP \
  -Dexec.arguments="$java_app_arguments"
