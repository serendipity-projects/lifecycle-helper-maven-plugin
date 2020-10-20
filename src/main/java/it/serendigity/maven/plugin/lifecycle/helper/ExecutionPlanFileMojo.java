
package it.serendigity.maven.plugin.lifecycle.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import it.serendigity.maven.plugin.lifecycle.helper.output.CSVTable;
import it.serendigity.maven.plugin.lifecycle.helper.utils.TextUtils;
import it.serendigity.maven.plugin.lifecycle.helper.vo.MavenExecutionPlanInfo;

/**
 * List execution plan for the current project to an output file.
 */
@Mojo(name = "execution-plan-file", defaultPhase = LifecyclePhase.VALIDATE, threadSafe = true, requiresProject = true)
public class ExecutionPlanFileMojo extends AbstractLifecycleMojo {

	private static final String OUTPUT_FILE_NAME = "lifecycle-helper";
	private static final String CSV = "CSV";

	public enum FILE_FORMAT {
		CSV
	}

	/**
	 * Output directory to write the execution plan. The default is the project's
	 * build directory. For goals that don't require a project the current directory
	 * will be used.
	 */
	@Parameter(property = "lifecycle-helper.outputDirectory", defaultValue = "${project.build.directory}")
	private File paramOutputDirectory;

	/**
	 * The name of the execution plan file. A file extension matching the configured
	 * {@code fileFormat} will be added if not specified.
	 */
	@Parameter(property = "lifecycle-helper.outputFileName", defaultValue = OUTPUT_FILE_NAME)
	private String paramOutputFileName;

	@Parameter(property = "lifecycle-helper.fileFormat", defaultValue = CSV)
	private FILE_FORMAT paramFileFormat;

	public void execute() throws MojoExecutionException, MojoFailureException {
		if ( isParamSkip() ) {
			getLog().info( "Skipping the execution as per configuration" );
			return;
		}

		String fileName = TextUtils.normalizeFileNameWithExtension( paramOutputFileName, paramFileFormat.name() );

		boolean created = validateOutputDirectory( paramOutputDirectory );
		if ( getLog().isInfoEnabled() ) {
			getLog().info( "Output directory :" + paramOutputDirectory + " (created: " + created + ")" );
		}

		MavenExecutionPlanInfo executionPlanInfo = calculateExecutionPlan( false );

		CSVTable csv = new CSVTable( executionPlanInfo );

		String table = csv.createTable();

		try {

			Path fullPathOutputFile = paramOutputDirectory.toPath().resolve( fileName );

			File fout = fullPathOutputFile.toFile();

			if ( fout.exists() ) {
				getLog().error( "Output file name already exists! " + fileName + "(" + paramOutputDirectory + ")" );
			}
			else {

				FileOutputStream fos = new FileOutputStream( fout );

				try ( BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( fos, StandardCharsets.UTF_8 ) ) ) {
					bw.write( table );

				}
			}

			handleOutput( "\nFile " + fileName + " created in " + paramOutputDirectory + "." );

		}
		catch (IOException e) {
			getLog().error( e );
		}

	}

	/**
	 * Check {@code dir} and if not exists create the directory
	 *
	 * @param dir directory to check
	 * @return true if and only if the directory was created, along with all necessary parent directories; false
	 * otherwise
	 */
	protected boolean validateOutputDirectory( File dir ) {
		boolean result = false;
		if ( !dir.exists() ) {
			result = dir.mkdirs();
		}
		return result;
	}

	@Override
	protected String headerParametersString() {
		String header = super.headerParametersString();
		header = header + "\nFormat: " + paramFileFormat;
		header = header + "\nDir: " + paramOutputDirectory;
		header = header + "\nFile: " + paramOutputFileName;

		return header;
	}

}
