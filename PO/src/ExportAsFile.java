import javax.swing.JFileChooser;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.swing.tree.TreeModel;



public class ExportAsFile implements Exportable {
	
	private List<String> folderName = new ArrayList<String>();
	private List<Set> filesInFolder = new ArrayList<Set>();
	private List<TreeNode[]> parents = new ArrayList<TreeNode[]>();
	
	public void exporting(List<DefaultMutableTreeNode> tnl){
		initializeListMaking(tnl);
		
		String goodPath = promptForFile();
        boolean created = false;
		
		if(goodPath!=null)
			created = new File(goodPath+"/Root").mkdirs();
		
		
		if(created){
			
			Set rootPhotoSet = filesInFolder.get(0);
			Object [] objArr = rootPhotoSet.toArray();
			
			for(int i=0;i<objArr.length;i++){
				Photo p = (Photo)objArr[i];
				File oldFile = p.getFile();
				
				try {
					Files.copy(oldFile.toPath(),
							(new File(goodPath+"/Root/"+oldFile.getName())).toPath(),
							StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
			
			for(int i=1;i<folderName.size();i++){
				String s = goodPath;
				
				for(int j=0;j<parents.get(i).length;j++){
					DefaultMutableTreeNode tempNode = (DefaultMutableTreeNode)parents.get(i)[j];
					Album tempAlbum = (Album)tempNode.getUserObject();
					String tempString = tempAlbum.toString();
					s = s+"/"+tempString;
					
				}
				
				boolean makingDir = new File(s).mkdir();
				
				Set tempPhotoSet = filesInFolder.get(i);
				Object [] arrTempPS = tempPhotoSet.toArray();
				
				for(int k=0;k<arrTempPS.length;k++){
					Photo p = (Photo)arrTempPS[k];
					File oldTempFile = p.getFile();
					
					try {
						Files.copy(oldTempFile.toPath(),
								(new File(s+"/"+oldTempFile.getName())).toPath(),
								StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
		}
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
    
    private void initializeListMaking (List<DefaultMutableTreeNode> nl){
		
		Album root = (Album) nl.get(0).getUserObject();
		
		folderName.add(root.toString());
		filesInFolder.add(root.getPhotoSet());
		parents.add(nl.get(0).getPath());
		
		
		
		fillingUpTheLists(nl);
		
	}
	

}
