---
layout: default
title: Textokit – Getting Started
---
# Getting Started

## Contents

* [Installation](#installation)
* [Preliminary reading - UIMA basics](#uima-basics)
* [Running](#running)
  * [How to define an input](#define-input)
  * [How to compose a text processing pipeline](#compose-pipeline)
  * [How to consume an output](#consume-output)
  * [How to run a pipeline](#run-pipeline)
  * [The complete example](#complete-example)
  * [How to consume an output - 2](#consume-output-2)

## <a name="installation"></a> Installation
TextoKit is available as a bunch of Maven artifacts.
They are published to the Textocat repository that you can use by specifying the following in your project POM:
{% highlight xml %}
  <repository>
    <id>textocat.artifactory</id>
    <url>http://corp.textocat.com/artifactory/oss-repo</url>
    <name>Textocat Open-Source Repository</name>
   </repository>
{% endhighlight %}

There are 3 main types of modules:

* __API modules.__ A module of this type defines analysis engine (AE) interfaces for a text processing step,
a type system of feature structures (e.g., annotations) that a component implementation produces,
fully-qualified names to lookup a component implementation,
utilities to work with related data structures and so on.

* __Implementation modules.__ A module of this type contains an actual analysis engine.
Usually your project doesn't need to depend on an implementation during compilation,
so it is better to declare the module dependency with `<scope>runtime</scope>`.

* __Resource packages.__ These jars contains serialized dictionaries, models, etc.
that can be required by analysis engines. It is also better to declare them in the runtime scope.

Depending on which analyzers you need, you include a set of dependencies into your application:

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
<!-- analyzer implementations -->
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
...
<properties>
  <!-- define in properties block of POM -->
  <textokit.version>0.1-SNAPSHOT</textokit.version>
</properties>
{% endhighlight %}

## <a name="uima-basics"></a> Preliminary reading - UIMA basics
[UIMA Tutorial](http://uima.apache.org/d/uimaj-current/tutorials_and_users_guides.html) – chapters 1, 3 and 5.

[UIMA Reference](http://uima.apache.org/d/uimaj-current/references.html) – chapters 2, 4, 5.

[UIMAfit Guide](http://uima.apache.org/d/uimafit-current/tools.uimafit.book.html) – chapters 1 through 6.

## <a name="running"></a> Running
To process texts with UIMA you need to (1) define an input, (2) compose a processing pipeline and (3) consume output.
Further sections explain each of these stages in detail.

### <a name="define-input"></a> How to define an input
There is the concept of *collection reader* in UIMA.
Basically it is an interface similar to an iterator of documents where each document is represented by UIMA CAS.
Check the [UIMA documentation](http://uima.apache.org/d/uimaj-current/tutorials_and_users_guides.html#ugr.tug.cpe.collection_reader.developing) for details.

TextoKit contains several collection reader implementations, we mention here a few:

* `FileDirectoryCollectionReader` (from `textokit-commons`) – for files with a plain text;

* `JdbcCollectionReader` (from `textokit-commons`) – to read docs from a result of SQL query;

* `XmiCollectionReader` (from `textokit-commons`) - for XMI files; XMI is an XML schema used by UIMA to serialize CAS.

UIMA needs a `CollectionReaderDescription` to produce an instance of collection reader in runtime.
You can either write an XML description (as supposed in UIMA documentation) or build it programmatically.
The latter approach is facilitated by UIMAfit's [`CollectionReaderFactory`](http://uima.apache.org/d/uimafit-current/api/org/apache/uima/fit/factory/CollectionReaderFactory.html).
The quite common convention is to provide a static factory method in a collection reader class where the method produces a description instance.

The main purpose of a collection reader is to set a document text and some initial feature structures into an empty CAS.
TextoKit's collection readers add a single annotation of type `DocumentMetadata` (from `textokit-commons`)
and set its `sourceUri` feature (and optionally some others).
A value of `sourceUri` is supposed to be a reference to source of text, e.g., a file URL, a record identifier, etc.

### <a name="compose-pipeline"></a> How to compose a text processing pipeline
Text processing pipeline is called an *aggregate analysis engine* in UIMA.
It is composed from a set of smaller analysis engines and a flow controller.
The latter defines a route for CASes.
Here we assume that a default flow controller implementation, provided by UIMA SDK, is good enough for introduction purposes.
It implements linear ordering of constituent analysis engines, so you just list them in an appropriate order.

UIMA needs an `AnalysisEngineDescription` to produce an instance of analysis engine in runtime.
You can either write an XML description (as supposed in UIMA documentation) or build it programmatically.
The latter approach is facilitated by UIMAfit's [`AnalysisEngineFactory`](http://uima.apache.org/d/uimafit-current/api/org/apache/uima/fit/factory/AnalysisEngineFactory.html).
When you assemble a description for aggregate AE there are a couple of ways to define an inner AE:

* write a description for the inner AE from scratch,

* import its description and, optionally, override its configuration parameter values.

In UIMA description an import can be specified by location or fully-qualified name.
Import by location has a lot of pitfalls and generally should not be used.
Import by fully-qualified name is resolved against application classpath and UIMA datapath.
It is preferred way to reference analysis engines in TextoKit.

Fully-qualified names of TextoKit analyzers for basic text processing steps are defined as constants
in corresponding _facade classes_ of API modules, here are a few of them:

* `TokenizerAPI` (in `textokit-tokenizer-api`),

* `SentenceSplitterAPI` (in `textokit-sentence-splitter-api`),

* `PosTaggerAPI` (in `textokit-pos-tagger-api`),

* `LemmatizerAPI` (in `textokit-lemmatizer-api`).

Using these names you can assemble an aggregate description with UIMAfit's [`AnalysisEngineFactory`](http://uima.apache.org/d/uimafit-current/api/org/apache/uima/fit/factory/AnalysisEngineFactory.html):
{% highlight java %}
import static org.apache.uima.fit.factory.AnalysisEngineFactory.* ;
...
AnalysisEngineDescription aeDesc = createEngineDescription(
        createEngineDescription(TokenizerAPI.AE_TOKENIZER),
        createEngineDescription(SentenceSplitterAPI.AE_SENTENCE_SPLITTER),
        createEngineDescription(PosTaggerAPI.AE_POSTAGGER),
        createEngineDescription(LemmatizerAPI.AE_LEMMATIZER)
);
{% endhighlight %}

Some PoS-tagger and lemmatizer implementations can expect an instance of `MorphDictionaryHolder` (from `textokit-morph-dictionary-api`) to be injected as an UIMA external resource.
It is true for implementations that we have chosen above.
For such cases `PosTaggerAPI` and `LemmatizerAPI` defines an expected name for external resource holding a morphological dictionary.
TextoKit's `MorphDictionaryAPI` has factory methods to produce descriptions for such external resource.
Consequently, you should add the external resource description into the aggregate description:

{% highlight java %}
ExternalResourceDescription morphDictDesc =
        MorphDictionaryAPIFactory.getMorphDictionaryAPI().getResourceDescriptionForCachedInstance();
morphDictDesc.setName(PosTaggerAPI.MORPH_DICTIONARY_RESOURCE_NAME);
PipelineDescriptorUtils.getResourceManagerConfiguration(aeDesc).addExternalResource(morphDictDesc);
{% endhighlight %}

### <a name="consume-output"></a> How to consume an output
There are several ways to extract data from a processed CAS depending on a deployment.
The first approach is to implement an analysis engine (starting from [`JCasAnnotator_ImplBase`](http://uima.apache.org/d/uimafit-current/api/org/apache/uima/fit/component/JCasAnnotator_ImplBase.html) or [`CasAnnotator_ImplBase`](http://uima.apache.org/d/uimafit-current/api/org/apache/uima/fit/component/CasAnnotator_ImplBase.html)),
write extraction logic in its `process` method where a CAS instance is passed.
And then you simply add this AE to the end of the pipeline.
Here is the example where each word with its lemma and PoS-tag is written to standard output:
{% highlight java %}
public class WordPosLemmaWriter extends JCasAnnotator_ImplBase {
    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        for(Word w : JCasUtil.select(jCas, Word.class)) {
            String src = w.getCoveredText();
            String lemma = MorphCasUtils.getFirstLemma(w);
            String posTag = MorphCasUtils.getFirstPosTag(w);
            System.out.print(String.format("%s/%s/%s ", src, lemma, posTag));
        }
        // mark the end of a document
        System.out.println("\n");
    }
}
{% endhighlight %}

Another approach will be shown in a section below.

### <a name="run-pipeline"></a> How to run a pipeline
UIMA provides quite a few options for pipeline deployment.
The easiest way is arguably provided by UIMAfit's [`SimplePipeline`](http://uima.apache.org/d/uimafit-current/api/org/apache/uima/fit/pipeline/SimplePipeline.html) utility.
Continuing our example an invocation is the following:

{% highlight java %}
AnalysisEngineDescription writerDesc = createEngineDescription(WordPosLemmaWriter.class);

SimplePipeline.runPipeline(readerDesc, aeDesc, writerDesc);
{% endhighlight %}

This approach is good for quick small experiments and testing,
it utilizes a single thread for analysis and only a single CAS instance which is reset before each document processing.

One of ways to enable multi-threaded processing is to use [Collection Processing Engine](http://uima.apache.org/d/uimaj-current/tutorials_and_users_guides.html#ugr.tug.cpe) machinery for deployment.
Fortunately, UIMAfit has utilities to minimize efforts for configuring and launching CPE:
[CpeBuilder](http://uima.apache.org/d/uimafit-current/api/org/apache/uima/fit/cpe/CpeBuilder.html) and [CpePipeline](http://uima.apache.org/d/uimafit-current/api/org/apache/uima/fit/cpe/CpePipeline.html).
Note that they are provided in a separate module:

{% highlight xml %}
<dependency>
    <groupId>org.apache.uima</groupId>
    <artifactId>uimafit-cpe</artifactId>
    <version>2.1.0</version>
</dependency>
{% endhighlight %}
Although UIMA CPE provides some limited scalability and remote deployment capabilities, it is a medium solution.
There are at least two more advanced tools: [UIMA Asynchronous Scaleout](https://uima.apache.org/doc-uimaas-what.html) and [UIMA DUCC](https://uima.apache.org/doc-uimaducc-whatitam.html).

### <a name="complete-example"></a> The complete example
You can see the complete example described above in [the project on Github](https://github.com/textocat/textokit-core-examples/tree/master/getting-started).

### <a name="consume-output-2"></a> How to consume an output - 2
Sometimes writing a separate annotator class just to do something with processing output might be excessive.
If you don't need parallelization of a pipeline (by CPE, AS or DUCC)
you can inspect CAS instances right in your application code after they are processed by the pipeline.
For such cases UIMAfit's [JCasIterable](http://uima.apache.org/d/uimafit-current/api/org/apache/uima/fit/pipeline/JCasIterable.html) does all preparations for you:
each iterator instantiates a collection reader and an analysis engine,
and creates a CAS instance which will be reused for each document in the collection.
This CAS instance is returned by the iterator's `next` method, once for each document.
Consequently, we can rewrite the example above without `WordPosLemmaWriter` as the following:

{% highlight java %}
JCasIterable jCasIterable = new JCasIterable(readerDesc, aeDesc);
for (JCas jCas : jCasIterable) {
    for (Word w : JCasUtil.select(jCas, Word.class)) {
        String src = w.getCoveredText();
        String lemma = MorphCasUtils.getFirstLemma(w);
        String posTag = MorphCasUtils.getFirstPosTag(w);
        System.out.print(String.format("%s/%s/%s ", src, lemma, posTag));
    }
    // mark the end of a document
    System.out.println("\n");
}
{% endhighlight %}
