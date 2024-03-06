package holdemhandhistory;

/*-  *****************************************************************************
 * Some simple file utilities.
* @author PEAK_
  ******************************************************************************/
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

class FileUtils implements Constants {

	FileUtils() {
	}

	/*- Read a .txt file into String */
	public static String readTxtFileIntoString(String filePath) {
		try {
			return new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
			return null;
		}
	}

	/*- Write a file */
	static <T> void writeFile(String filePath, T ret) {
		try {
			final var fileOut = new FileOutputStream(filePath);
			final var out = new ObjectOutputStream(fileOut);
			out.writeObject(ret);
			out.close();
		} catch (IOException i) {
			Logger.log("FileUtils() writeFile() IO Exception  " + filePath);
			i.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	/*- Read file and return file read or Null */
	static <T> T readFile(String filePath, T $) {

		if (!new File(filePath).exists()) {
			Logger.log("FileUtils() readFile() File does not exist  " + filePath);
			return null;
		}

		try (var fileIn = new FileInputStream(filePath); var in = new ObjectInputStream(fileIn)) {
			// check cast

			$ = (T) in.readObject();

		} catch (IOException i) {
			Logger.log("FileUtils() readFile() IO Exception  " + filePath);
			i.printStackTrace();
		} catch (ClassNotFoundException i) {
			Logger.log("FileUtils() readFile() File not found " + filePath);
			i.printStackTrace();
			return null;
		}
		return $;
	}

	/*-
	 * Delete a file
	 * Arg0 - Path to file
	 */
	static boolean deleteFile(String filePath) {
		final var f = new File(filePath);
		return f.exists() && f.delete();
	}

	/*-
	* Delete all files an a directory.
	* Recursive, deletes all levels
	* Arg0 - File 
	*/
	static void deleteDir(File f) {
		for (var subFile : f.listFiles()) {
			if (!subFile.isDirectory()) {
				subFile.delete();
			} else {
				deleteDir(subFile);
			}
		}
		f.delete();
	}

	/*-
	* Delete all files an a directory.
	* Recursive, deletes all levels
	* Arg0 - Path to file
	*/
	static void deleteDir(String path) {
		final var file = new File(path);
		for (var subFile : file.listFiles()) {
			if (!subFile.isDirectory()) {
				subFile.delete();
			} else {
				deleteDir(subFile);
			}
		}
		file.delete();
	}

	/*-
	 * Create a new directory
	 */
	static boolean makeDirectory(String name) {
		final var filex = new File(name);
		if (!filex.exists()) {
			filex.mkdirs();
		}
		return filex.exists();
	}

	public static void main(String[] args) throws IOException {
		final var sourceFile = "test1.txt";
		final var fos = new FileOutputStream("compressed.zip");
		final var zipOut = new ZipOutputStream(fos);

		final var fileToZip = new File(sourceFile);
		final var fis = new FileInputStream(fileToZip);
		final var zipEntry = new ZipEntry(fileToZip.getName());
		zipOut.putNextEntry(zipEntry);

		final byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zipOut.write(bytes, 0, length);
		}

		zipOut.close();
		fis.close();
		fos.close();
	}

	// Unzip a zip file

	static void unzip(String zipFilePath, String destDir) {
		final var dir = new File(destDir);
		// create output directory if it doesn't exist
		if (!dir.exists()) {
			dir.mkdirs();
		}
		final FileInputStream fis;
		// buffer for read and write data to file
		final byte[] buffer = new byte[1024];
		try {
			fis = new FileInputStream(zipFilePath);
			final var zis = new ZipInputStream(fis);
			var ze = zis.getNextEntry();
			while (ze != null) {
				final var fileName = ze.getName();
				final var newFile = new File(
						new StringBuilder().append(destDir).append(File.separator).append(fileName).toString());
				System.out.println("Unzipping to " + newFile.getAbsolutePath());
				// create directories for sub directories in zip
				new File(newFile.getParent()).mkdirs();
				final var fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				// close this ZipEntry
				zis.closeEntry();
				ze = zis.getNextEntry();
			}
			// close last ZipEntry
			zis.closeEntry();
			zis.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*-  ******************************************************************************
	 *  List all files in directorys 
	 * ****************************************************************************** */
	public static List<Path> listFiles(String directoryPath) {
		final var dir = Paths.get(directoryPath);

		try {
			if (!Files.isDirectory(dir)) {
				System.err.println("The provided path is not a directory: " + directoryPath);
				return null;
			}

			final List<Path> files = Files.list(dir).collect(Collectors.toList());
			return files;
		} catch (IOException e) {
			System.err.println("Error while listing files: " + e.getMessage());
			return null;
		}
	}

}
