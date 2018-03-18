import javax.swing.JFileChooser;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ExportAsHTML implements Exportable{
	
	
	private List<String> folderName = new ArrayList<String>();
	private List<Set> filesInFolder = new ArrayList<Set>();
	private List<TreeNode[]> parents = new ArrayList<TreeNode[]>();
	private HTMLDocument document = new HTMLDocument();
	private HTMLEditorKit editor = new HTMLEditorKit();
	
	public void exporting(List<DefaultMutableTreeNode> tnl){
		
		initializeListMaking(tnl);
		
		String goodPath = promptForFile();
		goodPath = goodPath+".html";
		
		 
		File f = new File(goodPath);
		
		for(int i=0;i<folderName.size();i++){
			
			editorAddsToHTML(parents.get(i).length-1,folderName.get(i));
			
			Set<Photo> photoSet = filesInFolder.get(i);
			Object photoArray[]= photoSet.toArray();
			
			for(int j=0;j<photoArray.length;j++){
				Photo p = (Photo)photoArray[j];
				File photoFile = p.getFile();
				String fileLocation = photoFile.getAbsolutePath();
				String htmlCode = "<img src=\"file:///"+fileLocation+"\" width=\"50\" height=\"50\" alt=\"Picture\">";
				try{
				    editor.insertHTML(document, document.getLength(),htmlCode, 0, 0, null);
				   }
			    catch(IOException ioe){
			       }
			    catch(BadLocationException ble){			 
			       }
			}
			
			try{
				editor.insertHTML(document, document.getLength(), "<b>"+"<br>"+"</b>", 0, 0, null);
				 
				 
				 
			   }
			catch(IOException ioe){
			   }
			catch(BadLocationException ble){			 
			   }
			
			
		}
		
		
		try{
		FileOutputStream fos = new FileOutputStream(f);
		editor.write(fos, document, 0, document.getLength());
		fos.close();
		}
		catch(IOException ioe){
		}
		catch(BadLocationException ble){
		}
		 
	
	}
	
	
	
	
	private String promptForFile(){
		JFileChooser fc=new JFileChooser();
		int returnVal=fc.showOpenDialog(fc);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile().getAbsolutePath();
		  }
		else {
		    return null;
		  }
		}
	
	
	 private void initializeListMaking (List<DefaultMutableTreeNode> nl){
			
			Album root = (Album) nl.get(0).getUserObject();
			
			folderName.add(root.toString());
			filesInFolder.add(root.getPhotoSet());
			parents.add(nl.get(0).getPath());
			
			
			
			fillingUpTheLists(nl);
			
		}
	 
	 private void fillingUpTheLists(List<DefaultMutableTreeNode> nodeList){
			
			for(int i=1;i<nodeList.size();i++){
				DefaultMutableTreeNode tempNode = nodeList.get(i);
				TreeNode tn []= tempNode.getPath();
				Album a = (Album)tempNode.getUserObject();
				folderName.add(a.toString());
				filesInFolder.add(a.getPhotoSet());
				parents.add(tn);
				
				
			}
			
		}
	 
	 
	 private void editorAddsToHTML(int parents, String folders){
		 
		 String tab="&nbsp;";
		 
		 for(int i=0;i<parents;i++){
			 tab = tab+"&nbsp;&nbsp;&nbsp;";
		 }
		 
		 try{
		 editor.insertHTML(document, document.getLength(), "<b>"+tab+folders+"<br>"+"</b>", 0, 0, null);
		 
		 
		 
		 }
		 catch(IOException ioe){
		 }
		 catch(BadLocationException ble){			 
		 }
	 }

}
