---
layout: default
title: Textokit – Getting Started
---
# Getting Started

## Installation
TextoKit is available as a bunch of Maven artifacts.
There are at least 3 types of them:

* __API modules.__ A module of this type defines analysis engine (AE) interfaces for a text processing step,
a type system of feature structures (e.g., annotations) that a component implementation produces,
fully-qualified names to lookup a component implementation,
utilities to work with related data structures and so on.

* __Implementation modules.__ A module of this type contains an actual analysis engine.
Usually your project doesn't need to depend on an implementation during compilation,
so it is better to declare the module dependency with `<scope>runtime</scope>`.

* __Resource packages.__ These jars contains serialized dictionaries, models, etc.
that can be required by analysis engines. It is also better to declare them in the runtime scope.

Depending on which analyzers you need, you include the following set of dependencies
into your application:

* API modules (sharing the same TextoKit version),

* modules with their implementations (sharing the same TextoKit version with API modules),

* resources that are required by analyzer implementations;
note that resource artifacts have the own separate versioning schema.

For example, if you need lemmatization capability you start from `textokit-lemmatizer-api` module
and its implementation `textokit-lemmatizer-dictionary-sim`.
Then provide analyzer implementations for all preliminary steps: a tokenizer, a sentence splitter, a Part-of-Speech tagger.
The following choices are quite reasonable:

* `textokit-tokenizer-simple`,

* `textokit-sentence-splitter-heuristic`,

* `textokit-pos-tagger-opennlp`.

The latter one requires two artifacts be provided: an implementation of morphological dictionary API
and a trained model. Hence you add the following:

* `textokit-morph-dictionary-opencorpora`,

* `textokit-pos-tagger-opennlp-model` with a specific classifier and version
(look at page ["Resource artifacts"]({{ site.baseurl }}/resource-artifacts/) for the list of available models).

The former one requires an actual dictionary be provided.
The simplest option is to add another dependency on the compiled dictionary:

* `textokit-dictionary-opencorpora-resource` with a specific classifier and version.

Consequently, you will end up with the following:
{% highlight xml %}

    <!-- API that you will use in your app -->
    <dependency>
        <groupId>com.textocat.textokit.core</groupId>
        <artifactId>textokit-lemmatizer-api</artifactId>
        <version>${textokit.version}</version>
    </dependency>
    <!-- analyzer implementations --->
    <dependency>
        <groupId>com.textocat.textokit.core</groupId>
        <artifactId>textokit-tokenizer-simple</artifactId>
        <version>${textokit.version}</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>com.textocat.textokit.core</groupId>
        <artifactId>textokit-sentence-splitter-heuristic</artifactId>
        <version>${textokit.version}</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>com.textocat.textokit.core</groupId>
        <artifactId>textokit-morph-dictionary-opencorpora</artifactId>
        <version>${textokit.version}</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>com.textocat.textokit.core</groupId>
        <artifactId>textokit-pos-tagger-opennlp</artifactId>
        <version>${textokit.version}</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>com.textocat.textokit.core</groupId>
        <artifactId>textokit-lemmatizer-dictionary-sim</artifactId>
        <version>${textokit.version}</version>
        <scope>runtime</scope>
    </dependency>
    <!-- models, dictionaries, etc. -->
    <dependency>
        <groupId>com.textocat.textokit.core</groupId>
        <artifactId>textokit-dictionary-opencorpora-resource</artifactId>
        <classifier>rnc</classifier>
        <version>0.1-20140407-1</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>com.textocat.textokit.core</groupId>
        <artifactId>textokit-pos-tagger-opennlp-model</artifactId>
        <classifier>rnc1M-8cat</classifier>
        <scope>runtime</scope>
        <version>0.1-20151116-1</version>
    </dependency>
{% endhighlight %}

## Prerequisite - UIMA basics
[UIMA Tutorial](http://uima.apache.org/d/uimaj-current/tutorials_and_users_guides.html) – chapters 1, 3 and 5.

[UIMA Reference](http://uima.apache.org/d/uimaj-current/references.html) – chapters 2, 4, 5.
