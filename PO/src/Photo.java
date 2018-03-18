import java.io.File;

/**
 * Photo is an immutable class representing a digital photo file on disk.
 * 
 * @author jbaek, rcm
 */
public class Photo {

	private final File file;
	private boolean flagged;
	private int rating;

	/**
	 * Make a Photo for a file. Requires file != null.
	 */
	
	
	public Photo(File file) {
		this.file = file;
		this.flagged = false;
		this.rating=0;
	}

	/**
	 * @return the file containing this photo.
	 */
	public File getFile() {
		return file;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Photo && ((Photo) obj).file.equals(file);
	}

	@Override
	public int hashCode() {
		return file.hashCode();
	}
	
	public void setFlag(){
		if(flagged==false)
			flagged=true;
		else
			flagged=false;
	}
	
	public boolean getFlag(){
		return flagged;
	}
	
	public void setRating(int i){
		this.rating=i;
	}
	public int getRating(){
		return rating;
	}
	
	
}
