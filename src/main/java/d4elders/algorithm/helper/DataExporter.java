package d4elders.algorithm.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class DataExporter {
	private static final Logger log = Logger.getLogger(DataExporter.class.getName());
	private static final String dataDirname = "data";
	private static final String separator = ",";

	public static void exportData(Path filePath, ArrayList<String> headers, ArrayList<String> data) throws DataExporterException{
		File dataFile = new File(filePath.toString());
		boolean writeHeaders = false;

		if(!dataFile.exists()){
			log.log(Level.WARNING, "File " + dataFile.getAbsolutePath() + " not found => no history!");
			writeHeaders = true;
		}

		BufferedWriter bw = null;
		try{
			bw = new BufferedWriter(new FileWriter(dataFile, true));

			// if it's a new file, write the headers too
			if (writeHeaders){
				bw.write("ID");
				for(String header : headers){
					bw.write(separator + header);
				}
				bw.newLine();
			}

			// write the timestamp first - as an ID
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			bw.write(timeStamp);

			// write the new data
			for(String datum : data){
				datum = datum.replaceAll(",", ";");
				bw.write(separator + datum);
			}

			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw new DataExporterException("Cannot write to " + dataFile.toString());
		}
		finally{
			if(bw != null)	try {
					bw.close();
				} catch (IOException e) {
			}
		}

	}
}












