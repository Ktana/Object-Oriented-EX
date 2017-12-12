package GUI;

import java.io.File;

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
 
