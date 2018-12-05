package helper;

import java.io.File;
import java.util.List;
import java.util.Map;

/*Interface for FileListReader*/
public interface FileListReaderIf {
	public void readFileList(File file, Map<String, List<String>> fileVehicleDetails);
}
