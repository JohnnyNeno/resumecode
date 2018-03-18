import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class FlagAlbum extends SearchAlbumTemplate {
	
	public FlagAlbum(String s){
		super(s);
	}
	
	public Set<Photo>findPhotos(List<Album> albumList){
		Set<Photo> flagSet = new HashSet<Photo>();
		
		
	
		
		for(int i=0;i<albumList.size();i++){
			
			Album a = albumList.get(i);
			Set<Photo> photoInAlbum=a.getPhotoSet();
			Object photoArray[] = photoInAlbum.toArray();
			
			for(int j=0;j<photoArray.length;j++){
				Photo p = (Photo) photoArray[j];
				
				if(p.getFlag()==true){
					flagSet.add(p);
					
				}
			}
			
			
		}
		
		return flagSet;
		
	}

}
