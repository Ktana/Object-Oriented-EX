package GUI;

import java.io.File;
/**
 * Was supposed to filter the folder from the non-CSV's - CURRENTLY NOT USED, found a better solution!
 * @author Alex Fishman
 *
 */
public class FolderFilter extends javax.swing.filechooser.FileFilter {
      @Override
      public boolean accept( File file ) {
        return file.isFile();
      }

      @Override
      public String getDescription() {
        return "We only pick directories";
      }
      
    }
 
