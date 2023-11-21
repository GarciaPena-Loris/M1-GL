package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * The CodebaseManager class handles codebase-related operations, such as emptying,
 * checking, and restoring the codebase folder. It is used for dynamic code downloading
 * in RMI.
 * 
 * @author anonbnr
 */
public class CodebaseManager {
	/**
     * Empties the codebase folder by deleting all its contents.
     */
	public static void emptyCodebaseFolder() {
		File codebaseFolder = new File("codebase/");
		if (codebaseFolder.exists() && codebaseFolder.isDirectory())
			deleteFolderContents(codebaseFolder);
	}

	/**
	 * Recursively deletes the contents of a given folder, including subfolders and files.
	 *
	 * @param folder The folder whose contents should be deleted.
	 */
	private static void deleteFolderContents(File folder) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory())
					// If it's a subfolder, recursively delete its contents.
					deleteFolderContents(file);
				// Delete the file or empty subfolder.
				file.delete();
			}
		}
	}
	
	/**
     * Checks if the codebase folder is empty.
     *
     * @return true if the codebase folder is empty, false otherwise.
     */
	public static boolean codebaseIsEmpty() {
		return isFolderEmpty("codebase/");
	}
	
	/**
     * Checks if a folder is empty.
     *
     * @param folderPath The path to the folder to be checked.
     * @return true if the folder is empty, false otherwise.
     */
	private static boolean isFolderEmpty(String folderPath) {
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            String[] files = folder.list();
            return files == null || files.length == 0;
        }

        // If the folder does not exist or is not a directory, consider it empty.
        return true;
    }
	
	/**
     * Restores the codebase folder by copying necessary files.
     */
	public static void restoreCodebaseFolder() {
        File sourceFile = new File("bin/model/Snake.class");
        File destinationFolder = new File("codebase/model/");
        File destinationFile = new File("codebase/model/Snake.class");
        
        try {
        	Files.createDirectory(destinationFolder.toPath());
            Files.copy(sourceFile.toPath(), destinationFile.toPath(), 
            		StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
        	System.err.println("Error: Couldn't restore codebase contents.");
            e.printStackTrace();
        }
    }
}