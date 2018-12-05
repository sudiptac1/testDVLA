package helper;

/*Factory class returning different type of singleton FileList Reader implementation based on mimeType
 * Currently returns CSV Reader implementation and can be updated to return other Mime type readers
 * */
public class FileListReaderFactory {

	public static FileListReader getFileListReaderInstance(String mimeType) {
		FileListReader instance = null;
		switch (mimeType) {
		case "text/csv":
			instance = FileListReader.getCSVReaderInstance();
			break;
		}

		return instance;

	}

}
