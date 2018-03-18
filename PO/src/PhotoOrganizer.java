import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.*;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;


/**
 * PhotoOrganizer is a window that allows arranging photos into
 * hierarchical albums and viewing the photos in each album.
 * 
 * Original @author rcm
 * 
 * This class is incomplete and it does not compile. You need to edit it 
 * in order to use the class(es) to represent albums that you have created.
 * 
 * You can modify any part of this class. However, we have marked with a TODO 
 * comment tag the sections of code that probably require most of your attention.
 * @param <root>
 */
public class PhotoOrganizer<root> extends JFrame implements Observer {

	//Done
	private DefaultMutableTreeNode tree_root;
	private Album root= new Album("Root");
	private SearchAlbumTemplate flaggedPhotosAlbum = new FlagAlbum("Flagged");
	private SearchAlbumTemplate highlyRatedPhotosAlbum = new GreatPhotosAlbum("Great Photos");
	private final JTree albumTree;
	private final PreviewPane previewPane;
	private List<Album> allAlbumsList; 
	private PhotoOrganizerController poc;
	private int POamount=1;
	private PhotoOrganizer po = this;
	private JButton undoButton;
	private JButton redoButton;
	private ExportFactory expFac= new ExportFactory();
	private List<DefaultMutableTreeNode> nodeList;
	
	
	
	
	
	
	
	public void update(JTree jt, DefaultMutableTreeNode tn, int i, boolean undoEnable,boolean redoEnable){
		undoButton.setEnabled(undoEnable);
		redoButton.setEnabled(redoEnable);
		
		
		
		POamount=i;
		

		this.tree_root.removeAllChildren();
		Album.resetCommand();
		
		Album modelRootAlbum = (Album)tn.getUserObject();
		Set<Photo> modelRootPhotoSet = modelRootAlbum.getPhotoSet();
		
		this.root.emptyOldPhotoSet();
	
		this.root.addPhotosToAlbum(modelRootPhotoSet);
		
		
		
		DefaultTreeModel stateTreeModel= (DefaultTreeModel)(jt.getModel());
		
		Album newpvpAlbum=updatePreviewPaneAlbum(this.previewPane.getPreviewPanesAlbum(),tn,stateTreeModel);
		this.previewPane.display(newpvpAlbum);
		updatingOldTree(stateTreeModel,tn,tree_root);
		
		
		albumTree.updateUI();
		
		
		
		 
			
	}
	
	public Album updatePreviewPaneAlbum(Album a, DefaultMutableTreeNode tn, DefaultTreeModel tm){
		
		Album returnAlbum =(Album) tn.getUserObject();
		
		for(int i=0;i<tn.getChildCount();i++){
			DefaultMutableTreeNode childNode = (DefaultMutableTreeNode)tm.getChild(tn,i);
			Album newAlbum = (Album)childNode.getUserObject();
			
			if(newAlbum.toString().equals(a.toString())){
				returnAlbum = newAlbum;
				
			}
			updatePreviewPaneAlbum(a,childNode,tm);
			
				
			
		}
		
		return returnAlbum;
		
		
	}
	
	
	
