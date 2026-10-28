package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;

public final class ReportGenerator {
    private ReportGenerator() {
    }

    public static String generate(Path logsFolder, Path reportFile) throws IOException {
        if (logsFolder == null || reportFile == null) {
            throw new IllegalArgumentException("Log folder and report file are required.");
        }
        if (!Files.isDirectory(logsFolder)) {
            throw new IOException("Log folder does not exist or is not a directory: " + logsFolder);
        }

        long totalDeposits = 0;
        long totalWithdrawals = 0;
        StringBuilder report = new StringBuilder("End-of-day transaction summary\n");
        Path normalizedReport = reportFile.toAbsolutePath().normalize();
        try (DirectoryStream<Path> files = Files.newDirectoryStream(logsFolder)) {
            for (Path file : files) {
                if (!Files.isRegularFile(file)
                        || file.toAbsolutePath().normalize().equals(normalizedReport)) {
                    continue;
                }

                BasicFileAttributes attributes =
                        Files.readAttributes(file, BasicFileAttributes.class);
                report.append("\nFile: ").append(file.getFileName())
                        .append(" | Size: ").append(attributes.size()).append(" bytes")
                        .append(" | Last modified: ").append(attributes.lastModifiedTime())
                        .append('\n');

                try (BufferedReader reader =
                        Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.trim().split("\\s+");
                        if (parts.length != 3) {
                            throw new IOException("Malformed transaction log line in " + file
                                    + ": " + line);
                        }
                        long amount;
                        try {
                            amount = Long.parseLong(parts[2]);
                        } catch (NumberFormatException e) {
                            throw new IOException("Invalid transaction amount in " + file
                                    + ": " + line, e);
                        }
                        if (amount <= 0) {
                            throw new IOException("Transaction amount must be positive in "
                                    + file + ": " + line);
                        }

                        switch (parts[0]) {
                            case "DEPOSIT" -> totalDeposits = Math.addExact(totalDeposits, amount);
                            case "WITHDRAW" ->
                                totalWithdrawals = Math.addExact(totalWithdrawals, amount);
                            default -> throw new IOException("Unknown transaction type in "
                                    + file + ": " + parts[0]);
                        }
                    }
                } catch (ArithmeticException e) {
                    throw new IOException("Transaction total exceeds the supported range.", e);
                }
            }
        }

        report.append("\nTotal deposits: Rs. ").append(totalDeposits)
                .append("\nTotal withdrawals: Rs. ").append(totalWithdrawals)
                .append("\nNet transaction amount: Rs. ")
                .append(totalDeposits - totalWithdrawals).append('\n');

        Path parent = reportFile.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(reportFile, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE)) {
            writer.write(report.toString());
        }
        return report.toString();
    }
}
