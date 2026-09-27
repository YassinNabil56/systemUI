import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DiskDetector {
    public static String DiskintoHTML(){
        long TotalBytes = 0;
        try{
            ProcessBuilder processBuilder = new ProcessBuilder(
            "cmd.exe","/C","wmic path win32_diskdrive get Size /format:csv"
            );
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while((line = reader.readLine()) != null){
                line = line.trim();

                // Skip headers and empty rows
                if (!line.isEmpty() && !line.startsWith("Node") && !line.startsWith("Size")) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2 && !parts[1].isEmpty()) {
                        TotalBytes += Long.parseLong(parts[1].trim());
                    }
                }
            }
        }
        catch(IOException e){
            return  "<html><center>Cannot allocate size</center></html>";
        }
        if (TotalBytes == 0) {
            return "<html><center>No disks detected</center></html>";
        }
        long totalGB = TotalBytes / (1024 * 1024 * 1024);
        return "<html><center>" + totalGB + " GB</center></html>";
        
        
    }
    public static String getStorageUsageHTML() {
        long totalSizeBytes = 0;
        long totalFreeBytes = 0;

        try {
            // DriveType=3 filters for local hard disks (ignores CDs, USBs, network drives)
            ProcessBuilder processBuilder = new ProcessBuilder(
                "cmd.exe", "/C", "wmic path win32_logicaldisk where DriveType=3 get FreeSpace, Size /format:csv"
            );
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Skip headers and empty rows
                if (!line.isEmpty() && !line.startsWith("Node") && !line.startsWith("FreeSpace")) {
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        // parts[1] = FreeSpace in bytes, parts[2] = Size in bytes
                        if (!parts[1].isEmpty() && !parts[2].isEmpty()) {
                            totalFreeBytes += Long.parseLong(parts[1].trim());
                            totalSizeBytes += Long.parseLong(parts[2].trim());
                        }
                    }
                }
            }
        } catch (IOException e) {
            return "<html><center>Cannot allocate storage</center></html>";
        }

        if (totalSizeBytes == 0) {
            return "<html><center>No storage detected</center></html>";
        }

        // Calculate used bytes
        long usedBytes = totalSizeBytes - totalFreeBytes;

        // Convert bytes to GB (using 1024L to avoid integer overflow)
        long totalGB = totalSizeBytes / (1024L * 1024L * 1024L);
        long usedGB = usedBytes / (1024L * 1024L * 1024L);

        // Returns format: "700 GB out of 1043 GB used"
        return "<html><center>" + usedGB + " GB out of " + totalGB + " GB used</center></html>";
    }
}
