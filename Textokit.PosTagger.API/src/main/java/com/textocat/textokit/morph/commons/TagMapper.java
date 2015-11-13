/**
 * 
 */
package com.textocat.textokit.morph.commons;

import java.util.Set;

/**
 * @author Rinat Gareev
 * 
 */
public interface TagMapper {

	Set<String> parseTag(String tag, String token);

	String toTag(Set<String> grammems);
}