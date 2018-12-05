package helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*Concrete singleton implementation of different types of mime type readers 
 * currently CSV implementation is supported and the class can be updated to 
 * support other mime type readers as well
 * */
public enum FileListReader implements FileListReaderIf {
	CSV_READER_INSTANCE {
		@Override
		public void readFileList(File file, Map<String, List<String>> fileVehicleDetails) {

			try (Stream<String> lines = Files.lines(file.toPath())) {
				List<List<String>> values = lines.map(line -> Arrays.asList(line.split(",")))
						.collect(Collectors.toList());
				for (List<String> vehicleDetails : values) {
					fileVehicleDetails.put(vehicleDetails.get(0), vehicleDetails);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	};
	private FileListReader() {

	}

	public static FileListReader getCSVReaderInstance() {
		return CSV_READER_INSTANCE;
	}

}