	public void updatingOldTree(DefaultTreeModel stm, DefaultMutableTreeNode tn, DefaultMutableTreeNode currentRoot){
		
		DefaultTreeModel treeModel = (DefaultTreeModel) (albumTree.getModel());
		
		
		
		
		for(int i=0; i<tn.getChildCount();i++){
			
			SearchAlbumTemplate sa;
		
			DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) stm.getChild(tn,i);
			DefaultMutableTreeNode tempNode = new DefaultMutableTreeNode();
			Set<Photo> tempPhotoSet = new HashSet<Photo>();
			
			Album fakeAlbum=(Album)childNode.getUserObject();
			tempPhotoSet = fakeAlbum.getPhotoSet();
				
			Album toBeInserted = new Album(childNode.getUserObject().toString());
			
			if(toBeInserted.toString().equals("Flagged")){
				 sa = new FlagAlbum("Flagged");
				 sa.addPhotosToAlbum(tempPhotoSet);
				 tempNode.setUserObject(sa);
				 treeModel.insertNodeInto(tempNode, currentRoot, currentRoot.getChildCount());
			
			}
			else if(toBeInserted.toString().equals("Great Photos")){
				sa = new GreatPhotosAlbum("Great Photos");
				sa.addPhotosToAlbum(tempPhotoSet);
				tempNode.setUserObject(sa);
				treeModel.insertNodeInto(tempNode, currentRoot, currentRoot.getChildCount());
			}
			
			else{
			toBeInserted.addPhotosToAlbum(tempPhotoSet);
			tempNode.setUserObject(toBeInserted);
			
			treeModel.insertNodeInto(tempNode,currentRoot, currentRoot.getChildCount());}
			
			albumTree.scrollPathToVisible(new TreePath(tempNode.getPath()));
			
			updatingOldTree(stm,childNode,(DefaultMutableTreeNode)currentRoot.getChildAt(i));
			
		}
	}
	
	
	
	/**
	 * Main entry point of photo organizer.
	 * @param args command line arguments
	 */
    public static void main(final String[] args) {
    	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				PhotoOrganizer main = new PhotoOrganizer();
				
				main.setVisible(true);
				
				if (args.length == 0) {
					main.loadPhotos("sample-photos");
				} else if (args.length == 1) {
					main.loadPhotos(args[0]);
				} else {
					System.err.println("too many command-line arguments");
					System.exit(0);
				}
			}
		}); 
	}
	
	/**
	 * Make a PhotoOrganizer window.
	 */
	public PhotoOrganizer() {
		

		// set up the panel on the left with two subpanels in a vertical layout
		JPanel catalogPanel = new JPanel();
		catalogPanel.setLayout(new BoxLayout(catalogPanel,
				BoxLayout.PAGE_AXIS));
		
		// make the row of buttons 
		JPanel buttonPanel = makeButtonPanel();
		catalogPanel.add(buttonPanel);
		
		// make the album tree
		
		albumTree = makeCatalogTree();
		albumTree.setEditable(true);
		catalogPanel.add(new JScrollPane(albumTree));
		
		// make the image previewer
		previewPane = new PreviewPane();

		// put the catalog tree and image previewer side by side, 
		// with an adjustable splitter between
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				catalogPanel, previewPane);
		splitPane.setDividerLocation(1200);
		this.add(splitPane);
		
		/*SlideShowWindow slideShowWindow = new SlideShowWindow();
		slideShowWindow.setVisible(true); */
		
		// give the whole window a good default size
		this.setTitle("Photo Organizer");
        this.setSize(1600,600);
        
        
        
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
            	if(POamount==1){
            		
            		setDefaultCloseOperation(EXIT_ON_CLOSE);
            		System.exit(0);
            	}
            	else{
            		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            		poc.closingWindow(po);
            	}
                
            }
        });

        // end the program when the user presses the window's Close button
		
		
		
		addSearchAlbums();
		allAlbumsList= allCurrentAlbums();
		
		previewPane.display(root);
		
		poc = new PhotoOrganizerController(this);
		
		
	
		
		
	}

	/**
	 * Load the photos found in all subfolders of a path on disk.
	 * If path is not an actual folder on disk, has no effect.
	 */
	public void loadPhotos(String path) {
		Set<Photo> photos = PhotoLoader.loadPhotos(path);	
		root.addPhotosToAlbum(photos);
		previewPane.display(root);
		poc.initializeSSPhoto(albumTree, tree_root);
		
		// TODO: Add photo set to the root album and display it
		
	}
	
	/**
	 * Make the button panel for manipulating albums and photos.
	 */
	private JPanel makeButtonPanel() {
		JPanel panel = new JPanel();
		
		// Using a BoxLayout so that buttons will be horizontally aligned
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		
		JButton newAlbumButton = new JButton("New Album");
		newAlbumButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String newAlbumName = promptForAlbumName();
				if (newAlbumName == null) return;
			    //Done
				Album a = new Album(newAlbumName);
				DefaultMutableTreeNode lastSelected = getSelectedTreeNode();
				Album b = (Album) lastSelected.getUserObject();
			
				if(b.getCommandsAllowed()){
				DefaultMutableTreeNode trnode= new DefaultMutableTreeNode();
				trnode.setUserObject(a);
				DefaultTreeModel model = (DefaultTreeModel) (albumTree.getModel());
				model.insertNodeInto(trnode,getSelectedTreeNode(), getSelectedTreeNode().getChildCount());
				albumTree.scrollPathToVisible(new TreePath(trnode.getPath()));
				System.out.println("new album " + newAlbumName + " as subalbum of " + getSelectedTreeNode());
				
				poc.newAlbumPressed(getSelectedTreeNode(), a);
				}
			}
		});
		panel.add(newAlbumButton);

		JButton deleteAlbumButton = new JButton("Delete Album");
		deleteAlbumButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			
				try{
					
				
				DefaultTreeModel model = (DefaultTreeModel) (albumTree.getModel());
				DefaultMutableTreeNode lastSelected = getSelectedTreeNode();
				Album b = (Album) lastSelected.getUserObject();
				if(b.getCommandsAllowed()&&b.toString()!="Root"){
					findAndDestroyChildrenOfAlbum(model,lastSelected);
					model.removeNodeFromParent(getSelectedTreeNode());
					
				
					b.destroyAlbum();
					System.out.println("delete album " + lastSelected);
					
					poc.deleteAlbumPressed((Album)lastSelected.getUserObject());
				
					}
				}
				catch (IllegalArgumentException IAE){
					System.out.println("Choose an album to delete (NOT ROOT)!");
					System.out.println(Album.getAlbumAmount());
				
					
				}
				
			}
		});
		panel.add(deleteAlbumButton);

		JButton addPhotosButton = new JButton("Add Photos");
		addPhotosButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO REPLACE WITH YOUR CODE
				DefaultMutableTreeNode selectedNode= getSelectedTreeNode();
				Album selectedAlbum=(Album)selectedNode.getUserObject();
				if(selectedAlbum.getCommandsAllowed()){
				Set <Photo> photosToBeAdded = previewPane.getSelectedPhotos();
				selectedAlbum.addPhotosToAlbum(photosToBeAdded);
				
				System.out.println("add " + previewPane.getSelectedPhotos().size() 
								   + " photos to album " + getSelectedTreeNode());
				
				poc.addPhotosPressed(selectedAlbum, photosToBeAdded);
			}
			}
		});
		panel.add(addPhotosButton);

		JButton removePhotosButton = new JButton("Remove Photos");
		removePhotosButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// REPLACE WITH YOUR CODE
				Album previewPaneAlbum=previewPane.getPreviewPanesAlbum();
				
				if(previewPaneAlbum.getCommandsAllowed()){
				Set <Photo> albumsSelectedPhotos= previewPane.getSelectedPhotos();
				Object[] photoArray = albumsSelectedPhotos.toArray();
				System.out.println("remove " + previewPane.getSelectedPhotos().size() + " photos from album "+ previewPaneAlbum.toString());
				for(int i=0;i<photoArray.length;i++){
					Photo p = (Photo)photoArray[i];
					previewPaneAlbum.removePhotoFromAlbum(p);
				}
				
				
				previewPane.display(previewPaneAlbum);
				
				poc.removePhotosPressed(previewPaneAlbum, albumsSelectedPhotos);
			
				}
				
			}
		});
		panel.add(removePhotosButton);
		
		JButton showInWindowButton = new JButton("Show as Slideshow");
		showInWindowButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				DefaultMutableTreeNode nodeChosen = getSelectedTreeNode();
				Album selectedForShow = (Album)nodeChosen.getUserObject();
				if(selectedForShow.getPhotoAmountInAlbum()!=0){
				SlideShowWindow show = new SlideShowWindow(selectedForShow);
				show.setVisible(true);
				
				}
				else{
					System.out.println("There are no photos in this album");
				}
				
			}
			
		});
		panel.add(showInWindowButton);
		
		JButton flagUnflagButton = new JButton("Flag/Unflag");
		flagUnflagButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				Set <Photo> albumsSelectedPhotos= previewPane.getSelectedPhotos();
				Object[] photoArray = albumsSelectedPhotos.toArray();
				for(int i=0;i<photoArray.length;i++){
					Photo p = (Photo)photoArray[i];
					p.setFlag();
				}
				flaggedPhotosAlbum.update(allCurrentAlbums());
				
				poc.flagButtonPressed(flaggedPhotosAlbum.getPhotoSet());
				if(previewPane.getPreviewPanesAlbum()==flaggedPhotosAlbum)
					previewPane.display(flaggedPhotosAlbum);
				
			}
		});
		panel.add(flagUnflagButton);
		
		JButton ratingButton = new JButton ("Rate Photos");
		ratingButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				try{
				
				int newRating= Integer.parseInt(promptForRating());
				
				if(newRating<=5&&newRating>=0){
					
					Set <Photo> albumsSelectedPhotos= previewPane.getSelectedPhotos();
					Object[] photoArray = albumsSelectedPhotos.toArray();
					for(int i=0;i<photoArray.length;i++){
						Photo p = (Photo)photoArray[i];
						p.setRating(newRating);
					}
					highlyRatedPhotosAlbum.update(allCurrentAlbums());
					
					poc.rateButtonPressed(highlyRatedPhotosAlbum.getPhotoSet());
					if(previewPane.getPreviewPanesAlbum()==highlyRatedPhotosAlbum)
						previewPane.display(highlyRatedPhotosAlbum);
					
				}
				
				else{
					System.out.println("Values out of bounds");
				}
				}
				catch(IllegalArgumentException IAE){
					System.out.println("Faulty Value Entered");
				}
			}
			
		});
		panel.add(ratingButton);
		
		JButton newWindowButton = new JButton("New Window");
		newWindowButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				PhotoOrganizer clone =  new PhotoOrganizer();
				clone.setVisible(true);
			}
			
		});
		panel.add(newWindowButton);
		
		undoButton = new JButton("Undo");
		undoButton.setEnabled(false);
		undoButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				poc.undoButtonPressed();
			}
			
		});
		panel.add(undoButton);
		
		redoButton = new JButton("Redo");
		redoButton.setEnabled(false);
		redoButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				poc.redoButtonPressed();
			}
			
		});
		panel.add(redoButton);
		
		
		JButton exportButton = new JButton("Export Collection");
		exportButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int formatNumber = promptForExportFormat();
				nodeList = new ArrayList<DefaultMutableTreeNode>();
				nodeList.add(tree_root);
				DefaultTreeModel model = (DefaultTreeModel) albumTree.getModel();
				makeNodeList(model,tree_root);
				Exportable exp = expFac.getExportFormat(formatNumber);
				if(exp!=null)
					exp.exporting(nodeList);
				
				
			}
			
		});
		panel.add(exportButton);
		

		return panel;
	}

	/**
	 * Make the tree showing album names.
	 */
	private JTree makeCatalogTree() {
		
		tree_root = new DefaultMutableTreeNode("all photos");
		tree_root.setUserObject(root);
		
		final JTree tree = new JTree(tree_root);
		tree.setMinimumSize(new Dimension(200,400));

		tree.setToggleClickCount(3); // so that we can use double-clicks for previewing instead of expanding/collapsing

		DefaultTreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
		selectionModel.setSelectionMode(DefaultTreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setSelectionModel(selectionModel);
		
		tree.addMouseListener(new MouseInputAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// if left-double-click @@@changed =2 to ==1
				if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
					DefaultMutableTreeNode tempNode = getSelectedTreeNode();
					Album chosenAlbum =(Album) tempNode.getUserObject();
					
					previewPane.display(chosenAlbum);
					
					// YOUR CODE HERE
					System.out.println("show the photos for album " + getSelectedTreeNode());
				
				}
			}
		});	
		
		return tree;
	}
	
	/**
	 * Return the album currently selected in the album tree.
	 * Returns null if no selection.
	 */
	private DefaultMutableTreeNode getSelectedTreeNode() {
		if(albumTree.getLastSelectedPathComponent()==null)
			return tree_root;
		return (DefaultMutableTreeNode) albumTree.getLastSelectedPathComponent();
	}

	/**
	 * Pop up a dialog box prompting the user for a name for a new album.
	 * Returns the name, or null if the user pressed Cancel.
	 */
	private String promptForAlbumName() {
		return (String)
		  JOptionPane.showInputDialog(
				albumTree, 
				"Album Name: ", 
				"Add Album",
				JOptionPane.PLAIN_MESSAGE, 
				null, 
				null, 
				"");		
	}
	
	private String promptForRating() {
		return (String)
		  JOptionPane.showInputDialog(
				albumTree, 
				"Rating: ", 
				"Give rating",
				JOptionPane.PLAIN_MESSAGE, 
				null, 
				null, 
				"");		
	}
	
	private int promptForExportFormat(){
		Object opt[]={"Files and Folders","HTML"};
		int n = JOptionPane.showOptionDialog(
			    albumTree,
			    "Choose export format.",
			    "Choice dialog",
			    JOptionPane.DEFAULT_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    opt,
			    null);
		return n;
		
		
		
	}
	
	private void findAndDestroyChildrenOfAlbum(DefaultTreeModel m,DefaultMutableTreeNode n){
		
		int childAmount = m.getChildCount(n);
		
		
		for(int i=0;i<childAmount;i++){
			DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) m.getChild(n, i);
			Album a = (Album)childNode.getUserObject();
			a.destroyAlbum();
			System.out.println("delete album "+a.toString());
			findAndDestroyChildrenOfAlbum(m,childNode);
		 
		}
	}
	
	private void addSearchAlbums(){
		DefaultMutableTreeNode flagNode= new DefaultMutableTreeNode();
		flagNode.setUserObject(flaggedPhotosAlbum);
		DefaultTreeModel modelOne = (DefaultTreeModel) (albumTree.getModel());
		modelOne.insertNodeInto(flagNode,getSelectedTreeNode(), getSelectedTreeNode().getChildCount());
		albumTree.scrollPathToVisible(new TreePath(flagNode.getPath()));
		
		DefaultMutableTreeNode greatNode= new DefaultMutableTreeNode();
		greatNode.setUserObject(highlyRatedPhotosAlbum);
		DefaultTreeModel modelTwo = (DefaultTreeModel) (albumTree.getModel());
		modelTwo.insertNodeInto(greatNode,getSelectedTreeNode(), getSelectedTreeNode().getChildCount());
		albumTree.scrollPathToVisible(new TreePath(greatNode.getPath()));
		
		
	}
	
	private List<Album> allCurrentAlbums(){
		allAlbumsList = new ArrayList<Album>();
		allAlbumsList.add(root);
		DefaultTreeModel treeModel = (DefaultTreeModel) (albumTree.getModel());
		allSubAlbums(treeModel,tree_root);
		
		return allAlbumsList;
	}
	
	
	private void allSubAlbums(DefaultTreeModel m, DefaultMutableTreeNode n){
		
		int childAmount = m.getChildCount(n);
		
		for(int i=0;i<childAmount;i++){
			
			DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) m.getChild(n, i);
			Album a = (Album)childNode.getUserObject();
			allAlbumsList.add(a);			
			allSubAlbums(m,childNode);
			
			
		}
		
	}
	
	
	public List<Album> getStartAlbumList(){
		return allAlbumsList;
	}
	
	
	
	public DefaultMutableTreeNode getRoot(){
		return tree_root;
	}
	
    protected void makeNodeList(DefaultTreeModel m, DefaultMutableTreeNode n){
		
		int childAmount = m.getChildCount(n);
		
		
		for(int i=0;i<childAmount;i++){
			DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) m.getChild(n, i);
			
			nodeList.add(childNode);
			
			makeNodeList(m,childNode);
			}

		
	}
 	
	
	
}
