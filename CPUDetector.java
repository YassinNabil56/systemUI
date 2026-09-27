import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CPUDetector {

    // 1. Get CPU Name (e.g., Intel Core i5-4210U)
    public static String getCPUInfoHTML() {
        String cpuName = "";

        try {
            ProcessBuilder processbuilder = new ProcessBuilder(
                "cmd.exe", "/C", "wmic path win32_Processor get Name /format:csv"
            );
            Process process = processbuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("Node") && !line.startsWith("Name")) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        cpuName = parts[1].trim();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            return "<html><center>Failed to detect CPU</center></html>";
        }

        if (cpuName.isEmpty()) {
            return "<html><center>No CPU detected</center></html>";
        }

        return "<html><center>" + cpuName + "</center></html>";
    }

    // 2. Get CPU Speed in GHz (e.g., 2.40 GHz)
    public static String getCPUhertzHTML() {
        String cpuHertz = "";

        try {
            ProcessBuilder processbuilder = new ProcessBuilder(
                "cmd.exe", "/C", "wmic path win32_Processor get MaxClockSpeed /format:csv"
            );
            Process process = processbuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("Node") && !line.startsWith("MaxClockSpeed")) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        String mhzStr = parts[1].trim();
                        if (!mhzStr.isEmpty()) {
                            double mhz = Double.parseDouble(mhzStr);
                            double ghz = mhz / 1000.0;
                            cpuHertz = String.format("%.2f GHz", ghz);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            return "<html><center>Failed to detect speed</center></html>";
        }

        if (cpuHertz.isEmpty()) {
            return "<html><center>No CPU speed detected</center></html>";
        }

        return "<html><center>" + cpuHertz + "</center></html>";
    }
}