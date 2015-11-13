/**
 * 
 */
package com.textocat.textokit.morph.ruscorpora;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.ExternalResourceFactory.bindExternalResource;
import static org.apache.uima.fit.factory.ExternalResourceFactory.createDependency;

import java.io.File;

import com.textocat.textokit.morph.util.NonTokenizedSpan;
import com.textocat.textokit.morph.util.NonTokenizedSpanAnnotator;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.ExternalResourceFactory;
import org.apache.uima.resource.ExternalResourceDescription;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import com.textocat.textokit.commons.annotator.AnnotationRemover;
import com.textocat.textokit.commons.consumer.XmiWriter;
import com.textocat.textokit.commons.util.Slf4jLoggerImpl;
import com.textocat.textokit.morph.dictionary.MorphDictionaryAPI;
import com.textocat.textokit.morph.dictionary.MorphDictionaryAPIFactory;
import com.textocat.textokit.morph.dictionary.resource.MorphDictionaryHolder;
import com.textocat.textokit.postagger.PosTaggerAPI;
import com.textocat.textokit.segmentation.SentenceSplitterAPI;
import com.textocat.textokit.tokenizer.simple.InitialTokenizer;
import com.textocat.textokit.tokenizer.TokenizerAPI;
import com.textocat.textokit.morph.opencorpora.OpencorporaMorphDictionaryAPI;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * @author Rinat Gareev
 * 
 */
public class RusCorporaParserBootstrap {

	public static void main(String[] args) throws Exception {
		// setup logging
		Slf4jLoggerImpl.forceUsingThisImplementation();
		//
		RusCorporaParserBootstrap launcher = new RusCorporaParserBootstrap();
		new JCommander(launcher, args);
		launcher.run();
	}

	@Parameter(names = "--ruscorpora-text-dir", required = true)
	private File ruscorporaTextDir;
	@Parameter(names = { "-o", "--output-dir" }, required = true)
	private File xmiOutputDir;
	@Parameter(names = "--enable-dictionary-aligning")
	private boolean enableDictionaryAligning;

	private RusCorporaParserBootstrap() {
	}

	private void run() throws Exception {
		CollectionReaderDescription colReaderDesc;
		{
			TypeSystemDescription tsDesc = TypeSystemDescriptionFactory
					.createTypeSystemDescription(
							"com.textocat.textokit.commons.Commons-TypeSystem",
							TokenizerAPI.TYPESYSTEM_TOKENIZER,
							SentenceSplitterAPI.TYPESYSTEM_SENTENCES,
							PosTaggerAPI.TYPESYSTEM_POSTAGGER);
			//
			if (!enableDictionaryAligning) {
				colReaderDesc = CollectionReaderFactory.createReaderDescription(
						RusCorporaCollectionReader.class,
						tsDesc,
						RusCorporaCollectionReader.PARAM_INPUT_DIR, ruscorporaTextDir.getPath());
			} else {
				File daLogFile = new File(xmiOutputDir, "dict-aligning2.log");
				colReaderDesc = CollectionReaderFactory.createReaderDescription(
						RusCorporaCollectionReader.class,
						tsDesc,
						RusCorporaCollectionReader.PARAM_INPUT_DIR, ruscorporaTextDir.getPath(),
						RusCorporaCollectionReader.PARAM_TAG_MAPPER_CLASS,
						DictionaryAligningTagMapper2.class,
						DictionaryAligningTagMapper2.PARAM_OUT_FILE, daLogFile.getPath());
				MorphDictionaryAPI dictAPI = MorphDictionaryAPIFactory.getMorphDictionaryAPI();
				if (!(dictAPI instanceof OpencorporaMorphDictionaryAPI)) {
					throw new UnsupportedOperationException(String.format(
							"Doesn't work with " + dictAPI.getClass().getName()));
				}
				ExternalResourceDescription morphDictDesc = dictAPI
						.getResourceDescriptionForCachedInstance();
				createDependency(colReaderDesc,
						DictionaryAligningTagMapper2.RESOURCE_KEY_MORPH_DICTIONARY,
						MorphDictionaryHolder.class);
				ExternalResourceFactory.bindExternalResource(colReaderDesc, DictionaryAligningTagMapper2.RESOURCE_KEY_MORPH_DICTIONARY, morphDictDesc);
			}
		}
		// 
		AnalysisEngineDescription xmiWriterDesc = XmiWriter.createDescription(xmiOutputDir, true);
		// make NonTokenizedSpanAnnotator
		AnalysisEngineDescription ntsAnnotatorDesc;
		{
			TypeSystemDescription tsDesc = TypeSystemDescriptionFactory
					.createTypeSystemDescription("com.textocat.textokit.morph.util.ts-util");
			ntsAnnotatorDesc = AnalysisEngineFactory.createEngineDescription(NonTokenizedSpanAnnotator.class, tsDesc);
		}
		// TODO can we use tokenizer through TokenizerAPI ?
		// make InitialTokenizer for NonTokenizedSpans
		AnalysisEngineDescription tokenizerDesc = createEngineDescription(
				InitialTokenizer.class,
				TokenizerAPI.PARAM_SPAN_TYPE, NonTokenizedSpan.class.getName());
		// make AnnotationRemovers
		AnalysisEngineDescription scaffoldRemover = createEngineDescription(
				AnnotationRemover.class,
				AnnotationRemover.PARAM_NAMESPACES_TO_REMOVE,
				new String[] { "com.textocat.textokit.morph.util" });
		AnalysisEngineDescription specialWTokenRemover = SpecialWTokenRemover.createDescription();
		//
		SimplePipeline.runPipeline(colReaderDesc,
				ntsAnnotatorDesc, tokenizerDesc,
				scaffoldRemover, specialWTokenRemover,
				xmiWriterDesc);
	}
}