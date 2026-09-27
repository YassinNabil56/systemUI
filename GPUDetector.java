import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GPUDetector {

    public static String getGPUInfoHTML() {
        List<String> gpus = new ArrayList<>();
        
        try {
            // Query only GPU Names from Windows WMIC
            ProcessBuilder processBuilder = new ProcessBuilder(
                "cmd.exe", "/c", "wmic path win32_VideoController get Name /format:csv"
            );
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // Skip headers and empty rows
                if (!line.isEmpty() && !line.startsWith("Node") && !line.startsWith("Name")) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        String gpuName = parts[1].trim();
                        if (!gpuName.isEmpty()) {
                            gpus.add(gpuName);
                        }
                    }
                }
            }
        } catch (Exception e) {
            return "<html><center>Failed to detect GPU</center></html>";
        }

        if (gpus.isEmpty()) {
            return "<html><center>No GPU detected</center></html>";
        }

        // Join multiple GPUs with <br><br> for HTML line breaks in Swing
        StringBuilder htmlResult = new StringBuilder("<html><center>");
        for (int i = 0; i < gpus.size(); i++) {
            htmlResult.append(gpus.get(i));
            if (i < gpus.size() - 1) {
                htmlResult.append("<br><br>"); // Separates each GPU onto a new line
            }
        }
        htmlResult.append("</center></html>");

        return htmlResult.toString();
    }
public static String getTotalVRAMHTML() {
        long totalVRAMBytes = 0;

        try {
            // Query AdapterRAM for all GPUs
            ProcessBuilder processBuilder = new ProcessBuilder(
                "cmd.exe", "/C", "wmic path win32_VideoController get AdapterRAM /format:csv"
            );
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // Skip headers and empty lines
                if (!line.isEmpty() && !line.startsWith("Node") && !line.startsWith("AdapterRAM")) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        String rawVram = parts[1].trim();
                        if (!rawVram.isEmpty()) {
                            long bytes = Long.parseLong(rawVram);
                            // Sum up bytes from every GPU found
                            totalVRAMBytes += bytes;
                        }
                    }
                }
            }
        } catch (Exception e) {
            return "<html><center>Failed to detect VRAM</center></html>";
        }

        if (totalVRAMBytes == 0) {
            return "<html><center>No VRAM detected</center></html>";
        }

        // Convert total bytes to GB (1 GB = 1024 * 1024 * 1024 bytes)
        double totalGB = (double) totalVRAMBytes / (1024 * 1024 * 1024);

        // Formats to 2 decimal places e.g., "4.00 GB VRAM"
        return String.format("<html><center>%.2f GB VRAM</center></html>", totalGB);
    }
}
