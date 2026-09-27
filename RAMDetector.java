import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RAMDetector {

    public static String ddrDisplay(String infoText) {
        return "<html><center>" + infoText + "</center></html>";
    }

    private static String parseDDRCode(int typeCode) {
        switch (typeCode) {
            case 20: return "DDR";
            case 21: return "DDR2";
            case 24: return "DDR3";
            case 26: return "DDR4";
            case 34:
            case 35: return "DDR5";
            default: return "RAM";
        }
    }

    // 1. Separate method ONLY for DDR Type (e.g., "DDR3")
    public static String getDDRTypeHTML() {
        String ddrType = "";

        try {
            ProcessBuilder processbuilder = new ProcessBuilder(
                "cmd.exe", "/C", "wmic memorychip get SMBIOSMemoryType /format:csv"
            );
            Process process = processbuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("Node") && !line.startsWith("SMBIOSMemoryType")) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2 && !parts[1].isEmpty()) {
                        int code = Integer.parseInt(parts[1].trim());
                        ddrType = parseDDRCode(code);
                        break; // Grab the type from the first active RAM stick
                    }
                }
            }
        } catch (IOException e) {
            return ddrDisplay("Unknown DDR");
        }

        if (ddrType.isEmpty()) {
            return ddrDisplay("Unknown DDR");
        }

        return ddrDisplay(ddrType);
    }

    // 2. Separate method ONLY for RAM Size (e.g., "12 GB")
    public static String getRAMSizeHTML() {
        long totalCapacityBytes = 0;

        try {
            ProcessBuilder processbuilder = new ProcessBuilder(
                "cmd.exe", "/C", "wmic memorychip get Capacity /format:csv"
            );
            Process process = processbuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("Node") && !line.startsWith("Capacity")) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2 && !parts[1].isEmpty()) {
                        totalCapacityBytes += Long.parseLong(parts[1].trim());
                    }
                }
            }
        } catch (IOException e) {
            return ddrDisplay("Cannot allocate RAM");
        }

        if (totalCapacityBytes == 0) {
            return ddrDisplay("No RAM detected");
        }

        long totalGB = totalCapacityBytes / (1024 * 1024 * 1024);
        return ddrDisplay(totalGB + " GB");
    }
    
}