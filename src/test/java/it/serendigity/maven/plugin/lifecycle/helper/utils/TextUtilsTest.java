package it.serendigity.maven.plugin.lifecycle.helper.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class TextUtilsTest {

	@Test
	void testLeftJustify_nullSeparator_exactLength() throws Exception {

		String text = "Simple text";
		int width = text.length();
		String leftJustify = TextUtils.leftJustify( null, null, text, width );
		assertNotNull( leftJustify );

		assertThat( leftJustify ).isEqualTo( text ).hasSize( width );

	}

	@Test
	void testLeftJustify_extraLength() throws Exception {

		String text = "Simple text";
		int width = text.length() + 20;
		String leftJustify = TextUtils.leftJustify( null, null, text, width );
		assertNotNull( leftJustify );

		assertThat( leftJustify ).startsWith( text ).hasSize( width );

	}

	@Test
	void testrightJustify_extraLength() throws Exception {

		String text = "Simple text";
		int width = text.length() + 20;
		String justify = TextUtils.rightJustify( null, null, text, width );
		assertNotNull( justify );

		assertThat( justify ).endsWith( text ).hasSize( width );
	}

	@Test
	void testFormatString() throws Exception {

		String text = "Simple text";

		String t = String.format( TextUtils.STRING_PLACEHOLDER + ";", text );
		assertNotNull( t );
		assertThat( t ).startsWith( text ).endsWith( ";" );

	}

	@Test
	void testNormalizeFileNameWithExtension() throws Exception {
		String fileName = "aaaaa";
		String ext = "csv";
		String normalized = TextUtils.normalizeFileNameWithExtension( fileName, ext );
		assertThat( normalized ).isEqualToIgnoringCase( fileName + "." + ext );
	}

	@Test
	void testNormalizeFileNameWithExtension_fileWithExtension() throws Exception {
		String fileName = "aaaaa.csv";
		String ext = "CSV";
		String normalized = TextUtils.normalizeFileNameWithExtension( fileName, ext );
		assertThat( normalized ).isEqualToIgnoringCase( fileName );
	}

	@Test
	void testNormalizeFileNameWithExtension_fileWithOtherDir() throws Exception {
		String fileName = "cccc/aaaaa";
		String ext = "CSV";
		String normalized = TextUtils.normalizeFileNameWithExtension( fileName, ext );
		assertThat( normalized ).isEqualToIgnoringCase( fileName + "."+ ext);
	}

	@Test
	void testJustifyFormat_withSeparator() throws Exception {
		int width = 10;
		char sep1 = '|';
		char sep2 = ';';
		String format = TextUtils.justifyFormat( sep1, sep2, width );
		assertThat( format ).contains( String.valueOf( sep1 ), String.valueOf( sep2 ), String.valueOf( width ) );
	}

}
