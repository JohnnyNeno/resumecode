import java.util.Set;
import java.util.HashSet;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;


public class Album {
	private Set<Photo> inThisAlbum= new HashSet<Photo>();
	private int photoAmount;
	private String albumName;
	private static int numberOfAlbums =0;
	private static List<String> albumList= new ArrayList<String>();
	private boolean commandsAllowed;
	
	
	
	
	public Album(String s){
		
		assert s!=null&&s!="":"FEL"; //Pre
		
		this.albumName=s;
		albumList.add(s);
		numberOfAlbums++;
		commandsAllowed=true;
		
		assert toString()==albumName&&invariant(); //Post
	}
	
	public void destroyAlbum(){
		assert albumList.contains(albumName)&&invariant();//Pre
		
		if(albumList.contains(albumName)){
		albumList.remove(albumName);
		numberOfAlbums--;
		}
		
		assert !albumList.contains(albumName)&&invariant(); //Post
	}
	
	
	public void addPhotoToAlbum(Photo p){
		assert albumList.contains(albumName)&&invariant(); //Pre
		
		if(albumList.contains(albumName)){
		inThisAlbum.add(p);
		photoAmount++;
		}
		
		assert inThisAlbum.contains(p)&&invariant(); //Post
	}
	
	
	public void addPhotosToAlbum(Set<Photo> photoSet){
		if(albumList.contains(albumName)){
			inThisAlbum.addAll(photoSet);
			photoAmount=photoAmount+photoSet.size();
			}
	}
	
	
	
	public void removePhotoFromAlbum(Photo p){
		assert albumList.contains(albumName)&&inThisAlbum.contains(p)&&invariant(); //Pre
		
		if(albumList.contains(albumName)&&inThisAlbum.contains(p)){
		inThisAlbum.remove(p);
		photoAmount--;
		}
		
		assert !inThisAlbum.contains(p)&&invariant(); //Post
	}
	
	
	public Set<Photo> getPhotoSet(){
		return inThisAlbum;
	}
	
	public int getPhotoAmountInAlbum(){
		return photoAmount;
	}
	
	
    public String toString(){
		return albumName;
		
	}
	
	public static int getAlbumAmount(){
		return numberOfAlbums;
	}
	
	public static List<String> getAlbumList(){
		return albumList;
	}
	public boolean getCommandsAllowed(){
		return commandsAllowed;
	}
	
	public void setCommandsAllowed(boolean b){
		commandsAllowed=b;
		
	}
	public void emptyOldPhotoSet(){
		inThisAlbum= new HashSet<Photo>();
		photoAmount=0;
		
	}
	
	public static void resetCommand(){
		for(int i=1;i<albumList.size();i++){
			albumList.remove(i);
		}
		numberOfAlbums=1;
	}

	
	public boolean invariant(){
		return albumName!=null&&albumName!=""
			&&albumList.size()==numberOfAlbums
			&&albumList.contains(albumName)
			&&photoAmount==inThisAlbum.size()
			&&numberOfAlbums>=0
			&&photoAmount>=0;
		
	}
}
