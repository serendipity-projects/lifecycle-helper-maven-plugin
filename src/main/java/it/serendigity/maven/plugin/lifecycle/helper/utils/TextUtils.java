package it.serendigity.maven.plugin.lifecycle.helper.utils;

public class TextUtils {

	public static final String STRING_PLACEHOLDER = "%s";

	private TextUtils() {

	}

	public static String leftJustify( Character leftSeparator, Character rightSeparator, String text, int width ) {

		return justify( leftSeparator, rightSeparator, text, -width );
	}

	public static String rightJustify( Character leftSeparator, Character rightSeparator, String text, int width ) {

		return justify( leftSeparator, rightSeparator, text, width );
	}

	public static String justifyFormat( int width ) {

		return justifyFormat( null, null, width );

	}

	public static String justifyFormat( Character leftSeparator, Character rightSeparator, int width ) {

		String l = leftSeparator != null ? leftSeparator.toString() : "";
		String r = rightSeparator != null ? rightSeparator.toString() : "";

		return l + "%" + width + "s" + r;

	}

	protected static String justify( Character leftSeparator, Character rightSeparator, String text, int width ) {

		return String.format( justifyFormat( leftSeparator, rightSeparator, width ), text );
	}

	/**
	 * Normalize file name with extension {@code fileFormat}
	 *
	 * @param fileName name of the file
	 * @param fileFormat the format of the file (extension)
	 * @return the fileName with extension
	 */
	public static String normalizeFileNameWithExtension( String fileName, String fileFormat ) {

		String result = fileName;
		if ( fileFormat != null && !fileName.endsWith( fileFormat ) ) {

			result += "." + fileFormat;

		}

		return result;
	}

}
