
public class ExportFactory {
	
	public Exportable getExportFormat(int i){
		if(i==-1)
			return null;
		if(i==0)
			return new ExportAsFile();
		if(i==1)
			return new ExportAsHTML();
		
		else
			return null;
		
	}

}
